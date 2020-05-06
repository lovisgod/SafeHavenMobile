package com.lovisgod.safehaven.retrofit

import com.lovisgod.safehaven.model.GeneralResponse
import com.lovisgod.safehaven.model.SignUpResponse
import com.lovisgod.safehaven.model.loginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Service {

    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun register(
        @Field("fullname") fullname: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String,
        @Field("email")email: String,
        @Field("phone")phone: String
    ): Response<SignUpResponse>

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
      @Field("email") email:String,
      @Field("password") password: String
    ): Response<loginResponse>

    @FormUrlEncoded
    @POST("auth/verify")
    suspend fun verify(
        @Field("email") email:String,
        @Field("verificationCode") verificationCode: String
    ): Response<loginResponse>
}