package com.test.mykotlindemo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.mykotlindemo.repository.ProductRepository

class MainViewModelFactory(private val repository: ProductRepository): ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
        return MainViewModel(repository) as T
    }
}