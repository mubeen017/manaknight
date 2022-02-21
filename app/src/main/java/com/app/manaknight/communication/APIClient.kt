package com.app.rofoof.communication

import com.app.manaknight.communication.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    companion object {

        private val retrofit by lazy {
            val client: OkHttpClient = OkHttpClient.Builder().apply {
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
            }.build()
            Retrofit.Builder().apply {
                baseUrl("https://vyod.manaknightdigital.com/")
                addConverterFactory(GsonConverterFactory.create())
                client(client)
            }.build()
        }

        val API: ApiInterface by lazy {
            retrofit.create(ApiInterface::class.java)
        }

    }
}