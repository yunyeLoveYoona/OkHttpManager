package com.yun.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;


/**
 *  图片获取线程
 * 
 * @author yunye
 * 
 */
public class LoadRunnable implements Runnable {
	private ImageView imageView;
	private String url;
	private int defaultBackround;
	private int width;
	private int height;
	private boolean isCache;
	private Handler handler;
	private Drawable errorImage;
	private Bitmap bitmap;

	public LoadRunnable(ImageView imageView, String url, int defaultBackround,
			Drawable errorImage, int width, int height, boolean isCache,
			Handler handler) {
		this.imageView = imageView;
		this.url = url;
		this.defaultBackround = defaultBackround;
		this.width = width;
		this.height = height;
		this.isCache = isCache;
		this.handler = handler;
		this.errorImage = errorImage;
	}

	@Override
	public void run() {
		if (url.contains("http://")) {
			// 锟斤拷锟斤拷图片
			try {
				final Bitmap bitmap = new ImageDownLoad().downLoad(url, width,
						height);
				if (bitmap != null) {
					ImageCache.getInstance().put(
							url + width + "*" + height, bitmap);
					if (isCache) {
						Message message = handler.obtainMessage();
						message.obj = url + width + "*" + height;
						handler.sendMessage(message);
					}
				}
				handler.post(new Runnable() {
					@Override
					public void run() {
						if (bitmap == null) {
							if (errorImage != null)
								imageView.setImageDrawable(errorImage);
							else if (defaultBackround != 0)
								imageView.setImageResource(defaultBackround);
							imageView = null;
							errorImage = null;
						} else {
							imageView.setImageBitmap(bitmap);
							imageView = null;
							errorImage = null;
						}

					}
				});
			} catch (Exception e) {
			}

		} else {
			// 锟斤拷锟斤拷图片
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(url, options);
			options.inSampleSize = options.outWidth / width >= options.outHeight
					/ height ? options.outWidth / width : options.outHeight
					/ height;
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(url, options);
			ImageCache.getInstance().put(
					url + width + "*" + height, bitmap);
			handler.post(new Runnable() {
				@Override
				public void run() {
					imageView.setImageBitmap(bitmap);
					imageView = null;
					errorImage = null;
					bitmap = null;
				}
			});
		}
	}
}
