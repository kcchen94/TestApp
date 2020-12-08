package com.example.mytest.object;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.mytest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import okhttp3.internal.Util;

public class ImageLoaderOptions {
    public static DisplayImageOptions forProductImage(final Context context) {
        return new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .showImageOnLoading(getDefaultImage(context))
                .showImageForEmptyUri(getDefaultImage(context))
                .showImageOnFail(getDefaultImage(context))
                .build();
    }

    public static int getDefaultImage(final Context context) {
        return R.drawable.coming_soon;
    }

    public static DisplayImageOptions defaultInit(final Context context) {
        final Drawable defaultImage = context.getResources().getDrawable(R.drawable.coming_soon);
        return new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisk(true)
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .build();
    }
}
