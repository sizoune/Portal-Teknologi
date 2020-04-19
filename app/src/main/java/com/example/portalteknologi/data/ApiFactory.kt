package com.example.portalteknologi.data

import com.example.portalteknologi.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Apifactory {
    private var newsApiKey = BuildConfig.NEWS_API_KEY

    //Creating Auth Interceptor to add apiKey query in front of all the requests.
    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("apiKey", newsApiKey)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    //OkhttpClient for building http request url
    private val newsAPiClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    fun retrofit(): Retrofit = Retrofit.Builder()
        .client(newsAPiClient)
        .baseUrl("http://newsapi.org/v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val newsApi: NewsApi = retrofit().create(NewsApi::class.java)
}