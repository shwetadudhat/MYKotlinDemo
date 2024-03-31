package com.test.mykotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.test.mykotlindemo.viewmodels.MainViewModel
import com.test.mykotlindemo.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val repository = (application as ProductApplication).productRepository

        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.products.observe(this, Observer {
            Toast.makeText(this@MainActivity, it.products.size.toString(), Toast.LENGTH_SHORT).show()
            Log.d("productsList",it.products.toString())
        })

    }
}