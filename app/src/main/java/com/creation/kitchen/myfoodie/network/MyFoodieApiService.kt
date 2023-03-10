package com.creation.kitchen.myfoodie.network

import com.creation.kitchen.myfoodie.ui.model.CategoryResponse
import com.creation.kitchen.myfoodie.ui.model.MealDetailsResponse
import com.creation.kitchen.myfoodie.ui.model.MealResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://www.themealdb.com//api/json/v1/1/"

val json = Json { ignoreUnknownKeys = true }
private val retrofit = Retrofit.Builder()
    .addConverterFactory(
        json.asConverterFactory("application/json".toMediaType())
    )
    .baseUrl(BASE_URL)
    .build()

interface MyFoodieApiService {
    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsList(@Query("c") category: String): MealResponse

    @GET("lookup.php")
    suspend fun getMealDetails(@Query("i") mealId: String): MealDetailsResponse
}

object MyFoodieApi {
    val retrofitService: MyFoodieApiService by lazy {
        retrofit.create(MyFoodieApiService::class.java)
    }
}