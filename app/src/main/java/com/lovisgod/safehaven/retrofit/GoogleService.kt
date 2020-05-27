package com.lovisgod.safehaven.retrofit

import com.lovisgod.safehaven.model.DetailsResponse
import com.lovisgod.safehaven.model.PlacesResponses
import com.lovisgod.safehaven.model.SignUpResponse
import com.lovisgod.safehaven.model.loginResponse
import retrofit2.Response
import retrofit2.http.*

interface GoogleService {

 @GET("nearbysearch/json")
 suspend fun searchPlaces(
     @Query("location")location: String,
     @Query("radius")radius: String,
     @Query("key")key: String,
     @Query("type")type: String
 ): Response<PlacesResponses>

 @GET("details/json")
 suspend fun getDetails(
     @Query("place_id")place_id: String,
     @Query("fields")fields: String,
     @Query("key")key: String
 ): Response<DetailsResponse>
}