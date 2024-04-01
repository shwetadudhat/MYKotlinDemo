package com.test.mykotlindemo

import android.content.Intent
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

class MainActivity : AppCompatActivity(), ProductListAdapter.LoadMoreListener {


    lateinit var adapter: ProductListAdapter
    lateinit var mainViewModel: MainViewModel
    private var currentPage = 1
    private var skip = 0
    private val limit = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = (application as ProductApplication).productRepository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)


        adapter = ProductListAdapter(clickListener = { product ->
            // Handle item click here
            Toast.makeText(this, "Clicked: ${product.title}", Toast.LENGTH_SHORT).show()
            val intent=Intent(this@MainActivity,DetailViewActivity::class.java)
            intent.putExtra("product",product)
            startActivity(intent)
        },this@MainActivity,mutableListOf(),this)


        rvProductList.layoutManager = GridLayoutManager(this,2)
        rvProductList.adapter = adapter

        /******************* Retrive data from Room and display in recyclerview *********************/
        mainViewModel.products.observe(this, Observer {
            Toast.makeText(this@MainActivity, it.products.size.toString(), Toast.LENGTH_SHORT).show()
            Log.d("productsList",it.products.toString())
            Log.d("productsListSize",it.products.size.toString())


            adapter.addData(it.products) // Adding new data to the adapter


        })

        // Load initial data
//        loadNextPage1()

    }

    /**********room***********/
//    override fun onLoadMore() {
//        skip += limit
//        loadNextPage1()
//    }
//    private fun loadNextPage1() {
//        mainViewModel.loadProductsFromRoom(skip, limit)
//    }


    /**********APi***********/
    override fun onLoadMore() {
        currentPage++
        mainViewModel.loadNextPage(currentPage)
    }
}