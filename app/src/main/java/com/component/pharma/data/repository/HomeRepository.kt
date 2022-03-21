package com.component.pharma.data.repository

import android.media.Image
import com.component.pharma.data.UserPreferences
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.data.db.promtionsdp.PromotionsDatabase
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.network.HomeApi
import com.component.pharma.model.CompleteObj
import com.component.pharma.model.PharmUserModel
import com.component.pharma.model.PrescripModel
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetPromItems
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.ui.base.BaseRepository
import okhttp3.MultipartBody

class HomeRepository(
    private val api: HomeApi,
    private val preferences: UserPreferences,
    private val db: ProductDatabase,
    private val db2: PromotionsDatabase

): BaseRepository() {

    suspend fun getApiProduct() = safeApiCall {
        api.getProduct()
    }
    suspend fun getApiCity() = safeApiCall {
        api.getCityData()
    }

    suspend fun savePic(pic: String) {
        preferences.savePic(pic)
    }

    suspend fun saveInitial(initial: String) {
        preferences.saveInitial(initial)
    }

    suspend fun saveLastNotificationId(notifyId: String) {
        preferences.saveLastNotificationId(notifyId)
    }

    suspend fun saveProfileType(profileType: String) {
        preferences.saveProfileType(profileType)
    }

    suspend fun getProfile(pharmUserId: String) = safeApiCall {
        api.getProfile(pharmUserId)
    }

    suspend fun getAllNotificaions(pharmUserId: String) = safeApiCall {
        api.getAllNotifications(pharmUserId)
    }

    suspend fun getAllOrders(pharmUserId: String) = safeApiCall {
        api.getAllOrders(pharmUserId)
    }

    suspend fun getOrderDet(orderId: String) = safeApiCall {
        api.getOrderDet(orderId)
    }

    suspend fun getNewNotify(pharmUserId: String) = safeApiCall {
        api.getNewNotify(pharmUserId)
    }

    suspend fun getCategory() = safeApiCall {
        api.getCategory()
    }

    suspend fun getGroups(categoryId: String) = safeApiCall {
        api.getGroups(categoryId)
    }

    suspend fun getProducts(groupId: String) = safeApiCall {
        api.getProducts(groupId)
    }

    suspend fun getSearchProducts(searchText: String) = safeApiCall {
        api.getSearchProducts(searchText)
    }

    suspend fun getUserSI(id: String) = safeApiCall {
        api.getUserSI(id)
    }

    suspend fun getPromoData(pCode: String) = safeApiCall {
        api.getPromo(pCode)
    }

    suspend fun sendOrder(order: CompleteObj) = safeApiCall {
        api.sendOrder(order)
    }
    suspend fun saveUserStatus(userStatus: Int) {
        preferences.saveUserStatus(userStatus)
    }

    suspend fun onBadgeStart(badgeStart: String) {
        preferences.saveBadgeStart(badgeStart)
    }

    suspend fun saveUserProfile(user: UserProfileModel) = safeApiCall {
        api.sendUserProfile(user)
    }

    suspend fun sendPrescrip(prescrip: PrescripModel) = safeApiCall {
        api.sendPrescrip(prescrip)
    }

    suspend fun onNotifyCartBadge(badgeStart: String) {
        preferences.saveNotifyCartBadge(badgeStart)
    }

    suspend fun upsert(product: TestProductModel) = db.getProductDao().upsert(product)
    suspend fun upsert2(prom: GetPromItems) = db2.getPromDao().upsert(prom)

    fun getSavedProducts() = db.getProductDao().getAllProducts()
    fun getSavedProms() = db2.getPromDao().getAllProms()

    suspend fun deleteProduct(product: TestProductModel) = db.getProductDao().deleteProduct(product)
    suspend fun deleteProm(prom: GetPromItems) = db2.getPromDao().deleteProm(prom)


    suspend fun updateProduct(product: TestProductModel) = db.getProductDao().updateProducts(product)
    suspend fun updateProm(prom: GetPromItems) = db2.getPromDao().updateProm(prom)
}