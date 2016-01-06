package com.yun.imageloader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.yun.imageloader.util.ImageCache;
import com.yun.imageloader.util.ImageDiskLruCache;
import com.yun.imageloader.util.LoadRunnable;

/**
 * 图片加载缓存 使用了内存缓存和磁盘缓存 可以加载网络图片，也可加载本地图片
 *
 * @author yunye
 *
 */
public class ImageLoader {
    private static ImageLoader imageLoader;
    private final int LOAD_COUNT = 1;// 同时加载图片的最大线程数
    private ExecutorService executorServie;
    private final int DEFAULT_WIDTH = 0;
    private final int DEFAULT_HEIGHT = 0;
    private ImageDiskLruCache diskLruCache;
    private Resources resources;

    public static ImageLoader getInstance(Context context) {
        if (imageLoader == null) {
            imageLoader = new ImageLoader(context);
        }
        return imageLoader;
    }

    private ImageLoader(Context context) {
        executorServie = Executors.newFixedThreadPool(LOAD_COUNT);
        diskLruCache = new ImageDiskLruCache(context, "image",
                10 * 1024 * 1024, CompressFormat.JPEG, 100);
        resources = context.getResources();
    }

    public void load(ImageView imageView, String url, int defaultBackround,
                     int width, int height, boolean isCache, final Drawable errorImage) {
        if (url == null || !url.contains("http://") && errorImage != null) {
            imageView.setImageDrawable(errorImage);
        } else {
            if (ImageCache.getInstance().get(
                    url + width + "*" + height) != null) {
                imageView.setImageBitmap(ImageCache.getInstance().get(url + width + "*" + height));
            } else if (diskLruCache.containsKey(url + width + "*" + height)) {
                imageView.setImageBitmap(diskLruCache.getBitmap(url + width
                        + "*" + height));
            } else {
                if (defaultBackround != 0) {
                    imageView.setImageResource(defaultBackround);
                }
                final LoadRunnable run = new LoadRunnable(imageView, url,
                        defaultBackround, errorImage, width, height, isCache,
                        handler);
                executorServie.execute(run);
                // imageView.setOnClickListener(new OnClickListener() {
                // @Override
                // public void onClick(View v) {
                // ImageView imageView = (ImageView) v;
                // if (imageView.getDrawable() == null
                // || imageView.getDrawable() == errorImage) {
                // executorServie.execute(run);
                // }
                // }
                // });
            }
        }
    }

    public void load(ImageView imageView, String url, int defaultBackround,
                     int width, int height, Drawable errorImage) {
        load(imageView, url, defaultBackround, width, height, false, errorImage);
    }

    public void load(ImageView imageView, String url, int width, int height,
                     Drawable errorImage) {
        load(imageView, url, 0, width, height, false, errorImage);
    }

    public void load(ImageView imageView, String url, Drawable errorImage) {
        load(imageView,
                url,
                0,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, false,
                errorImage);
    }

    public void load(ImageView imageView, String url, int defaultBackround,
                     Drawable errorImage) {
        load(imageView,
                url,
                defaultBackround,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, false,
                errorImage);
    }

    public void load(ImageView imageView, String url, Drawable errorImage,
                     boolean isCache) {
        load(imageView,
                url,
                0,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, isCache,
                errorImage);
    }

    public void load(ImageView imageView, String url, int defaultBackround,
                     boolean isCache) {
        load(imageView,
                url,
                defaultBackround,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, isCache,
                null);
    }

    public void load(ImageView imageView, String url, int defaultBackround,
                     boolean isCache, Drawable errorImage) {
        load(imageView,
                url,
                defaultBackround,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, isCache,
                errorImage);
    }

    public void load(ImageView imageView, String url, int defaultBackround,
                     int width, int height) {
        load(imageView, url, defaultBackround, width, height, false, null);
    }

    public void load(ImageView imageView, String url, int width, int height) {
        load(imageView, url, 0, width, height, false, null);
    }

    public void load(ImageView imageView, String url) {
        load(imageView,
                url,
                0,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, false, null);
    }

    public void load(ImageView imageView, String url, int defaultBackround) {
        load(imageView,
                url,
                defaultBackround,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, false, null);
    }

    public void load(ImageView imageView, String url, boolean isCache) {
        load(imageView,
                url,
                0,
                imageView.getLayoutParams().width > 0 ? imageView
                        .getLayoutParams().width : DEFAULT_WIDTH,
                imageView.getLayoutParams().height > 0 ? imageView
                        .getLayoutParams().height : DEFAULT_HEIGHT, isCache,
                null);
    }

    public void loadLocal(View view, int resource) {
        int width = view.getLayoutParams().width > 0 ? view.getLayoutParams().width
                : DEFAULT_WIDTH;
        int height = view.getLayoutParams().height > 0 ? view.getLayoutParams().height
                : DEFAULT_HEIGHT;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(resources, resource, options);
        if (width != 0 && height != 0)
            options.inSampleSize = options.outWidth / width >= options.outHeight
                    / height ? options.outWidth / width : options.outHeight
                    / height;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resource,
                options);
        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj != null) {
                if (!diskLruCache.containsKey(msg.obj.toString())) {
                    diskLruCache.putBitmap(msg.obj.toString(),
                            ImageCache.getInstance().get(msg.obj.toString()));
                }
            }
            super.handleMessage(msg);
        }
    };

    public void stopAllLoad() {
        executorServie.shutdownNow();
    }

    public void clearDiskLruCache() {
        imageLoader = null;
        diskLruCache.clearCache();
    }

    public Bitmap getBitmap(String url) {
        if (diskLruCache.containsKey(url)) {
            return diskLruCache.getBitmap(url);
        } else {
            return null;
        }
    }

}
