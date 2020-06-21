package com.inderbagga.foundation.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.inderbagga.foundation.R

/**
 * Created by Inder Bagga on 21/06/20.
 * Email er[dot]inderbagga[at]gmail[dot]com
 */
object BindingUtils {
    @JvmStatic
    @BindingAdapter("avatar")
    fun loadAvatar(view: ImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(view.context)
                .load(it).apply(RequestOptions().circleCrop())
                .placeholder(R.drawable.bg_light_grey)
                .error(R.drawable.bg_black)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("repoType")
    fun loadAvatar(view: TextView, boolean: Boolean?) {
        boolean?.let {
            if(it){
                view.text="Private"
            }else{
                view.text="Public"
            }
        }
    }
}