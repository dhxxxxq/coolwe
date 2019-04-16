package com.example.a17630.mythread;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyService extends Service {
    private DownloadBinder downloadBinder=new DownloadBinder();
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return downloadBinder;
    }
    public void onCreate(){
        super.onCreate();
        Log.d("MyService","oncreate executed");
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentText("this is content")
                .setContentTitle("this is title")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }
    public int onStartCommand(Intent intent,int flags,int startID){
        // super.onStartCommand(Intent intent,int flags,int startID);
        Log.d("MyService","onStartCommand executesd");
        return super.onStartCommand(intent,flags,startID);
    }
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyService","ondestroy executed");
    }
    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("MyService","startDownload");
        }
        public void getDownload(){
            Log.d("MyService","getProgress");
        }
    }
}
