package com.component.pharma.data.repository

import com.component.pharma.data.UserPreferences
import com.component.pharma.data.network.UploadApi
import com.component.pharma.model.PrescripModel
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.ui.base.BaseRepository
import okhttp3.MultipartBody

class UploadRepository(
        private val api: UploadApi,
        private val preferences: UserPreferences

): BaseRepository() {

//    suspend fun uploadImage(image: MultipartBody.Part) = safeApiCall {
//        api.uploadImage(image)
//    }

    suspend fun sendPrescrip(prescrip: PrescripModel) = safeApiCall {
        api.sendPrescrip(prescrip)
    }
    suspend fun saveUserProfile(user: UserProfileModel) = safeApiCall {
        api.sendUserProfile(user)
    }
    suspend fun savePic(pic: String) {
        preferences.savePic(pic)
    }
    suspend fun getProfile(pharmUserId: String) = safeApiCall {
        api.getProfile(pharmUserId)
    }

    suspend fun saveInitial(initial: String) {
        preferences.saveInitial(initial)
    }

    suspend fun saveProfileType(profileType: String) {
        preferences.saveProfileType(profileType)
    }

//    suspend fun onNotifyBadge(badgeStart: String) {
//        preferences.saveNotifyBadge(badgeStart)
//    }

    suspend fun onBadgeStart(badgeStart: String) {
        preferences.saveBadgeStart(badgeStart)
    }
}