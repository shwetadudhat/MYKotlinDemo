package com.test.mykotlindemo.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.mykotlindemo.Model.DummyProductsList
import com.test.mykotlindemo.Model.Product
import com.test.mykotlindemo.Utils.NetworkUtils
import com.test.mykotlindemo.api.ApiService
import com.test.mykotlindemo.db.ProductDatabase

class ProductRepository(
    private val productService: ApiService,
    private val productDatabase: ProductDatabase,
    private val applicationContext: Context
) {

    private val productsLiveData = MutableLiveData<DummyProductsList>()

    val products: LiveData<DummyProductsList>
        get() = productsLiveData


    suspend fun getProducts(page: Int) {

        if (NetworkUtils.isInternetAvailable(applicationContext)) {
            val result = productService.getQuotes()
            if (result?.body() != null) {
                productDatabase.productDao().addProduct(result.body()!!.products)
                productsLiveData.postValue(result.body())
            }
        } else {
            val products = productDatabase.productDao().getProductss()
            val productsList = DummyProductsList(1, products, 1, 1)
            productsLiveData.postValue(productsList)
        }

    }

    suspend fun getProductsFromRoom() {
        val products = productDatabase.productDao().getProductss()
        val productsList = DummyProductsList(1, products, 1, 1)
        productsLiveData.postValue(productsList)

    }
}


