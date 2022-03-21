package com.component.pharma.data.network

import okhttp3.ResponseBody
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
    @Headers("Content-Type:application/json;")
    @POST("logout")
    suspend fun logout(): ResponseBody
}