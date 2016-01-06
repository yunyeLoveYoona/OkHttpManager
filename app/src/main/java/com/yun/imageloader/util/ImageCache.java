package com.yun.imageloader.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * 图片的内存缓存
 * @author yunye
 *
 */
public class ImageCache extends LruCache<String, Bitmap>{
	private final static int MAX_SIZE=10;
	private static ImageCache imageCache;
    public static ImageCache getInstance(){
    	if(imageCache==null){
    		imageCache=new ImageCache(MAX_SIZE*1024);
    	}
    	return imageCache;
    }
	private ImageCache(int maxSize) {
		super(maxSize);
	}

}
