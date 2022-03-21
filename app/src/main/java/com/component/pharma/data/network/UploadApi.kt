package com.component.pharma.data.network

import com.component.pharma.model.PharmUserModel
import com.component.pharma.model.PrescripModel
import com.component.pharma.model.responses.UploadResponse
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.model.responses.userProfile

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface UploadApi {

    @Headers("Content-Type:application/json;")
    @POST("Prescrip/create")
    suspend fun sendPrescrip(
            @Body prescrip: PrescripModel

    ) : Any




    @Multipart
    @POST("file/upload")
    fun uploadImage(
            @Part image: MultipartBody.Part
    ) : Call<UploadResponse>

    @Headers("Content-Type:application/json;")
    @POST("PhUserProfile/edit")
    suspend fun sendUserProfile(
            @Body post: UserProfileModel

    ) : Any

    @Headers("Content-Type:application/json;")
    @GET("PharmUser/getprofileinfo/{pharmUserId}")
    suspend fun getProfile(
            @Path("pharmUserId") pharmUserId: String
    ): userProfile

    companion object{
        operator fun invoke() : UploadApi {
            return Retrofit.Builder()
                    .baseUrl("http://pharmacyapi.autopay-mcs.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(UploadApi::class.java)
        }
    }

}