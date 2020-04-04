package com.lovisgod.safehaven.Util

import android.content.Intent

class FileChooser {
    fun openImageChooser(): Intent {
        val x = Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
        }
        return x
    }

    fun openVideoChooser(): Intent {
        val x = Intent(Intent.ACTION_PICK).also {
            it.type = "video/*"
        }
        return x
    }
}