//package com.minos.recordscreendemo;
//
//import android.app.Service;
//import android.content.Intent;
//import android.hardware.display.DisplayManager;
//import android.hardware.display.VirtualDisplay;
//import android.media.MediaRecorder;
//import android.media.projection.MediaProjection;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import java.io.IOException;
//
//public class RecordService extends Service {
//
//    private static final String TAG = "Record";
//
//    VirtualDisplay virtualDisplay;
//    MediaRecorder mediaRecorder;
//    MediaProjection mediaProjection;
//
//    int width;
//    int height;
//    int bitRate;
//    int dpi;
//    MediaProjection mp;
//    String targetPath;
//    VideoParam param;
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//
//
//        width = param.getWidth();
//        height = param.getHeight();
//        dpi = param.getDpi();
//        bitRate = param.getBitRate();
//        mp = param.getMp();
//        targetPath = param.getTargetPath();
//
//        Log.d(TAG, "服务开启");
//        //初始化
//        init();
//        virtualDisplay = mp.createVirtualDisplay(TAG, width, height, dpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, mediaRecorder.getSurface(), null, null);
//        Log.d(TAG, "屏幕显示");
//        mediaRecorder.start();
//        Log.d(TAG, "录屏已开始");
//        try {
//            Thread.sleep(60000);     //时长1分钟
//        } catch (InterruptedException e) {
//
//            Thread.currentThread().interrupt();
//            Log.d(TAG, "资源释放");
//            release();
//        }
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    /**
//     * 初始化资源
//     */
//    public void init() {
//
//        Log.d(TAG, "初始化视频录制控件");
//        //实例化
//        mediaRecorder = new MediaRecorder();
//        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
////        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        mediaRecorder.setVideoSize(width, height);
//        //帧率
//        mediaRecorder.setVideoFrameRate(60);
//        mediaRecorder.setVideoEncodingBitRate(bitRate);
//        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
////        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
//
//        mediaRecorder.setOutputFile(targetPath);
//        Log.d(TAG, "初始化完成");
//
//        try {
//            mediaRecorder.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.d(TAG, "准备就绪");
//    }
//
//    /**
//     * 释放资源
//     */
//    public void release() {
//
//        if (virtualDisplay != null) {
//            virtualDisplay.release();
//            virtualDisplay = null;
//        }
//
//        if (mediaRecorder != null) {
//            mediaRecorder.setOnErrorListener(null);
//            mediaRecorder.stop();
//            mediaRecorder.reset();
//            mediaRecorder.release();
//        }
//
//        if (mediaProjection != null) {
//            mediaProjection.stop();
//            mediaProjection = null;
//        }
//
//        Log.d(TAG, "线程资源释放");
//    }
//
//}
