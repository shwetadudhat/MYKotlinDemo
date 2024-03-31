package com.test.mykotlindemo.Model

data class DummyProductsList(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)