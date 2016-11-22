package com.beiing.roundimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by chenliu on 2016/8/19.<br/>
 * 描述：
 * </br>
 */
public class CircleImageView extends AbsRoundImageView {


    public CircleImageView(Context context) {
        this(context, null, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        super.initAttrs(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = Math.max(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(width, width);
    }

    @Override
    protected void initRoundPath() {
        roundPath.reset();
        final int width = getWidth();
        final int height = getHeight();
        final float cx = width * 0.5f;
        final float cy = height * 0.5f;
        final float radius = Math.min(width, height) * 0.5f;
        roundPath.addCircle(cx, cy, radius, Path.Direction.CW);
    }

    @Override
    protected void initBorderPath() {
        borderPath.reset();
        final float halfBorderWidth = borderWidth * 0.5f;
        final int width = getWidth();
        final int height = getHeight();
        final float cx = width * 0.5f;
        final float cy = height * 0.5f;
        final float radius = Math.min(width, height) * 0.5f;
        borderPath.addCircle(cx, cy, radius - halfBorderWidth, Path.Direction.CW);
    }


}
