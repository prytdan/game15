package com.example.game15.api

import com.example.game15.model.internet.Data
import retrofit2.Response
import retrofit2.http.GET

interface DataApi {

    @GET("pagesofwealth/status.json")
    suspend fun getData(): Response<Data>
}