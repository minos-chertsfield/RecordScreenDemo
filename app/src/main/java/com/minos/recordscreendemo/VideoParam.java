package com.minos.recordscreendemo;

import android.media.projection.MediaProjection;

import java.io.Serializable;

/**
 * @Author : Minos
 * @Description: 描述
 * @CreateDate: 2020/4/8 15:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/4/8 15:47
 **/
public class VideoParam implements Serializable {

    private int width;
    private int height;
    private int bitRate;
    private int dpi;
    private MediaProjection mp;
    private String targetPath;

    public VideoParam(int width, int height, int bitRate, int dpi, MediaProjection mp, String targetPath) {
        this.width = width;
        this.height = height;
        this.bitRate = bitRate;
        this.dpi = dpi;
        this.mp = mp;
        this.targetPath = targetPath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBitRate() {
        return bitRate;
    }

    public void setBitRate(int bitRate) {
        this.bitRate = bitRate;
    }

    public int getDpi() {
        return dpi;
    }

    public void setDpi(int dpi) {
        this.dpi = dpi;
    }

    public MediaProjection getMp() {
        return mp;
    }

    public void setMp(MediaProjection mp) {
        this.mp = mp;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}
