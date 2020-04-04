package com.lovisgod.safehaven

import com.lovisgod.safehaven.Repoitory.AuthRepo
import com.lovisgod.safehaven.retrofit.Network
import org.koin.dsl.module

val appModule = module {
    single {
        AuthRepo(get())
    }
    single { Network }
}