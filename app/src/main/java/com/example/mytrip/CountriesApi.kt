package com.example.mytrip

import retrofit2.Call
import retrofit2.http.GET

interface CountriesApi {

    @GET("all")
    suspend fun  getAllCountries(): List<Country>
}