package com.example.a17630.mythread;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    private TextView positionTextView;
    private LocationClient mLocationClient;//定位当前位置
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main2);
        positionTextView=findViewById(R.id.position_text_view);
        mLocationClient=new LocationClient(getApplicationContext());

        mapView=findViewById(R.id.bmapview);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);

        mLocationClient.registerLocationListener(new MyLocationLister());
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(Main2Activity.this,Manifest.permission.READ_PHONE_STATE)!=PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(Main2Activity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[]  permissions=permissionList.toArray(new String[permissionList.size()]);
            //把其转换为一个数组
            ActivityCompat.requestPermissions(Main2Activity.this,permissions,1);

        }else {
            requestLocation();
        }



    }

    private void navigateTo(BDLocation location){
        if(isFirstLocate){
            LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
            //获取当前的经纬度
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
            //更新当前位置
            baiduMap.animateMapStatus(update);
            //这样就显示了当前的位置
            update=MapStatusUpdateFactory.zoomTo(16f);
            //进行缩放功能
            baiduMap.animateMapStatus(update);
            isFirstLocate=false;
        }
        MyLocationData.Builder locationData=new MyLocationData.Builder();
        locationData.latitude(location.getLatitude());
        locationData.longitude(location.getLongitude());
        MyLocationData locationData1=locationData.build();
        baiduMap.setMyLocationData(locationData1);
    }
    protected void onResume() {

        super.onResume();
        mapView.onResume();

    }
    protected void onPause() {

        super.onPause();
        mapView.onPause();
    }
    protected void onDestroy() {

        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option=new LocationClientOption();
        //高精度定位模式，使用GPS与网络结合的模式
        option.setIsNeedAddress(true);
        //option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        mLocationClient.setLocOption(option);
    }

    public void onRequestPermissionsResult(int requestCode,String[] premissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result : grantResults){
                        if(result!=PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
                            Toast.makeText(this,"必须统一所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(Main2Activity.this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
                default:
                    break;
        }
    }
    public class MyLocationLister implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
            StringBuilder currentPosition=new StringBuilder();
            currentPosition.append("纬度:").append(bdLocation.getLatitude()).append("\n");//获取纬度
            currentPosition.append("经度:").append(bdLocation.getLongitude()).append("\n");//获取经线
            currentPosition.append("国家：").append(bdLocation.getCountry()).append("\n");
            currentPosition.append("省：").append(bdLocation.getProvince()).append("\n");
            currentPosition.append("城市：").append(bdLocation.getCity()).append("\n");
            currentPosition.append("街道：").append(bdLocation.getStreet()).append("\n");
            currentPosition.append("定位方式:");
            if(bdLocation.getLocType()==BDLocation.TypeGpsLocation){
                currentPosition.append("GPS");

            }else if(bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
                currentPosition.append("网络");
            }
            positionTextView.setText(currentPosition);
        }
    }
}
