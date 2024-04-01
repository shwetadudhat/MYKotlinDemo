package com.test.mykotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.test.mykotlindemo.Model.Product
import kotlinx.android.synthetic.main.activity_detail_view.*

class DetailViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Retrieve product object from Intent extras
        val product = intent.getSerializableExtra("product") as? Product

        // Now you can use the product object in this activity
        if (product != null) {
            // Do something with the product object
            // For example, display its details in the UI
            Log.d("TAG", "onCreate: "+product.title)
//            ivThumbnail
            Glide.with(this@DetailViewActivity).load(product.thumbnail).into(ivThumbnail)
            tvTitle.text=product.title
            supportActionBar?.title=product.title
            tvBrand.text=product.brand
            tvDesc.text=product.description
            tvProPrice.text="Rs."+product.price.toString()
            Log.d("arraylistImages",product.images.toString())
        }
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