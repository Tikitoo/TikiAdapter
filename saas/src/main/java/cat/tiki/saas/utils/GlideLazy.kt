package cat.tiki.saas.utils

import android.graphics.Bitmap
import android.widget.ImageView

/**
 * Created by Yifa Liang on 2019-08-29.
 */
inline fun ImageView.load(url:String, width: Int, height: Int) {
    this?.layoutParams.width = width
    this?.layoutParams.height = height

    print("imageUrl: " + url)
    GlideApp.with(this?.context)
        .asBitmap()
        .load(url)
        .dontAnimate()
        .override(width, height)
        .encodeFormat(Bitmap.CompressFormat.JPEG)
        .centerCrop()
        .into(this)

}

