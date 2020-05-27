package com.lovisgod.safehaven.Util

import android.content.Context
import android.location.Location
import com.lovisgod.safehaven.model.LatLngDetails

class Geocode {
    fun getCoordinates(): String {

        val location = Location("")
        val lat = location.latitude
        val long = location.longitude
        val toFromLatLng = "${lat},${long}"
        return toFromLatLng

    }
}