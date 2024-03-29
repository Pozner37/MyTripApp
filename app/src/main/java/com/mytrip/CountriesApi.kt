package com.mytrip

import com.mytrip.classes.Country
import com.mytrip.classes.Flag
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CountriesApi {

    @GET("all?fields=flags,name")
    suspend fun  getAllCountries(): List<Country>

    @GET("name/{country}?fields=flags")
    fun getCountryFlag(@Path("country") countryName: String): Call<List<Flag>>
}