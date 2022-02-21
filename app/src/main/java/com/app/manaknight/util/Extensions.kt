package com.app.manaknight.util

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.app.manaknight.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Activity.snackBar(
    msg: String,
    length: Int = Snackbar.LENGTH_SHORT,
    color: Int = R.color.white
) =
    Snackbar.make(findViewById(android.R.id.content), msg, length)
        .setBackgroundTint(resources.getColor(R.color.primary, resources.newTheme()))
        .setTextColor(resources.getColor(color, resources.newTheme()))
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
        .show()

fun Fragment.snackBar(msg: String) = activity!!.snackBar(msg)

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}