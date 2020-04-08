package com.minos.recordscreendemo;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import thread.RecordService;

public class SecondActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String action;    //动作码
    MediaProjectionManager mediaProjectionManager;
    MediaProjection mp;

    Thread thread;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        button = (Button) findViewById(R.id.btn_stop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thread.interrupt();
            }
        });

        verifyStoragePermissions(this);
        doRecordScreen(getApplicationContext());


    }


    public static void verifyStoragePermissions(Context activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions((Activity) activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 屏幕录制
     */
    private void doRecordScreen(Context activity) {
        //请求码
        int requestCode = 1;

        mediaProjectionManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            mediaProjectionManager = (MediaProjectionManager) activity.
                    getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        }
        if (mediaProjectionManager != null){
            Intent intent = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                intent = mediaProjectionManager.createScreenCaptureIntent();
            }
            PackageManager packageManager = activity.getPackageManager();
            if (packageManager.resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY) != null){
                //存在录屏授权的Activity
                Log.d("TEST", "启动业务");
                startActivityForResult(intent, requestCode);
            }else {
                Toast.makeText(activity,"取消录制",Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            try {
//
                mp = mediaProjectionManager.getMediaProjection(resultCode, data);

                File file = new File("/sdcard/test.mp4");
                thread = new RecordService(1024, 600, 3072, 1, mp, file.getAbsolutePath());
                thread.start();
//                Intent intent = new Intent(this, RecordService.class);
//                VideoParam param = new VideoParam(1024, 600, 60000, 1, mp, file.getAbsolutePath());
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("video", param);
//                intent.putExtras(bundle);
//                startService(intent);
                Log.d("TEST", "录屏开始");

//                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "停止录屏", Toast.LENGTH_SHORT).show();
        }
    }


}
