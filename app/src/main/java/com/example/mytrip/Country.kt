package com.example.mytrip

import com.google.gson.annotations.SerializedName

data class CountryName (
    val common: String
)

data class CountryFlag (
    val png: String
)

data class Country(
    @SerializedName("name") val name: CountryName,
    @SerializedName("flags") val flags: CountryFlag
)
