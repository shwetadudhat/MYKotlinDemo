package com.test.mykotlindemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.test.mykotlindemo.Model.Product


@Dao
interface ProductDao {

    @Insert
    suspend fun addProduct(quotes: MutableList<Product>)

    @Query("SELECT * FROM product")
    suspend fun getProductss() : MutableList<Product>

   /* @Query("SELECT * FROM products LIMIT :limit OFFSET :skip")
    suspend fun getProducts(skip: Int, limit: Int): MutableList<Product>*/
}