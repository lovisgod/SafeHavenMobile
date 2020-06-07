package com.lovisgod.safehaven.viewmodel

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.lovisgod.safehaven.Repoitory.AuthRepo
import com.lovisgod.safehaven.Repoitory.FireBaseRepo
import com.lovisgod.safehaven.Repoitory.GoogleRepo
import com.lovisgod.safehaven.Util.Geocode
import com.lovisgod.safehaven.model.*
import com.lovisgod.safehaven.retrofit.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject

class AppViewModel(application: Application): ViewModel() {

    val googleRepo by application.inject<GoogleRepo>()
    val fireBaseRepo by application.inject<FireBaseRepo>()
    private val placesAround : MutableLiveData<ArrayList<Business>> = MutableLiveData()
    val _placesAround: LiveData<ArrayList<Business>> get() = placesAround
    private val name : MutableLiveData<String> = MutableLiveData()
    val _name: LiveData<String>get() = name
    var email: MutableLiveData<String> = MutableLiveData()
    val _email: LiveData<String>get() = email
    var phoneNumber :MutableLiveData<String> = MutableLiveData()
    val _phoneNumber: LiveData<String>get() = phoneNumber

    var friendList :MutableLiveData<ArrayList<Friend>> = MutableLiveData()
    val _friendList: LiveData<ArrayList<Friend>>get() = friendList


    init {
        getUserProfileFirebase()
        getSafeFriendList()
    }



    fun searchPlaces(type: String, location: String) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                val location = Geocode().getCoordinates()
                val response =  googleRepo.searchPlaces(location, type)
                when(response) {
                    is ResultWrapper.Success -> {
                        placesAround.postValue(response.value.results)
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

    fun getPlaceDetails(place_id: String) =
        liveData(Dispatchers.IO) {
            val fields = "name,rating,international_phone_number,website"
            when(val response = googleRepo.getPlaceDetails(place_id = place_id, fields = fields)) {
                is ResultWrapper.Success -> {
                    println(response.value.result)
                    emit(response.value.result)
                }
                is ResultWrapper.GenericError -> {
                    EventBus.getDefault().post(AppEvent(event = "error", message = response.error!!.message))
                }
                is ResultWrapper.NetworkError -> {
                    EventBus.getDefault().post(AppEvent(event = "error", message = response.message))
                }
            }
        }


    fun getUserProfileFirebase() {
        val user = fireBaseRepo.getCurrentUser(Firebase.auth.currentUser!!.email!!)
            .addOnSuccessListener {
                val userdata = it.toObject<Users>()
                name.postValue(userdata!!.name)
                email.postValue(userdata.email)
                phoneNumber.postValue(userdata.phone_number)
            }

    }

    fun saveSafeFriend(name:String, phoneNumber: String) {
        viewModelScope.launch() {
            try {
                withContext(Dispatchers.IO) {
                    val email = Firebase.auth.currentUser!!.email!!
                    fireBaseRepo.saveFriendDetails(name = name, phoneNumber = phoneNumber, userEmail = email)
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }

    }


    fun getSafeFriendList() {
        viewModelScope.launch() {
            try {
                withContext(Dispatchers.IO) {
                    val email = Firebase.auth.currentUser!!.email!!
                    var response = fireBaseRepo.getSafeFriendList(email)
                    // this fetch  friends list, and update it
                    response.get().addOnSuccessListener {documents ->
                        var friends = ArrayList<Friend>()
                        if (documents != null) {
                            for ( document in documents ) {
                                val data = document.data
                                val name = data.get("name")
                                val phoneNumber = data.get("phone_number")
                                val myEmail = data.get("user_email")

                                friends.add(Friend(name = name.toString(), user_email = myEmail.toString(), phone_number = phoneNumber.toString()))
                            }
                            println(friends)
                            friendList.postValue(friends)
                        }
                    }

                    // this listens to change in value and update the list
                    response.addSnapshotListener { querySnapshot, exception ->
                        var friends = ArrayList<Friend>()
                        if (querySnapshot != null) {
                                for ( document in querySnapshot ) {
                                    val data = document.data
                                    val name = data.get("name")
                                    val phoneNumber = data.get("phone_number")
                                    val myEmail = data.get("user_email")

                                    friends.add(Friend(
                                        name = name.toString(),
                                        user_email = myEmail.toString(),
                                        phone_number = phoneNumber.toString()))
                                }
                                println(friends)
                                friendList.postValue(friends)
                            }
                        }

                    // this listen to changes
                    response.get().addOnFailureListener {
                        println(it.localizedMessage)
                        EventBus.getDefault().post(AppEvent(event = "error", message = "An error occurred getting friend list"))
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }


    /**
     * Factory for constructing AppViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}