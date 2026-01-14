package com.ucahealth.app.data.repository

import com.ucahealth.app.data.model.Sport
import com.ucahealth.app.data.remote.RetrofitClient

class SportsRepository {

    private val api = RetrofitClient.sportsApi

    suspend fun getSports(): List<Sport> {
        return api.getSports()
    }


}