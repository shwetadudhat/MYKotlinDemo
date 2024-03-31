package com.test.mykotlindemo

import android.app.Application
import com.test.mykotlindemo.api.ApiService
import com.test.mykotlindemo.api.RetrofitHelper
import com.test.mykotlindemo.db.ProductDatabase
import com.test.mykotlindemo.repository.ProductRepository

class ProductApplication : Application() {

    lateinit var productRepository: ProductRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
    }

    private fun initialize() {
        val apiService =RetrofitHelper.getInstance().create(ApiService::class.java)
        val database=ProductDatabase.getDatabase(applicationContext)
        productRepository= ProductRepository(apiService,database,applicationContext)
    }
}