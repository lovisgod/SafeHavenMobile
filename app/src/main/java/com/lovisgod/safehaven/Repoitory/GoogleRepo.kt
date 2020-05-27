package com.lovisgod.safehaven.Repoitory

import com.lovisgod.safehaven.model.DetailsResponse
import com.lovisgod.safehaven.model.PlacesResponses
import com.lovisgod.safehaven.retrofit.GoogleNetwork
import com.lovisgod.safehaven.retrofit.ResultWrapper
import com.lovisgod.safehaven.retrofit.safeApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GoogleRepo(val googleNetWork: GoogleNetwork) {
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
    val key = "AIzaSyAiTJdmngbJGYuuFkxUcfEEZAMkxt9YAPo"

    suspend fun  searchPlaces(location: String, type: String): ResultWrapper<PlacesResponses> {
      return safeApiResult(dispatcher){
          googleNetWork.invoke("").searchPlaces(
              location = location,
              radius = "3000",
              key = key,
              type = type
          )
      }
    }

    suspend fun getPlaceDetails(place_id: String, fields: String): ResultWrapper<DetailsResponse> {
        return  safeApiResult(dispatcher) {
            googleNetWork.invoke("").getDetails(
                place_id = place_id,
                fields = fields,
                key = key
            )
        }
    }
}