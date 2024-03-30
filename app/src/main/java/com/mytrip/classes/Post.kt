package com.mytrip.classes

import com.google.android.gms.maps.model.LatLng
import java.io.Serializable

data class Post(
    val id: String,
    val userId : String,
    val countryName: String,
    val description: String,
    val position: LatLng, ): Serializable {
}