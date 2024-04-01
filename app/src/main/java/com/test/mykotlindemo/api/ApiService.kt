package com.test.mykotlindemo.api

import com.test.mykotlindemo.Model.DummyProductsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/products")
//    suspend fun getQuotes() : Response<DummyProductsList>
    suspend fun getQuotes1(@Query("skip") page: Int) : Response<DummyProductsList>
}