package com.component.pharma.data.repository

import com.component.pharma.data.UserPreferences
import com.component.pharma.data.network.AuthApi
import com.component.pharma.model.OtpSend
import com.component.pharma.model.PharmUserModel
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.ui.base.BaseRepository

class AuthRepository(
    private val api: AuthApi,
    private val preferences: UserPreferences

): BaseRepository() {




    suspend fun login(post: PharmUserModel) = safeApiCall {
        api.login(post)
    }

    suspend fun saveUserProfile(user: UserProfileModel) = safeApiCall {
        api.sendUserProfile(user)
    }

    suspend fun getOtp(sub: OtpSend) = safeApiCall {
        api.getOtp(sub)
    }

    suspend fun saveAuthToken(token: String) {
        preferences.saveAuthToken(token)
    }
    suspend fun saveFirstName(firstName: String) {
        preferences.saveFirstName(firstName)
    }
    suspend fun saveLastName(lastName: String) {
        preferences.saveLastName(lastName)
    }
    suspend fun savePic(pic: String) {
        preferences.savePic(pic)
    }

    suspend fun saveProfileType(profileType: String) {
        preferences.saveProfileType(profileType)
    }
    suspend fun saveMobile(mobile: String) {
        preferences.saveMobile(mobile)
    }
//
    suspend fun onboardingFinished(finished: String) {
        preferences.saveOnboarding(finished)
    }
    suspend fun saveUserStatus(userStatus: Int) {
        preferences.saveUserStatus(userStatus)
    }
    suspend fun saveUserProfileId(userProfileId: Int) {
        preferences.saveUserProfileId(userProfileId)
    }
    suspend fun saveUserId(userId: String) {
        preferences.saveUserId(userId)
    }

}