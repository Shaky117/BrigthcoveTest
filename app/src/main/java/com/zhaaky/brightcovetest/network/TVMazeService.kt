package com.zhaaky.brightcovetest.network

import com.zhaaky.brightcovetest.models.TvShow
import retrofit2.http.GET
import retrofit2.http.Query

interface TVMazeService {

    @GET("schedule?country=US")
    suspend fun getSchedule(@Query("date") timeStamp : String) : List<TvShow>

}