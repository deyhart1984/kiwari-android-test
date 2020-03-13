package com.realtimechat.app.utils

import android.content.Context
import android.widget.Toast

object ToastMessageUtils {
    fun message(context: Context, msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}
