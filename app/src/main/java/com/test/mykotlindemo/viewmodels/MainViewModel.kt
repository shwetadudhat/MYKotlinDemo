package com.test.mykotlindemo.viewmodels

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.test.mykotlindemo.Model.DummyProductsList
import com.test.mykotlindemo.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository): ViewModel()  {

    init {
        viewModelScope.launch(Dispatchers.IO){
//            repository.getProducts(1)
            repository.getProductsFromRoom()//data from room database
        }
    }

    val products : LiveData<DummyProductsList>
        get() = repository.products


    fun loadNextPage(skip: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts(skip)
        }
    }

    // Inside onCameraClicked function in MainViewModel
    fun onCameraClicked() {
        // Handle camera click event
        // For example, open the camera
        val context = ApplicationProvider.getApplicationContext<Context>()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(viewFinder.createSurfaceProvider())
            }
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch (exc: Exception) {
                Log.e("TAG", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }


    /*fun loadProductsFromRoom(skip: Int, limit: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProductsFromRoom(skip, limit)
        }
    }*/
}