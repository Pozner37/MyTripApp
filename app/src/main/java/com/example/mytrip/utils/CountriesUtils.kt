package com.example.mytrip.utils

import android.util.Log
import com.example.mytrip.CountriesApi
import com.example.mytrip.Country
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

val restCountriesApi = "https://restcountries.com/v3.1/"

@OptIn(DelicateCoroutinesApi::class)
fun getAllCountries(): List<Country>? {
    var res: List<Country>?= emptyList<Country>()
    val api = Retrofit.Builder().baseUrl(restCountriesApi)
        .addConverterFactory(GsonConverterFactory.create()).build().create(CountriesApi::class.java)
    GlobalScope.launch(Dispatchers.IO) {
        val response = api.getAllCountries().awaitResponse()
        if (response.isSuccessful) {
            res = response.body()
        }
    }
    return res
}


