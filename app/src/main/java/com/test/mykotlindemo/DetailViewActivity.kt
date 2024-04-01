package com.test.mykotlindemo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.test.mykotlindemo.Model.Product
import com.test.mykotlindemo.repository.ProductRepository
import com.test.mykotlindemo.viewmodels.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_CAPTURE_IMAGE = 1001 // Example code, you can use any integer value
    }

    private lateinit var viewModel: DetailViewModel

    private lateinit var productRepository: ProductRepository
    private  var imageInByte: ByteArray = byteArrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize repository
        productRepository = (application as ProductApplication).productRepository

        // Initialize viewModel
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)


        // Retrieve product object from Intent extras
        val product = intent.getSerializableExtra("product") as? Product

        // Now you can use the product object in this activity
        if (product != null) {
            // Do something with the product object
            // For example, display its details in the UI
            // Now you can use the product object in this activity
            updateUI(product)
            Log.d("TAG", "onCreate: "+product.title)
//            ivThumbnail

            ivThumbnail.setOnClickListener {
                checkCameraPermission()
            }

            observeViewModel()

            btnSubmit.setOnClickListener {

                // Get the image byte array from ViewModel
                viewModel.imageByteArray.value?.let { imageByteArray ->
                    // Call updateProduct method with the image byte array
                    updateProductWithImage(imageByteArray,product)
                }

             }
        }
    }

    private fun updateProductWithImage(imageByteArray: ByteArray, product: Product) {
        // Assuming you have the product object
        // Pass the imageByteArray to your updateProduct method
        // You can perform the update operation here
        // For example, update the product in the repository with the imageByteArray
        val newDescription = etDesc.text.toString()
        val newPrice = etProPrice.text.toString().toIntOrNull()

        if (newDescription.isNotEmpty() && newPrice != null) {

            val thumbnailString: String? = if (imageByteArray.isNotEmpty()) {
                // Convert the byte array to a Base64 encoded string
                Base64.encodeToString(imageByteArray, Base64.DEFAULT)
            } else {
                // If thumbnail is empty or null, use existing thumbnail from the product
                product.thumbnail
            }
            // Create an updated product object with the new description, price, and image
            val updatedProduct = product.copy(description = newDescription, price = newPrice, thumbnail = thumbnailString)

//                    val updatedProduct = product.copy(description = newDescription, price = newPrice)
            updateProduct(updatedProduct)
        } else {
            Toast.makeText(this, "Please enter valid description and price", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeViewModel() {
        viewModel.thumbnailBitmap.observe(this, Observer { bitmap ->
            ivThumbnail.setImageBitmap(bitmap)
            // Process the bitmap or save it to the database as needed
        })
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CODE_CAPTURE_IMAGE)
        } else {
            viewModel.capturePhoto(this)
        }
    }

    private fun updateProduct(updatedProduct: Product) {
        GlobalScope.launch(Dispatchers.IO) {
            productRepository.updateProduct(updatedProduct)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@DetailViewActivity, "Product updated successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(product: Product) {
        Glide.with(this@DetailViewActivity).load(product.thumbnail).into(ivThumbnail)
        tvTitle.text=product.title
        supportActionBar?.title=product.title
        tvBrand.text=product.brand
        tvDesc.text=product.description
        tvProPrice.text="Rs."+product.price.toString()
        Log.d("arraylistImages",product.images.toString())

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        onBackPressedDispatcher.onBackPressed()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.capturePhoto(this)
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            viewModel.processCapturedImage(data)
           }
    }
}


