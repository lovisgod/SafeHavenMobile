package com.lovisgod.safehaven.model

data class SignUpResponse (
    val status: Boolean,
    val message: String
)

data class loginResponse(
    val user: User,
    val message:String
)

data class User (
    val token: String
)