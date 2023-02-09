package com.mobile.tuesplace.services

import com.mobile.tuesplace.BASE_URL
import com.mobile.tuesplace.TuesplaceApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {

//    private const val baseUrl = BASE_URL
//    private var logging = HttpLoggingInterceptor()
//
//    fun getInstance(): Retrofit {
//        logging.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder()
//            .authenticator(TuesAuthenticator(authService = get()))
//            .addInterceptor(TuesInterceptor(TuesplaceApplication.instance.applicationContext))
//            .addInterceptor(logging)
//            .connectTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(60, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .build()
//
//        return Retrofit.Builder().baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//    }
}