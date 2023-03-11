package com.manish.dkb.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.transform.CircleCropTransformation
import com.manish.dkb.R

fun ImageView.loadProfilePhoto(photoUrl: String?, context: Context) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    this.load(photoUrl) {
        crossfade(true)
        placeholder(circularProgressDrawable)
        error(R.drawable.ic_launcher_foreground)
        transformations(CircleCropTransformation())
    }
}
