package com.test.mykotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.test.mykotlindemo.Model.Product
import com.test.mykotlindemo.db.ProductDatabase
import com.test.mykotlindemo.repository.ProductRepository
import com.test.mykotlindemo.viewmodels.MainViewModel
import com.test.mykotlindemo.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewActivity : AppCompatActivity() {

    private lateinit var productRepository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize repository

        productRepository = (application as ProductApplication).productRepository

//        mainViewModel = ViewModelProvider(this, MainViewModelFactory(productRepository)).get(MainViewModel::class.java)



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




            btnSubmit.setOnClickListener { v->
                val newDescription = etDesc.text.toString()
                val newPrice = etProPrice.text.toString().toIntOrNull()

                if (newDescription.isNotEmpty() && newPrice != null) {
                    val updatedProduct = product.copy(description = newDescription, price = newPrice)
                    updateProduct(updatedProduct)
                } else {
                    Toast.makeText(this, "Please enter valid description and price", Toast.LENGTH_SHORT).show()
                }
             }
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
}