package com.hung.exrotrofit.network

import com.hung.exrotrofit.model.Song
import retrofit2.Call
import retrofit2.http.GET

interface SongService {

    @GET("music.json")
    fun getAllMusic(): Call<Song>
}