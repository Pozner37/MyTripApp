package com.example.mytrip.utils

import com.example.mytrip.CountriesApi
import com.example.mytrip.Country
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CountriesApiManager {

    private val restCountriesApi = "https://restcountries.com/v3.1/"

    private val api = Retrofit.Builder().baseUrl(restCountriesApi)
        .addConverterFactory(GsonConverterFactory.create()).build().create(CountriesApi::class.java)

    suspend fun getAllCountries(): List<Country> {
        return withContext(Dispatchers.IO) {
            api.getAllCountries()
        }
    }
}