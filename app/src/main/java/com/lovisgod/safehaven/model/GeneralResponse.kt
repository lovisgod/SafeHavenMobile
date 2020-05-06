package com.lovisgod.safehaven.model

data class GeneralResponse(
    var status: String,
    var data: String
)

data class ErrorResponse(
    var status: Boolean,
    var message: String
)