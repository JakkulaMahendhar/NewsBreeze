package com.modules.newsbreeze.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.modules.newsbreeze.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitInstance {

    companion object{
        public  fun<T> getNewsClient(serviceClass : Class<T>): T {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL).client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(serviceClass)
        }

        private fun getHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val loggingHeaderInterceptor = HttpLoggingInterceptor()
            loggingHeaderInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingHeaderInterceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES).build()
            return client.newBuilder().build()
        }

    }
}