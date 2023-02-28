package com.creation.kitchen.myfoodie.network

import com.creation.kitchen.myfoodie.ui.model.CategoryResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://www.themealdb.com//api/json/v1/1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL)
    .build()

interface MyFoodieApiService {
    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php?c={category}")
    suspend fun getMealsList(@Path("category") category: String)
}

object MyFoodieApi {
    val retrofitService: MyFoodieApiService by lazy {
        retrofit.create(MyFoodieApiService::class.java)
    }
}