package com.component.pharma.data.network

import com.component.pharma.model.responses.GetProduct
import com.component.pharma.model.responses.GetProductArray
import retrofit2.http.Headers
import retrofit2.http.POST

interface ProductApi {

    @Headers("Content-Type:application/json;")
    @POST("phproduct/getpagedata/86/2/10/1/86/false")
    suspend fun getProduct(): ArrayList<GetProduct>
}