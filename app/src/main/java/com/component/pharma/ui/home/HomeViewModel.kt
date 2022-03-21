  package com.component.pharma.ui.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.component.pharma.data.Resource
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.model.CompleteObj
import com.component.pharma.model.PrescripModel
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.*
import com.component.pharma.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import java.security.acl.Group

class HomeViewModel(
    private val repository: HomeRepository
): BaseViewModel(repository) {
//        var productList = mutableListOf(
//                TestProductModel(1, 1, "Panadol", "box", "https://i-cf65ch.gskstatic.com/content/dam/cf-consumer-healthcare/panadol/en_ie/ireland-products/panadol-tablets/MGK5158-GSK-Panadol-Tablets-455x455.png", 20, "good", false, 1),
//                TestProductModel(2, 2, "Capsule", "box", "https://image.made-in-china.com/202f0j00JCSRoKudMTck/Ampicillin-Capsule.jpg", 300, "good", false, 1),
//                TestProductModel(3, 3, "dry-Cough", "box", "https://www.benylin.ca/sites/benylin_ca/files/styles/product_image/public/product-images/dry-cough-es_250ml_e.png", 35, "good", false, 1)
//        )
//    lateinit var productList: List<TestProductModel>


    private val _ProductResponse : MutableLiveData<Resource<ResponsePro>> = MutableLiveData()

    private val _CityResponse : MutableLiveData<Resource<GetCities>> = MutableLiveData()

    private val _CategoryResponse : MutableLiveData<Resource<Categories>> = MutableLiveData()
    private val _GroupsResponse : MutableLiveData<Resource<Groups>> = MutableLiveData()
    private val _ProductsResponse : MutableLiveData<Resource<ResponsePro>> = MutableLiveData()
    private val _SProductsResponse : MutableLiveData<Resource<ResponsePro>> = MutableLiveData()

    private val _ProfileResponse : MutableLiveData<Resource<userProfile>> = MutableLiveData()

    private val _AllNotificationsResponse : MutableLiveData<Resource<AllNotificationsResponse>> = MutableLiveData()

    private val _AllOrdersResponse : MutableLiveData<Resource<AllOrdersResponse>> = MutableLiveData()

    private val _OrderDetResponse : MutableLiveData<Resource<OrderDetails>> = MutableLiveData()

    private val _AllNewNotify : MutableLiveData<Resource<NewNotify>> = MutableLiveData()

    private val _userResponse : MutableLiveData<Resource<Any>> = MutableLiveData()

    private val _promoResponse : MutableLiveData<Resource<PromoResponse>> = MutableLiveData()

    private val _orderResponse : MutableLiveData<Resource<Any>> = MutableLiveData()

    val userResponse: LiveData<Resource<Any>>
        get() = _userResponse

    val promoResponse: LiveData<Resource<PromoResponse>>
        get() = _promoResponse

    val orderResponse: LiveData<Resource<Any>>
        get() = _orderResponse


    val ProductResponse: LiveData<Resource<ResponsePro>>
        get() = _ProductResponse

    val CityResponse: LiveData<Resource<GetCities>>
        get() = _CityResponse

    val CategoryResponse: LiveData<Resource<Categories>>
        get() = _CategoryResponse
    val GroupsResponse: LiveData<Resource<Groups>>
        get() = _GroupsResponse
    val ProductsResponse: LiveData<Resource<ResponsePro>>
        get() = _ProductsResponse
    val SProductsResponse: LiveData<Resource<ResponsePro>>
        get() = _SProductsResponse

    val ProfileResponse: LiveData<Resource<userProfile>>
        get() = _ProfileResponse

    val AllNotificationsResponse: LiveData<Resource<AllNotificationsResponse>>
        get() = _AllNotificationsResponse

    val AllOrdersResponse: LiveData<Resource<AllOrdersResponse>>
        get() = _AllOrdersResponse

    val OrderDetResponse: LiveData<Resource<OrderDetails>>
        get() = _OrderDetResponse

    val AllNewNotify: LiveData<Resource<NewNotify>>
        get() = _AllNewNotify


    private val _prescripResponse : MutableLiveData<Resource<Any>> = MutableLiveData()
    private val _userProfileResponse : MutableLiveData<Resource<Any>> = MutableLiveData()

    val userProfileResponse: LiveData<Resource<Any>>
        get() = _userProfileResponse

    val prescripResponse: LiveData<Resource<Any>>
        get() = _prescripResponse



//    init {
//        getApiProduct()
//    }

    fun getApiProduct() = viewModelScope.launch {
        _ProductResponse.value = repository.getApiProduct()
    }

    fun getApiCity() = viewModelScope.launch {
        _CityResponse.value = repository.getApiCity()
    }

    fun getCategory() = viewModelScope.launch {
        _CategoryResponse.value = repository.getCategory()
    }
    fun getGroups(categoryId:String) = viewModelScope.launch {
        _GroupsResponse.value = repository.getGroups(categoryId)
    }
    fun getProducts(groupId:String) = viewModelScope.launch {
        _ProductsResponse.value = repository.getProducts(groupId)
    }
    fun getSearchProducts(searchText:String) = viewModelScope.launch {
        _SProductsResponse.value = repository.getSearchProducts(searchText)
    }

    fun getProfile(pharmUserId:String) = viewModelScope.launch {
        _ProfileResponse.value = repository.getProfile(pharmUserId)
    }

    fun getAllNotifications(pharmUserId:String) = viewModelScope.launch {
        _AllNotificationsResponse.value = repository.getAllNotificaions(pharmUserId)
    }

    fun getAllOrders(pharmUserId:String) = viewModelScope.launch {
        _AllOrdersResponse.value = repository.getAllOrders(pharmUserId)
    }

    fun getOrderDet(orderId:String) = viewModelScope.launch {
        _OrderDetResponse.value = repository.getOrderDet(orderId)
    }

    fun getAllNewNotify(pharmUserId:String) = viewModelScope.launch {
        _AllNewNotify.value = repository.getNewNotify(pharmUserId)
    }

    suspend fun savePic(pic: String) {
        repository.savePic(pic)
    }

    suspend fun saveInitial(initial: String) {
        repository.saveInitial(initial)
    }

    suspend fun saveLastNotificationId(notifyId: String) {
        repository.saveLastNotificationId(notifyId)
    }

    suspend fun saveProfileType(profileType: String) {
        repository.saveProfileType(profileType)
    }

    fun sendPrescrip(prescrip: PrescripModel) = viewModelScope.launch {
        _prescripResponse.value = Resource.Loading
        _prescripResponse.value = repository.sendPrescrip(prescrip)
    }






    fun getUserSI(id:String) = viewModelScope.launch {
        _userResponse.value = Resource.Loading
        _userResponse.value = repository.getUserSI(id)
    }

    fun getPromoData(pCode:String) = viewModelScope.launch {
        _promoResponse.value = repository.getPromoData(pCode)
    }

    fun sendOrder(order:CompleteObj) = viewModelScope.launch {
        _orderResponse.value = repository.sendOrder(order)
    }

    suspend fun saveUserStatus(userStatus: Int) {
        repository.saveUserStatus(userStatus)
    }

    fun saveProduct(product: TestProductModel) = viewModelScope.launch {
        repository.upsert(product)
    }

    fun saveProm(prom: GetPromItems) = viewModelScope.launch {
        repository.upsert2(prom)
    }

    fun getSavedProduct() = repository.getSavedProducts()
    fun getSavedProm() = repository.getSavedProms()

    fun updateProduct(product: TestProductModel) = viewModelScope.launch {
        repository.updateProduct(product)
    }
    fun updateProm(prom: GetPromItems) = viewModelScope.launch {
        repository.updateProm(prom)
    }

    fun deleteProduct(product: TestProductModel) = viewModelScope.launch {
        repository.deleteProduct(product)
    }
    fun deleteProm(prom: GetPromItems) = viewModelScope.launch {
        repository.deleteProm(prom)
    }
    suspend fun onBadgeStart(badgeStart: String) {
        repository.onBadgeStart(badgeStart)
    }

    suspend fun onNotifyCartBadge(badgeStart: String) {
        repository.onNotifyCartBadge(badgeStart)
    }

    fun saveUserProfile(user: UserProfileModel) = viewModelScope.launch {
        _userProfileResponse.value = Resource.Loading
        _userProfileResponse.value = repository.saveUserProfile(user)
    }

}
