package com.example.a17630.mythread;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder downloadBinder;

    private Button startService,stopService,startDownload,getProgress,startIntentService,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService=findViewById(R.id.start_service);
        stopService=findViewById(R.id.stop_service);
        startDownload=findViewById(R.id.start_download);
        getProgress=findViewById(R.id.get_progress);
        login=findViewById(R.id.login);
        login.setOnClickListener(this);
        startIntentService=findViewById(R.id.start_intents);
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        startDownload.setOnClickListener(this);
        getProgress.setOnClickListener(this);
        startIntentService.setOnClickListener(this);
    }
private ServiceConnection connection=new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        downloadBinder=(MyService.DownloadBinder)iBinder;
        downloadBinder.startDownload();
        downloadBinder.getDownload();

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {


    }
};
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_service:
                Intent startIntent=new Intent(this,MyService.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopService=new Intent(this,MyService.class);
                stopService(stopService);
                break;
            case R.id.start_download:
                Intent startDownloadIntent=new Intent(this,MyService.class);
                bindService(startDownloadIntent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.get_progress:
                //Intent getProgressIntent=new Intent(this,MyService.class);
                unbindService(connection);
                break;
            case R.id.start_intents:
                Log.d("MainActivity","Htrad  aaf");
                Intent sI=new Intent(this,MyIntentService.class);
                startService(sI);

                break;
            case R.id.login:
                Intent intent=new Intent(this,Main5Activity.class);
                startActivity(intent);

                break;
                default:
                    break;

        }

    }
}
