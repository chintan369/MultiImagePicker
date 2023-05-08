package com.creativechintak.multiimagepicker

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ckdroid.dynamicpermissions.PermissionStatus
import com.ckdroid.dynamicpermissions.PermissionUtils

class StartImagePickerActivity : AppCompatActivity() {

    private var maxSelectionLimit = 0
    private var showAlbums = false

    companion object {
        private const val MAX_PICTURE_LIMIT = 30

        private val PERMISSIONS_GALLERY_WITH_WRITE: Array<String>
            get() {
                val permissions = mutableListOf<String>()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
                } else {
                    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                }

                return permissions.toTypedArray()
            }

        private const val REQUEST_PICK_IMAGE_PERMISSION = 1

        const val REQUEST_PICK_IMAGE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initParams()
        checkForGalleryPermission()
    }

    private fun initParams() {
        maxSelectionLimit = intent.getIntExtra(
            Constants.BUNDLE_MAX_SELECTION_LIMIT,
            MAX_PICTURE_LIMIT
        )

        if (maxSelectionLimit <= 0 || maxSelectionLimit > MAX_PICTURE_LIMIT) {
            maxSelectionLimit = MAX_PICTURE_LIMIT
        }

        showAlbums = intent.getBooleanExtra(Constants.BUNDLE_SHOW_ALBUMS, false)
    }

    private fun checkForGalleryPermission(hasAskedOnce: Boolean = false) {
        val result = PermissionUtils.checkAndRequestPermissions(
            this, PERMISSIONS_GALLERY_WITH_WRITE.toMutableList(),
            REQUEST_PICK_IMAGE_PERMISSION, hasAskedOnce
        )

        when (result.finalStatus) {
            PermissionStatus.ALLOWED -> {
                openImagePickerActivity()
            }

            PermissionStatus.DENIED_PERMANENTLY -> {
                if (hasAskedOnce) {

                }
            }

            else -> Unit
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PICK_IMAGE_PERMISSION) {
            checkForGalleryPermission(true)
        }
    }

    private fun openImagePickerActivity() {
        val intent = Intent(this, GalleryActivity::class.java)
        intent.putExtra(Constants.BUNDLE_SHOW_ALBUMS, showAlbums)
        intent.putExtra(Constants.BUNDLE_MAX_SELECTION_LIMIT, maxSelectionLimit)
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_PICK_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                setResult(resultCode, data)
            }
            onBackPressed()
        }
    }

}