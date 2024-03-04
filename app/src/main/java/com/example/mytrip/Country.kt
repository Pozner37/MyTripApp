package com.example.mytrip

data class CountryName (
    val common: String
)

data class CountryFlag (
    val png: String
)

data class Country(
    val name: CountryName,
    val flags: CountryFlag
)
