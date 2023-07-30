package com.example.game15.repository

import com.example.game15.api.RetrofitInstance
import com.example.game15.model.internet.Data
import retrofit2.Response

class Repository {
    suspend fun getData(): Response<Data> {
        return RetrofitInstance.api.getData()
    }
}