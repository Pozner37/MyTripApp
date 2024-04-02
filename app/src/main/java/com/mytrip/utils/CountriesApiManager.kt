package com.mytrip.utils

import com.mytrip.CountriesApi
import com.mytrip.classes.Country
import com.mytrip.classes.Flag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CountriesApiManager {

    private val restCountriesApi = "https://restcountries.com/v3.1/"

    private val api = Retrofit.Builder().baseUrl(restCountriesApi)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create()).build().create(CountriesApi::class.java)

    suspend fun getAllCountries(): List<Country> {
        return withContext(Dispatchers.IO) {
            api.getAllCountries()
        }
    }

    fun getCountryFlag(countryCode : String): Call<Flag> {
        return api.getCountryFlag(countryCode);
    }

    suspend fun getCountryByCode(countryCode : String): Country {
        return withContext(Dispatchers.IO) {
            api.getCountryByCode(countryCode);
        }
    }
}