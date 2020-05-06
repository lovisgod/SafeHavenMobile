package com.lovisgod.safehaven.Repoitory

import com.lovisgod.safehaven.model.SignUpResponse
import com.lovisgod.safehaven.model.loginResponse
import com.lovisgod.safehaven.retrofit.Network
import com.lovisgod.safehaven.retrofit.ResultWrapper
import com.lovisgod.safehaven.retrofit.safeApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AuthRepo(private val network: Network) {
    val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun register(email: String,
                         phone: String,
                         name:String,
                         pass: String,
                         con_pass:String
       ):ResultWrapper<SignUpResponse> {
       return safeApiResult(dispatcher) {
            network.invoke("").register(
              email = email,
                phone = phone,
                fullname = name,
                password = pass,
                confirmPassword = con_pass
            )
        }
    }

    suspend fun login(email: String,
                      pass:String
    ):ResultWrapper<loginResponse> {
        return safeApiResult(dispatcher) {
            network.invoke("").login(
                email = email,
                password = pass
            )
        }
    }
}