package com.example.shimmer.model.service

import com.example.shimmer.model.dto.Data
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface{

    @Headers("Content-Type: application/json")
    @GET("users")
    fun getName() : Call<List<Data>>
}

