package com.lovisgod.safehaven.retrofit

import com.google.gson.Gson
import com.lovisgod.safehaven.model.GeneralResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response

    suspend fun <T : Any> safeApiResult(
        dispatcher: CoroutineDispatcher,
        call: suspend () -> Response<T>
    ): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                val response = call.invoke()
                if (response.isSuccessful) {
                    ResultWrapper.Success(response.body()!!)
                } else {
                    ResultWrapper.GenericError(response.code(), extractErrorBody(response.errorBody()))
                }
            } catch (e: Throwable) {
                println("input message ${e.message}")
                e.printStackTrace()
                ResultWrapper.NetworkError()
            }
        }
    }


    private fun extractErrorBody(response: ResponseBody?): GeneralResponse? {
        return try {
            val gson = Gson()
            return gson.fromJson(response?.string(), GeneralResponse::class.java)
        } catch (exception: Exception) {
            println("exception ${exception.message}")
            null
        }
    }