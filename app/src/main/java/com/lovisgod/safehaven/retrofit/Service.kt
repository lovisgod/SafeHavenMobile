package com.lovisgod.safehaven.retrofit

import com.lovisgod.safehaven.model.GeneralResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Service {

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("fullname") fullname: String,
        @Field("password") password: String,
        @Field("email")email: String,
        @Field("phone_number")phone_number: String,
        @Field("username")username: String,
        @Field("user_type")user_type: String,
        @Field("partner_plan_id")partner_plan_id: Int
    ): Response<GeneralResponse>
}