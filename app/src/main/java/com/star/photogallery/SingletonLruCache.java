package com.star.photogallery;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class SingletonLruCache extends LruCache<String, Bitmap> {

    private static SingletonLruCache sSingletonLruCache;

    private SingletonLruCache(int maxSize) {
        super(maxSize);
    }

    public static SingletonLruCache getInstance(int maxSize) {
        if (sSingletonLruCache == null) {
            synchronized (SingletonLruCache.class) {
                if (sSingletonLruCache == null) {
                    sSingletonLruCache = new SingletonLruCache(maxSize);
                }
            }
        }
        return sSingletonLruCache;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
    }

    public static Bitmap getBitmapFromMemoryCache(String key) {
        if (key == null) {
            return null;
        }
        return sSingletonLruCache.get(key);
    }

    public static void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            sSingletonLruCache.put(key, bitmap);
        }
    }
}
