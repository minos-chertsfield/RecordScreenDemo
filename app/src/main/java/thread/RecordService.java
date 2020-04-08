package thread;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.IOException;

/**
 * @Author : Minos
 * @Description: 描述 录屏线程
 * @CreateDate: 2020/4/1 18:12
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/1 18:12
 **/
public class RecordService extends Thread {

    private static final String TAG = "RecordService";

    VirtualDisplay virtualDisplay;
    MediaRecorder mediaRecorder;
    MediaProjection mediaProjection;

    private int width;
    private int height;
    private int bitRate;
    private int dpi;
    private MediaProjection mp;
    private String targetPath;

    public RecordService(int width, int height, int bitRate, int dpi, MediaProjection mp, String targetPath) {
        this.width = width;
        this.height = height;
        this.bitRate = bitRate;
        this.dpi = dpi;
        this.mp = mp;
        this.targetPath = targetPath;
    }

    /**
     * 初始化
     */
    public void init() {

        Log.d(TAG, "初始化视频录制控件");
        //实例化
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
//        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setVideoSize(width, height);
        //帧率
        mediaRecorder.setVideoFrameRate(60);
        mediaRecorder.setVideoEncodingBitRate(bitRate);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        mediaRecorder.setOutputFile(targetPath);
        Log.d(TAG, "初始化完成");

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "准备就绪");

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            Log.d(TAG, "线程开启");
            //初始化
            init();
            virtualDisplay = mp.createVirtualDisplay(TAG, width, height, dpi, DisplayManager.VIRTUAL_DISPLAY_FLAG_PUBLIC, mediaRecorder.getSurface(), null, null);
            Log.d(TAG, "屏幕显示");
            mediaRecorder.start();
            Log.d(TAG, "录屏已开始");
            try {
                Thread.sleep(60000);     //时长1分钟
            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                Log.d(TAG, "资源释放");
                release();
            }
        }

    }

    /**
     * 释放资源
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void release() {

        if (virtualDisplay != null) {
            virtualDisplay.release();
            virtualDisplay = null;
        }

        if (mediaRecorder != null) {
            mediaRecorder.setOnErrorListener(null);
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
        }

        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }

        Log.d(TAG, "线程资源释放");


    }

}
