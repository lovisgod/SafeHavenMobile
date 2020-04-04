package com.lovisgod.safehaven.Util

import com.auth0.android.jwt.JWT

class JwtHelper {
    companion object Helper {
        fun decode(token: String): Boolean {
            val jwt = JWT(token)
            val isExpired = jwt.isExpired(10)
            return isExpired
        }
    }
}