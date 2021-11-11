package com.creative.multiimagepicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.creative.multiimagepicker.databinding.ActivityMainBinding
import com.creativechintak.multiimagepicker.builder.MultiImagePicker

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPickImage.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        MultiImagePicker.with(this)
            .setSelectionLimit(10)
            .open()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == MultiImagePicker.REQUEST_PICK_MULTI_IMAGES && resultCode == RESULT_OK){
            val result = MultiImagePicker.Result(data)
            if(result.isSuccess()){
                val imageList = result.getImageList()

                imageList.forEach {
                    Log.e("Image Uri ->",it.toString())
                }
            }
        }
    }
}