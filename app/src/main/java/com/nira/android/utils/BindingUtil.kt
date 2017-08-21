package com.suvesh.utils

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.net.Uri
import android.support.v7.widget.Toolbar
import android.view.View
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.nira.android.utils.Utils

/**
 * Created by Suvesh on 07/08/2017 AD.
 */
@BindingAdapter("fresco:actualImageUri", "app:w", "app:h")
fun loadImage(view: SimpleDraweeView, url: String, w: Int, h: Int) {
    val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
            .setResizeOptions(ResizeOptions(Utils.dpToPx(w), Utils.dpToPx(h))).build()
    val controller = Fresco.newDraweeControllerBuilder().setOldController(view.controller)
            .setImageRequest(request).build()
    view.controller = controller
}

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    return if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("app:menuFlag", "app:menu")
fun handleMenu(view: Toolbar, menuFlag: Boolean, menuResourceId : Int) {
    if (menuFlag)
        view.inflateMenu(menuResourceId)
    else
        view.menu?.clear()
}


@BindingAdapter("android:background")
fun setBackgroundResource(view: View, resource: Int) {
    view.setBackgroundResource(resource)
}
