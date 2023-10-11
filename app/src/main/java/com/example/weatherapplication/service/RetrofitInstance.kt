package com.example.weatherapplication.service

import com.bumptech.glide.util.Util
import com.example.weatherapplication.Utils
import com.google.android.gms.common.api.Api.Client
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            Retrofit.Builder().baseUrl(Utils.BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build()
        }

        val api by lazy {

            retrofit.create(Service::class.java)
        }
    }
}