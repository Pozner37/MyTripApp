package com.mytrip.classes

import com.google.android.gms.maps.model.LatLng

data class Post(
    val id: String,
    val countryName: String,
    val description: String,
    val picture: String,
    val position: LatLng, ){
}