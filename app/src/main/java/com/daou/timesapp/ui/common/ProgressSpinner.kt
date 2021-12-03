package com.daou.timesapp.ui.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.daou.timesapp.R

class ProgressSpinner(
    context: Context
) : Dialog(context, R.style.DialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.progress_spinner)
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }
}