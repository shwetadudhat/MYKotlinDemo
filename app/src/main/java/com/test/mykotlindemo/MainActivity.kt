package com.test.mykotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.test.mykotlindemo.adapter.ProductListAdapter
import com.test.mykotlindemo.viewmodels.MainViewModel
import com.test.mykotlindemo.viewmodels.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var adapter: ProductListAdapter
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as ProductApplication).productRepository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        /******************* Retrive data from Room and display in recyclerview *********************/
        mainViewModel.products.observe(this, Observer {
            Toast.makeText(this@MainActivity, it.products.size.toString(), Toast.LENGTH_SHORT).show()
            Log.d("productsList",it.products.toString())
            Log.d("productsListSize",it.products.size.toString())

            adapter = ProductListAdapter(this@MainActivity,it.products)
            rvProductList.layoutManager = GridLayoutManager(this,2)
            rvProductList.adapter = adapter


        })

    }
}