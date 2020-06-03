package com.lovisgod.safehaven.model

class Users (
    val name: String,
    val email: String,
    val phone_number: String
) {
    constructor():this( name = "", email = "", phone_number = "")

    companion object {
        fun tomap(name: String, email: String, phoneNumber: String): HashMap<String, String> {
            val user = hashMapOf(
                "name" to name,
                "email" to email,
                "phone_number" to phoneNumber
            )

            return user
        }
    }


}
