package com.test.mykotlindemo.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mykotlindemo.Model.DummyProductsList
import com.test.mykotlindemo.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository): ViewModel()  {

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getProducts(1)
            repository.getProductsFromRoom()//data from room database
        }
    }

    val products : LiveData<DummyProductsList>
        get() = repository.products


    fun loadNextPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProducts(page)
        }
    }
}