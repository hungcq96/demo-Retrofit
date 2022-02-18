package com.hung.exrotrofit.network

import com.hung.exrotrofit.utils.Constants
import com.hung.exrotrofit.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object SongClient {
//
//
//    fun getloggingInterceptor(): HttpLoggingInterceptor{
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        return loggingInterceptor
//    }
//
//
//    private val okHttp = OkHttpClient.Builder()
//        .addInterceptor(getloggingInterceptor())
//        .build()
//
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(Constants.BASE_URL)
//        .client(okHttp)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    operator fun invoke(): SongService = retrofit.create(SongService::class.java)
//}

class SongClient {

    companion object {

        private val retrofit by lazy {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api: SongService by lazy {
            retrofit.create(SongService::class.java)
        }
    }
}