package com.lovisgod.safehaven.retrofit

import com.lovisgod.safehaven.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL: String = "https://maps.googleapis.com/maps/api/place/"

private val sLogLevel =
    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val _interceptor = HttpLoggingInterceptor()
val _client: OkHttpClient =
    OkHttpClient.Builder().addInterceptor(_interceptor.setLevel(sLogLevel)).build()

object GoogleNetwork {

    /**
     * Creates retrofit instance of api service.
     */
    operator fun invoke(accessToken: String): GoogleService {

        val authInterceptor = Interceptor {

            var request = it.request()
            if (request.header("No-Authentication") == null) {

                if (accessToken.isNotEmpty()) {
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer $accessToken")
                        .build()
                }
                it.proceed(request)
            } else {
                it.proceed(request)
            }

        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(_interceptor.setLevel(sLogLevel))
        /*.connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)*/


        val retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val googleService: GoogleService by lazy {
            retrofit.create(GoogleService::class.java)
        }
        return googleService
    }
}