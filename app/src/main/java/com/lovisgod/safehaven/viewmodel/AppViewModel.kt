package com.lovisgod.safehaven.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.lovisgod.safehaven.Repoitory.AuthRepo
import com.lovisgod.safehaven.Repoitory.GoogleRepo
import com.lovisgod.safehaven.Util.Geocode
import com.lovisgod.safehaven.model.AppEvent
import com.lovisgod.safehaven.model.Business
import com.lovisgod.safehaven.model.PlacesResponses
import com.lovisgod.safehaven.retrofit.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.android.inject

class AppViewModel(application: Application): ViewModel() {

    val googleRepo by application.inject<GoogleRepo>()
    private val placesAround : MutableLiveData<ArrayList<Business>> = MutableLiveData()
    val _placesAround: LiveData<ArrayList<Business>> get() = placesAround



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