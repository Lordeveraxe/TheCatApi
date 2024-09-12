package com.example.thecatapi.network

import com.example.thecatapi.model.Breed
import com.example.thecatapi.model.Cat
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CatApiService {

    // Fetch breeds with pagination
    @Headers("x-api-key: live_zqRPLP11ZYDs1QV5TTGg8xXnHXWkblCaGJZMFsl9S01NAZRaErCiQWaRb4rivQcj")
    @GET("v1/breeds")
    suspend fun getBreeds(
        @Query("page") page: Int,          // Page number
        @Query("limit") limit: Int         // Number of items per page
    ): List<Breed>

    // Fetch image for each breed
    @Headers("x-api-key: live_zqRPLP11ZYDs1QV5TTGg8xXnHXWkblCaGJZMFsl9S01NAZRaErCiQWaRb4rivQcj")
    @GET("v1/images/search")
    suspend fun getImageByBreed(@Query("breed_ids") breedId: String): List<Cat>

    companion object {
        private const val BASE_URL = "https://api.thecatapi.com/"

        fun create(): CatApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CatApiService::class.java)
        }
    }
}