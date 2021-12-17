package com.modules.newsbreeze.utils

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

class AppUtils {

    companion object{

        fun loadSvgImage(context: Context,resourceId:Int,target:ImageView){
            Glide.with(context)
                .`as`(PictureDrawable::class.java)
                .load(resourceId)
                .transition(withCrossFade())
                .listener(SvgSoftwareLayerSetter())
                .into(target)

        }
    }
}