package com.component.pharma.ui.base

import androidx.lifecycle.ViewModel
import com.component.pharma.data.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel (
    private val repository: BaseRepository
): ViewModel() {

    suspend fun logout(api: UserApi) = withContext(Dispatchers.IO) {repository.logout(api)}
}