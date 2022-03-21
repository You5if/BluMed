package com.component.pharma.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.data.repository.ProductRepository
import com.component.pharma.data.repository.UploadRepository
import com.component.pharma.ui.auth.AuthViewModel
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.firstpage.FirstNavViewModel
import com.component.pharma.ui.upload.UploadViewModel
import java.lang.IllegalArgumentException

open class ViewModelFactory(
    protected val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository as AuthRepository) as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository) as T
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> UploadViewModel(repository as UploadRepository) as T
//            modelClass.isAssignableFrom(FirstNavViewModel::class.java) -> FirstNavViewModel(repository as ProductRepository) as T

            else -> throw IllegalArgumentException("ViewModelClass Not Found")

        }
    }

}