package com.component.pharma.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.component.pharma.data.Resource

import com.component.pharma.data.repository.UploadRepository
import com.component.pharma.model.PrescripModel
import com.component.pharma.model.responses.Categories
import com.component.pharma.model.responses.UploadResponse
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.model.responses.userProfile
import com.component.pharma.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class UploadViewModel(
        private val repository: UploadRepository
): BaseViewModel(repository) {
    private val _prescripResponse : MutableLiveData<Resource<Any>> = MutableLiveData()
    private val _userProfileResponse : MutableLiveData<Resource<Any>> = MutableLiveData()
    private val _ProfileResponse : MutableLiveData<Resource<userProfile>> = MutableLiveData()

    val userProfileResponse: LiveData<Resource<Any>>
        get() = _userProfileResponse

    val prescripResponse: LiveData<Resource<Any>>
        get() = _prescripResponse

    val ProfileResponse: LiveData<Resource<userProfile>>
        get() = _ProfileResponse


    fun sendPrescrip(prescrip: PrescripModel) = viewModelScope.launch {
        _prescripResponse.value = Resource.Loading
        _prescripResponse.value = repository.sendPrescrip(prescrip)
    }
    fun saveUserProfile(user: UserProfileModel) = viewModelScope.launch {
        _userProfileResponse.value = Resource.Loading
        _userProfileResponse.value = repository.saveUserProfile(user)
    }
    suspend fun savePic(pic: String) {
        repository.savePic(pic)
    }

    fun getProfile(pharmUserId:String) = viewModelScope.launch {
        _ProfileResponse.value = repository.getProfile(pharmUserId)
    }

    suspend fun saveInitial(initial: String) {
        repository.saveInitial(initial)
    }

    suspend fun saveProfileType(profileType: String) {
        repository.saveProfileType(profileType)
    }

//    suspend fun onNotifyBadge(badgeStart: String) {
//        repository.onNotifyBadge(badgeStart)
//    }

    suspend fun onBadgeStart(badgeStart: String) {
        repository.onBadgeStart(badgeStart)
    }
}