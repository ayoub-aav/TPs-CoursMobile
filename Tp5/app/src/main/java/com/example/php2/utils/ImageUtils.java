package com.example.php2.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ImageUtils {
    public static InputStream base64ToInputStream(String base64Image) {
        byte[] imageBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return new ByteArrayInputStream(imageBytes);
    }

    public static GlideUrl getGlideUrl(String base64Image) {
        try {
            return new GlideUrl("data:image/jpeg;base64," + base64Image,
                    new LazyHeaders.Builder().addHeader("Content-Type", "image/jpeg").build());
        } catch (IllegalArgumentException e) {
            Log.e("ImageUtils", "Invalid Base64 string", e);
            return null;
        }
    }
}