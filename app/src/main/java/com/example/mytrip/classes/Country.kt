package com.example.mytrip.classes

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CountryName (
    val common: String
)

data class CountryFlag (
    val png: String
)

data class Country(
    @SerializedName("name") val name: CountryName,
    @SerializedName("flags") val flags: CountryFlag
): Serializable
