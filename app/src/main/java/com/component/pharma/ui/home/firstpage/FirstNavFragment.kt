package com.component.pharma.ui.home.firstpage

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.network.ProductApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.data.repository.ProductRepository
import com.component.pharma.databinding.FragmentFirstNavBinding
import com.component.pharma.model.TestProductModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.fourthpage.CartAdapter
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.visible
import com.google.android.material.badge.BadgeDrawable
import kotlinx.coroutines.launch
import kotlin.math.log


class FirstNavFragment : BaseFragment<HomeViewModel, FragmentFirstNavBinding, HomeRepository>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.searchIcon.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        binding.etSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
//        productList = mutableListOf()
//        productAdapter = ProductAdapter()
//
//        binding.rvProducts.apply {
//            adapter = productAdapter
//            layoutManager = LinearLayoutManager(activity)
//        }
//
//        viewModel.ProductResponse.observe(viewLifecycleOwner, Observer {response ->
//            when(response) {
//                is Resource.Success -> {
//                    lifecycleScope.launch {
//                        var num = 0
//                        for (pro in response.value) {
//                            productList.add(TestProductModel(num,
//                                    pro.active,
//                                    pro.apiImagePath,
//                                    pro.apiPath,
//                                    pro.barcode,
//                                    pro.categoryName,
//                            pro.description,
//                            pro.extension,
//                            pro.fileName,
//                            pro.fullPath,
//                            pro.groupName,
//                            pro.originalFileName,
//                            pro.phProdCatId,
//                            pro.phProdGroupId,
//                            pro.phProdUnitId,
//                            pro.phProductId,
//                            pro.phSaleUnitId,
//                            pro.phWarehouseId,
//                            pro.price,
//                            pro.productCode,
//                            pro.productName,
//                            pro.productUnit,
//                            pro.qrcode,
//                            pro.saleUnit,
//                            pro.sciName,
//                            pro.totalPages,
//                            pro.totalRecords,
//                            pro.unitMeasure,
//                            pro.wareHouseName,
//                            false,
//                            1))
//                            num +=1
//                        }
//
//                        viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
//                            for (testProduct in productList) {
//                                for (dbProduct in it) {
//                                    if (dbProduct.phProductId == testProduct.phProductId) {
//                                        testProduct.removeBtn = true
//                                    }
//                                }
//                            }
//                            productAdapter.differ.submitList(productList)
//                        })
//
//
//
//                        productAdapter.setOnItemClickListener {
//                            it.removeBtn = true
//                            viewModel.saveProduct(it)
//                        }
//
//                        productAdapter.setOnItemClickListener2 {
//                            it.removeBtn = false
//                            for (product in productList) {
//                                if (it.phProductId == product.phProductId) {
//                                    product.quantity = 1
//                                }
//                            }
//                            viewModel.deleteProduct(it)
//
//
//                        }
//
//                    }
//                }
//                is Resource.Failure -> {
//                    lifecycleScope.launch {
//                        Toast.makeText(context, "${response.errorBody}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        })




    }

    private fun getSearchProducts(searchText: String){
        viewModel.getSearchProducts(searchText)
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentFirstNavBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)


}

