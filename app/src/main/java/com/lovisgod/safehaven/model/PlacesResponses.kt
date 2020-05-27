package com.lovisgod.safehaven.model

data class PlacesResponses (
    val status: String,
    val results: ArrayList<Business>
)

data class DetailsResponse (
    val status: String,
    val result: DetailsResult
)

data class DetailsResult(
    val international_phone_number: String,
    val name: String,
    val rating: String,
    val website: String
)

data class Business (
    val business_status: String,
    val icon: String,
    val name: String,
    val place_id: String,
    val geometry: Geometry,
    val vicinity: String,
    val opening_hours: OpeningHours
)
data class OpeningHours (
    val open_now: Boolean
)

data class Geometry(
  val geometry: BusinessLocation
)

data class BusinessLocation (
    val lat : Double,
    val lng : Double
)