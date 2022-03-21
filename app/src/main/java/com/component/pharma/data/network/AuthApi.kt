package com.component.pharma.data.network

import com.component.pharma.model.OtpSend
import com.component.pharma.model.PharmUserModel
import com.component.pharma.model.responses.OtpResponse
import com.component.pharma.model.responses.UserProfileModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @Headers("Content-Type:application/json;")
    @POST("PharmUser/create")
    suspend fun login(
        @Body post: PharmUserModel

    ) : Any


    @Headers("Content-Type:application/json;")
    @POST("PharmUser/verifyotp")
    suspend fun getOtp(
        @Body post: OtpSend

    ) : OtpResponse


    @Headers("Content-Type:application/json;")
    @POST("PhUserProfile/edit")
    suspend fun sendUserProfile(
            @Body post: UserProfileModel

    ) : Any



}