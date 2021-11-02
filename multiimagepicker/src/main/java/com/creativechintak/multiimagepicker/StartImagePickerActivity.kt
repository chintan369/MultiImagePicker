package com.creativechintak.multiimagepicker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class StartImagePickerActivity : AppCompatActivity() {

    companion object{

        private var activity: Activity? = null

        private const val MAX_PICTURE_LIMIT = 30

        private var maxPictureLimit: Int = MAX_PICTURE_LIMIT



    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkForGalleryPermission()
    }

    private fun checkForGalleryPermission() {

    }
}