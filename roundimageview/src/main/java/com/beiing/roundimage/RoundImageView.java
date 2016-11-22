package com.beiing.roundimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.beiing.roundimageview.R;

/**
 * Created by chenliu on 2016/8/19.<br/>
 * 描述：
 * </br>
 */
public class RoundImageView extends AbsRoundImageView {

    private float leftTopRadius;

    private float rightTopRadius;

    private float rightBottomRadius;

    private float leftBottomRadius;

    public RoundImageView(Context context) {
        this(context, null, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    protected void initAttrs(AttributeSet attrs) {
        super.initAttrs(attrs);
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            leftTopRadius = ta.getDimension(R.styleable.RoundImageView_leftTopRadius, 0);
            rightTopRadius = ta.getDimension(R.styleable.RoundImageView_rightTopRadius, 0);
            rightBottomRadius = ta.getDimension(R.styleable.RoundImageView_rightBottomRadius, 0);
            leftBottomRadius = ta.getDimension(R.styleable.RoundImageView_leftBottomRadius, 0);
            ta.recycle();
        }
    }

    @Override
    protected void initRoundPath() {
        roundPath.reset();
        final int width = getWidth();
        final int height = getHeight();
        leftTopRadius = Math.min(leftTopRadius, Math.min(width, height) * 0.5f);
        rightTopRadius = Math.min(rightTopRadius, Math.min(width, height) * 0.5f);
        rightBottomRadius = Math.min(rightBottomRadius, Math.min(width, height) * 0.5f);
        leftBottomRadius = Math.min(leftBottomRadius, Math.min(width, height) * 0.5f);

        RectF rect = new RectF(0, 0, width, height);
        roundPath.addRoundRect(rect,
                new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius,
                        rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius},
                Path.Direction.CW);
    }

    @Override
    protected void initBorderPath() {
        borderPath.reset();
        /**
         * 乘以0.5会导致border在圆角处不能包裹原图
         */
        final float halfBorderWidth = borderWidth * 0.35f;
        final int width = getWidth();
        final int height = getHeight();
        leftTopRadius = Math.min(leftTopRadius, Math.min(width, height) * 0.5f);
        rightTopRadius = Math.min(rightTopRadius, Math.min(width, height) * 0.5f);
        rightBottomRadius = Math.min(rightBottomRadius, Math.min(width, height) * 0.5f);
        leftBottomRadius = Math.min(leftBottomRadius, Math.min(width, height) * 0.5f);

        RectF rect = new RectF(halfBorderWidth, halfBorderWidth,
                width - halfBorderWidth, height - halfBorderWidth);
        borderPath.addRoundRect(rect,
                new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius,
                        rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius},
                Path.Direction.CW);
    }
}
