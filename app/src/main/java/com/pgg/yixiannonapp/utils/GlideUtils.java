package com.pgg.yixiannonapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.pgg.yixiannonapp.R;

public class GlideUtils {
    public static void loadImage(Context context, String url, ImageView imageView){
        Glide.with(context).load(url).centerCrop().into(imageView);
    }

    public static void loadImageFitCenter(Context context,String url,ImageView imageView){
        Glide.with(context).load(url).fitCenter().into(imageView);
    }

    public static void loadUrlImage(Context context, String url, final ImageView imageView){
        Glide.with(context).load(url).placeholder(R.drawable.icon_back).error(R.drawable.icon_back).centerCrop().into(
                new SimpleTarget<GlideDrawable> () {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        imageView.setImageDrawable(resource);
                    }
                });
    }
}
