package com.component.pharma.data.network

import com.component.pharma.data.UserPreferences
import com.component.pharma.model.CompleteObj
import com.component.pharma.model.OtpSend
import com.component.pharma.model.PrescripModel
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface HomeApi {



    @Headers("Content-Type:application/json;")
    @POST("pharmuser/updateusertoactive/{id}")
    suspend fun getUserSI(
            @Path("id") id: String
    ): Any

    @Headers("Content-Type:application/json;")
    @POST("phproduct/getpagedata/86/2/10/1/86/false")
    suspend fun getProduct(): ResponsePro


    @Headers("Content-Type:application/json;")
    @POST("PhDeliverPrice/getpagedata/74/2/10/1/74/false")
    suspend fun getCityData(): GetCities

    @Headers("Content-Type:application/json;")
    @GET("PhProdCat/fetchall")
    suspend fun getCategory(): Categories

    @Headers("Content-Type:application/json;")
    @GET("phprodgroup/bycategory/{categoryId}")
    suspend fun getGroups(
        @Path("categoryId") categoryId: String
    ): Groups

    @Headers("Content-Type:application/json;")
    @GET("phproduct/bygroup/{groupId}")
    suspend fun getProducts(
        @Path("groupId") groupId: String
    ): ResponsePro


    @Headers("Content-Type:application/json;")
    @GET("phproduct/bysearch/{searchText}")
    suspend fun getSearchProducts(
            @Path("searchText") searchText: String
    ): ResponsePro


    @Headers("Content-Type:application/json;")
    @GET("phpromo/code/{pCode}")
    suspend fun getPromo(
        @Path("pCode") pCode: String
    ): PromoResponse


    @Headers("Content-Type:application/json;")
    @POST("PhInvoice/createcart")
    suspend fun sendOrder(
        @Body post: CompleteObj

    ) : Any

    @Headers("Content-Type:application/json;")
    @GET("PharmUser/getprofileinfo/{pharmUserId}")
    suspend fun getProfile(
            @Path("pharmUserId") pharmUserId: String
    ): userProfile

    @Headers("Content-Type:application/json;")
    @GET("PhInvProd/order/{orderId}")
    suspend fun getOrderDet(
            @Path("orderId") orderId: String
    ): OrderDetails

    @Headers("Content-Type:application/json;")
    @GET("Notification/byuserall/{pharmUserId}")
    suspend fun getAllNotifications(
            @Path("pharmUserId") pharmUserId: String
    ): AllNotificationsResponse

    @Headers("Content-Type:application/json;")
    @GET("PhInvoice/byuser/{pharmUserId}")
    suspend fun getAllOrders(
            @Path("pharmUserId") pharmUserId: String
    ): AllOrdersResponse

    @Headers("Content-Type:application/json;")
    @GET("Notification/byuser/{pharmUserId}")
    suspend fun getNewNotify(
        @Path("pharmUserId") pharmUserId: String
    ): NewNotify

    @Headers("Content-Type:application/json;")
    @POST("PhUserProfile/edit")
    suspend fun sendUserProfile(
            @Body post: UserProfileModel

    ) : Any

    @Headers("Content-Type:application/json;")
    @POST("Prescrip/create")
    suspend fun sendPrescrip(
            @Body prescrip: PrescripModel

    ) : Any

//    @Multipart
//    @POST("file/upload")
//    fun uploadImage(
//            @Part image: MultipartBody.Part
//    ) : Call<UploadResponse>
}