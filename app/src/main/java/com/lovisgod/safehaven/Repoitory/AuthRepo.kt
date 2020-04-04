package com.lovisgod.safehaven.Repoitory

import com.lovisgod.safehaven.retrofit.Network
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AuthRepo(private val network: Network) {
    val dispatcher: CoroutineDispatcher = Dispatchers.IO
}