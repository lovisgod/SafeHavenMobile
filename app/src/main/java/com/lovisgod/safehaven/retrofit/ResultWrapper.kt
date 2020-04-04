package com.lovisgod.safehaven.retrofit

import com.lovisgod.safehaven.model.GeneralResponse

sealed class ResultWrapper<out T> {
    data class Success<T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: GeneralResponse? = null) :
        ResultWrapper<Nothing>()

    data class NetworkError(val message: String = "Error during operation please check your network") : ResultWrapper<Nothing>()
}