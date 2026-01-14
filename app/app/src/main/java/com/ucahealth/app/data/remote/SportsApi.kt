package com.ucahealth.app.data.remote

import com.ucahealth.app.data.model.Sport
import retrofit2.http.GET

interface SportsApi {

    @GET("/api/sports")
    suspend fun getSports(): List<Sport>

}