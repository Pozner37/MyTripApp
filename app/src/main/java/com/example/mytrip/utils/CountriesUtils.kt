package com.example.mytrip.utils

import android.util.Log
import com.example.mytrip.CountriesApi
import com.example.mytrip.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val restCountriesApi = "https://restcountries.com/v3.1/"

fun getAllCountries() {
    val api = Retrofit.Builder().baseUrl(restCountriesApi)
        .addConverterFactory(GsonConverterFactory.create()).build().create(CountriesApi::class.java)
    api.getAllCountries().enqueue(object : Callback<List<Country>> {
        override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
            if (response.isSuccessful){
                response.body()?.let {
                    for (country in it){
                        Log.i("country", "countries: ${country.name.common}")
                    }
                }
            }
        }

        override fun onFailure(call: Call<List<Country>>, t: Throwable) {
            Log.i("country", "failed : ${t.message}")
        }

    })
}


