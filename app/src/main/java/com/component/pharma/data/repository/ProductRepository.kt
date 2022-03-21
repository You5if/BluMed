package com.component.pharma.data.repository

import com.component.pharma.data.UserPreferences
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.data.network.ProductApi
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetProduct
import com.component.pharma.ui.base.BaseRepository

class ProductRepository (
        private val api: ProductApi,
        private val preferences: UserPreferences,
        private val db: ProductDatabase
): BaseRepository() {
    suspend fun getProduct() = safeApiCall {
        api.getProduct()
    }

//    fun getSavedProducts() = db.getProductDao().getAllProducts()

//    suspend fun upsert(product: TestProductModel) = db.getProductDao().upsert(product)
//
//    suspend fun deleteProduct(product: TestProductModel) = db.getProductDao().deleteProduct(product)
//    suspend fun updateProduct(product: Tes) = db.getProductDao().updateProducts(product)
}