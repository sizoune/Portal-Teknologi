package com.example.portalteknologi.data

import com.example.portalteknologi.data.model.ResponseNewsApi
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun getIndonesianTechNews(
        @Query("country") countryId: String,
        @Query("category") category: String
    ): Deferred<Response<ResponseNewsApi>>
}