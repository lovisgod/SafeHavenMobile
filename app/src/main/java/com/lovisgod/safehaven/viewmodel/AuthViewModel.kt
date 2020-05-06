package com.lovisgod.safehaven.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lovisgod.safehaven.Repoitory.AuthRepo
import com.lovisgod.safehaven.model.AppEvent
import com.lovisgod.safehaven.retrofit.ResultWrapper
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject

class AuthViewModel(application: Application): ViewModel() {

    val authRepo by application.inject<AuthRepo>()
    var saved_name = ""
    var saved_phone =""
    var saved_email = ""
    var password = ""
    var confirm_password = ""

    fun getSavedName(): String{
        return this.saved_name
    }

    fun getSavedPhone(): String{
        return this.saved_phone
    }

    fun getSavedEmail(): String{
        return this.saved_email
    }

    fun saveName(value:String){
        this.saved_name = value
        println(this.saved_name)
    }

    fun savePhone(value: String){
        this.saved_phone = value
    }

    fun saveEmail(value:String){
        Prefs.putString("user_email", value)
        this.saved_email = value
    }


    fun register(password:String, confirmPassword:String){
        println("${this.saved_email}, ${this.saved_name}, ${this.saved_phone}, ${password}, ${confirmPassword}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response =authRepo.register(
                    email = saved_email,
                    phone = saved_phone,
                    name = saved_name,
                    pass = password,
                    con_pass = confirmPassword
                )
                when (response) {
                    is ResultWrapper.Success -> {
                        println(response.value.message)
                        EventBus.getDefault().post(AppEvent(event = "success", message = response.value.message))
                    }
                    is ResultWrapper.GenericError -> {
                        EventBus.getDefault().post(AppEvent(event = "error", message = response.error!!.message))
                    }
                    is ResultWrapper.NetworkError -> {
                        EventBus.getDefault().post(AppEvent(event = "error", message = response.message))
                    }
                }
            }
        }
    }

    fun login(password:String, email:String){
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val response =authRepo.login(
                    email = email,
                    pass = password
                )
                when (response) {
                    is ResultWrapper.Success -> {
                        println(response.value.message)
                        Prefs.putString("token",response.value.user.token)
                        EventBus.getDefault().post(AppEvent(event = "success", message = response.value.message))
                    }
                    is ResultWrapper.GenericError -> {
                        EventBus.getDefault().post(AppEvent(event = "error", message = response.error!!.message))
                    }
                    is ResultWrapper.NetworkError -> {
                        EventBus.getDefault().post(AppEvent(event = "error", message = response.message))
                    }
                }
            }
        }
    }



    /**
     * Factory for constructing AuthViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}