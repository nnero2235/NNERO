package com.nnero.nnero.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by nnero on 16/3/16.
 *
 * 相机 渲染 画面的view
 */
public class CameraView extends SurfaceView implements SurfaceHolder {

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void addCallback(Callback callback) {

    }

    @Override
    public void removeCallback(Callback callback) {

    }

    @Override
    public boolean isCreating() {
        return false;
    }

    @Override
    public void setType(int type) {

    }

    @Override
    public void setFixedSize(int width, int height) {

    }

    @Override
    public void setSizeFromLayout() {

    }

    @Override
    public void setFormat(int format) {

    }

    @Override
    public Canvas lockCanvas() {
        return null;
    }

    @Override
    public Canvas lockCanvas(Rect dirty) {
        return null;
    }

    @Override
    public void unlockCanvasAndPost(Canvas canvas) {

    }

    @Override
    public Rect getSurfaceFrame() {
        return null;
    }

    @Override
    public Surface getSurface() {
        return null;
    }
}
