package com.example.calculationsapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface APIService {
    @GET
    suspend fun getResponse(@Url url: String?): Response<ResponseAnswer>?

    @GET
    suspend fun getZeroes(@Url url: String?): Response<ResponseZeroes>?
}

