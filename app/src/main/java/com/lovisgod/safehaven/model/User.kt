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

        fun friendMap(name: String, phone_number: String, user_email: String): HashMap<String, String> {
            val user = hashMapOf(
                "name" to name,
                "phone_number" to phone_number,
                "user_email" to user_email
            )
            return  user;
        }
    }


}
