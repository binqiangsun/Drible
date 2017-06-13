package com.sunbinqiang.drible.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.sunbinqiang.drible.R;
import com.sunbinqiang.drible.util.DribleUtils;


/**
 * Created by sunbinqiang on 9/2/16.
 */

public class NetworkImageView extends android.support.v7.widget.AppCompatImageView {

    private int roundRadius ;
    private boolean isCircle;

    public NetworkImageView(Context context) {
        this(context, null);
    }

    public NetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetworkImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NetworkImageView, defStyleAttr, 0);
        String imageUrl = a.getString(R.styleable.NetworkImageView_imageUrl);
        roundRadius = a.getInt(R.styleable.NetworkImageView_roundRadius, 0);
        isCircle = a.getBoolean(R.styleable.NetworkImageView_enableCircle, false);
        a.recycle();
        setImageUrl(imageUrl);
        setScaleType(ScaleType.CENTER_CROP);
    }

    public void setImageUrl(String imageUrl){
        setImageUrl(imageUrl, 0, 0);
    }

    /**
     * 当列表中图片的尺寸会变化的时候， 传入width， height参数，可以避免图片显示不正常
     * @param imageUrl
     * @param width
     * @param height
     */
    public void setImageUrl(String imageUrl, int width, int height){
        setImageUrl(imageUrl, Priority.NORMAL, width, height);
    }

    public void setImageUrl(String imageUrl, Priority priority, int width, int height){
        setImageUrl(imageUrl, "", priority, width, height);
    }

    /**
     * 优先级参数： 可以自定义高的优先级
     * @param imageUrl
     * @param thumbUrl  缩略图
     * @param priority
     * @param width
     * @param height
     */
    public void setImageUrl(String imageUrl, String thumbUrl, Priority priority, int width, int height){
        Activity activity = DribleUtils.getActivityFromContext(getContext());
        if(TextUtils.isEmpty(imageUrl) || activity == null){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed()) {
            return;
        }
        DrawableTypeRequest glideRequest = Glide
                .with(activity)
                .load(imageUrl);
        if (!TextUtils.isEmpty(thumbUrl)) {
            Log.d("networkimageview", "thumn:"+thumbUrl);
            DrawableRequestBuilder<String> requestBuilder = Glide.with(activity).load(thumbUrl);
            glideRequest.thumbnail(requestBuilder);
        }
        if (width > 0 && height > 0) {
            glideRequest.override(width, height);
        }
        if (priority != Priority.NORMAL){
            glideRequest.priority(priority);
        }
        if(roundRadius > 0) {
            glideRequest.transform(new GlideInstance.GlideRoundTransform(getContext(), roundRadius));
        }else if(isCircle){
            glideRequest.transform(new GlideInstance.CircleTransform(getContext()));
        }
        glideRequest.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        glideRequest.into(this);
    }


    /**
     * 设置圆角
     * @param imageUrl
     * @param radius
     */
    public void setImageUrl(String imageUrl, int radius){
        roundRadius = radius;
        setImageUrl(imageUrl);
    }

    /**
     * 加载本地图片
     * @param uri
     */
    public void setImageUri(Uri uri){
        if(getContext() != null) {
            Glide.with(getContext())
                    .load(uri)
                    .into(this);
        }
    }

    public void setImageResource(int resId){
        if(getContext() != null) {
            Glide.with(getContext())
                    .load(resId)
                    .into(this);
        }
    }

    public static class GlideInstance {

        public static class GlideRoundTransform extends BitmapTransformation {

            private static float radius = 0f;

            public GlideRoundTransform(Context context) {
                this(context, 4);
            }

            public GlideRoundTransform(Context context, int dp) {
                super(context);
                this.radius = Resources.getSystem().getDisplayMetrics().density * dp;
            }

            @Override
            protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                return roundCrop(pool, toTransform);
            }

            private static Bitmap roundCrop(BitmapPool pool, Bitmap source) {
                if (source == null) return null;

                Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
                if (result == null) {
                    result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
                }

                Canvas canvas = new Canvas(result);
                Paint paint = new Paint();
                paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                paint.setAntiAlias(true);
                RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
                canvas.drawRoundRect(rectF, radius, radius, paint);
                return result;
            }

            @Override
            public String getId() {
                return getClass().getName() + Math.round(radius);
            }
        }

        public static class CircleTransform extends BitmapTransformation {
            public CircleTransform(Context context) {
                super(context);
            }

            @Override
            protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                return circleCrop(pool, toTransform);
            }

            private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
                if (source == null) return null;

                int size = Math.min(source.getWidth(), source.getHeight());
                int x = (source.getWidth() - size) / 2;
                int y = (source.getHeight() - size) / 2;

                // TODO this could be acquired from the pool too
                Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

                Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
                if (result == null) {
                    result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
                }

                Canvas canvas = new Canvas(result);
                Paint paint = new Paint();
                paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                paint.setAntiAlias(true);
                float r = size / 2f;
                canvas.drawCircle(r, r, r, paint);
                return result;
            }

            @Override
            public String getId() {
                return getClass().getName();
            }
        }
    }
}
