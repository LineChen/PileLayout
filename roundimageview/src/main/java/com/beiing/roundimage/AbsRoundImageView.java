package com.beiing.roundimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.beiing.roundimageview.R;

/**
 * Created by chenliu on 2016/8/19.<br/>
 * 描述：
 * </br>
 */
public abstract class AbsRoundImageView extends ImageView {

    private static final PorterDuffXfermode xFermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);

    private Paint mBitmapPaint;

    /**
     * 图片可视区
     */
    protected Path roundPath;

    /**
     * 图片边框
     */
    protected Path borderPath;

    /**
     * 边框宽度
     */
    protected float borderWidth;

    /**
     * 边框颜色
     */
    protected int borderColor;

    private Paint borderPaint;

    public AbsRoundImageView(Context context) {
        this(context, null, 0);
    }

    public AbsRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbsRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    protected void initAttrs(AttributeSet attrs){
        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AbsRoundImageView);
            borderWidth = ta.getDimension(R.styleable.AbsRoundImageView_round_borderWidth, 0);
            borderColor = ta.getColor(R.styleable.AbsRoundImageView_round_borderColor, 0);
            ta.recycle();
        }
    }

    private void init() {
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        roundPath = new Path();
        borderPath = new Path();

        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStrokeWidth(borderWidth);

        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            initBorderPath();
            initRoundPath();
        }
    }


    /**
     * 初始化边框Path
     */
    protected abstract void initBorderPath();

    /**
     * 初始化图片区域Path
     */
    protected abstract void initRoundPath();


    /**
     * 获取图片区域纯颜色Bitmap
     * @return
     */
    protected Bitmap getRoundBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        canvas.drawPath(roundPath, paint);
        return bitmap;
    }

    private void drawBorder(Canvas canvas) {
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        canvas.drawPath(borderPath, borderPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawImage(canvas);
        drawBorder(canvas);
    }

    private void drawImage(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(!isInEditMode() && drawable != null) {
            try {
                Bitmap bitmap;
                if (drawable instanceof ColorDrawable) {
                    bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
                } else {
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                }
                Canvas drawCanvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, drawCanvas.getWidth(), drawCanvas.getHeight());
                drawable.draw(drawCanvas);

                Bitmap roundBm = getRoundBitmap();
                mBitmapPaint.reset();
                mBitmapPaint.setFilterBitmap(false);
                mBitmapPaint.setXfermode(xFermode);
                drawCanvas.drawBitmap(roundBm, 0, 0, mBitmapPaint);
                mBitmapPaint.setXfermode(null);
                canvas.drawBitmap(bitmap, 0, 0, mBitmapPaint);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
























