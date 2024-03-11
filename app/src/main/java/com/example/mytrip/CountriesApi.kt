package com.example.mytrip

import com.example.mytrip.classes.Country
import retrofit2.http.GET

interface CountriesApi {

    @GET("all")
    suspend fun  getAllCountries(): List<Country>
}