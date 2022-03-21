package com.component.pharma.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.component.pharma.data.Resource
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.model.OtpSend
import com.component.pharma.model.PharmUserModel
import com.component.pharma.model.responses.OtpResponse
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel (
    private val repository: AuthRepository
): BaseViewModel(repository) {


    private val _loginResponse : MutableLiveData<Resource<Any>> = MutableLiveData()
    private val _userProfileResponse : MutableLiveData<Resource<Any>> = MutableLiveData()
    private val _otpResponse : MutableLiveData<Resource<OtpResponse>> = MutableLiveData()


    val loginResponse: LiveData<Resource<Any>>
        get() = _loginResponse

    val userProfileResponse: LiveData<Resource<Any>>
        get() = _userProfileResponse

    val otpResponse: LiveData<Resource<OtpResponse>>
        get() = _otpResponse

    fun login(post: PharmUserModel) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(post)
    }

    fun saveUserProfile(user: UserProfileModel) = viewModelScope.launch {
        _userProfileResponse.value = Resource.Loading
        _userProfileResponse.value = repository.saveUserProfile(user)
    }

    fun getOtp(sub: OtpSend) = viewModelScope.launch {
        _otpResponse.value = Resource.Loading
        _otpResponse.value = repository.getOtp(sub)
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }
    suspend fun saveFirstName(firstName: String) {
        repository.saveFirstName(firstName)
    }
    suspend fun saveLastName(lastName: String) {
        repository.saveLastName(lastName)
    }
    suspend fun savePic(pic: String) {
        repository.savePic(pic)
    }
    suspend fun saveProfileType(profileType: String) {
        repository.saveProfileType(profileType)
    }
    suspend fun saveMobile(mobile: String) {
        repository.saveMobile(mobile)
    }
//
    suspend fun onboardingFinished(finshed: String) {
        repository.onboardingFinished(finshed)
    }

    suspend fun saveUserStatus(userStatus: Int) {
        repository.saveUserStatus(userStatus)
    }
    suspend fun saveUserProfileId(userProfileId: Int) {
        repository.saveUserProfileId(userProfileId)
    }
    suspend fun saveUserId(userId: String) {
        repository.saveUserId(userId)
    }

//    fun login2(post: LoginPost) {
//        viewModelScope.launch {
//
//            _loginResponse.value = repository.login(post)
//        }
//    }
}