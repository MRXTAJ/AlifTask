package com.example.aliftask.network

import com.example.aliftask.model.Guides
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(baseUrl: String): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}

interface RetrofitServices {
    @GET("/service/v2/upcomingGuides/")
    fun getGuidesListAsync(): Deferred<Response<Guides>>
}

object GuidesApi {
    const val BASE_URL = "https://guidebook.com"
    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}