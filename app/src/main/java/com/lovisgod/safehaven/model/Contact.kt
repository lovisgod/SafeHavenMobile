package com.lovisgod.safehaven.model

data class Contact (
    var name: String,
    var phone_number: String,
    var address: String = "",
    var status: String = ""

)


data class Friend (
    var name: String,
    var phone_number: String,
    var user_email: String
)

data class FriendList (
    var friends: ArrayList<Contact>
)