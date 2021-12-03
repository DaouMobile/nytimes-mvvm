package com.daou.timesapp.common.ext

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}