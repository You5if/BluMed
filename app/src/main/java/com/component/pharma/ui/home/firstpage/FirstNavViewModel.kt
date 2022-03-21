package com.component.pharma.ui.home.firstpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.component.pharma.data.Resource
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.data.repository.ProductRepository
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetProduct
import com.component.pharma.model.responses.GetProductArray
import com.component.pharma.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FirstNavViewModel(
        private val repository: HomeRepository
): BaseViewModel(repository) {

//    private val _productResponse : MutableLiveData<Resource<TestProductModel>> = MutableLiveData()
//
//    val ProductResponse: LiveData<Resource<TestProductModel>>
//        get() = _productResponse



//    fun getProduct() = viewModelScope.launch {
////        _BankResponse.value = Resource.Loading
//        _productResponse.value = repository.getProduct()
//    }

//    fun getSavedProduct() = repository.getSavedProducts()
//
//    fun deleteProduct(product: TestProductModel) = viewModelScope.launch {
//        repository.deleteProduct(product)
//    }
//
//    fun saveProduct(product: TestProductModel) = viewModelScope.launch {
//        repository.upsert(product)
//    }
//    fun updateProduct(product: TestProductModel) = viewModelScope.launch {
//        repository.updateProduct(product)
//    }
}