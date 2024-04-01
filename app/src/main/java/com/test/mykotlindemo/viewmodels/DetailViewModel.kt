package com.test.mykotlindemo.viewmodels

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.mykotlindemo.DetailViewActivity.Companion.REQUEST_CODE_CAPTURE_IMAGE
import java.io.ByteArrayOutputStream

class DetailViewModel : ViewModel() {

    private val _thumbnailBitmap = MutableLiveData<Bitmap>()
    val thumbnailBitmap: LiveData<Bitmap>
        get() = _thumbnailBitmap

    private val _imageByteArray = MutableLiveData<ByteArray>()
    val imageByteArray: LiveData<ByteArray>
        get() = _imageByteArray


    fun setImageByteArray(byteArray: ByteArray) {
        _imageByteArray.value = byteArray
    }

    fun capturePhoto(context: Context) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(context.packageManager) != null) {
            (context as Activity).startActivityForResult(cameraIntent, REQUEST_CODE_CAPTURE_IMAGE)
        }
    }


    fun processCapturedImage(data: Intent?) {
        val bitmap = data?.extras?.get("data") as? Bitmap
        bitmap?.let {
            _thumbnailBitmap.postValue(it)
            val byteArrayOutputStream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            setImageByteArray(byteArray)
        }
    }
}
