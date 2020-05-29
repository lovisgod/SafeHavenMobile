package com.lovisgod.safehaven.model

data class Contact (
    var name: String,
    var phone_number: String,
    var address: String = "",
    var status: String = ""
)