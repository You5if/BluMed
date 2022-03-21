package com.component.pharma.ui.home.firstpage.search.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentSearchBinding
import com.component.pharma.model.TestProductModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.firstpage.ProductAdapter
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class SearchFragment : BaseFragment<HomeViewModel, FragmentSearchBinding, HomeRepository>() {

    lateinit var productAdapter: ProductAdapter
    lateinit var productList: MutableList<TestProductModel>

    lateinit var textS: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductAdapter()
        binding.etSearch.requestFocus()

        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.firstNavFragment)
        }
        binding.searchIcon.setOnClickListener {
            productList = mutableListOf()
            textS = binding.etSearch.text.toString()
            getSearchProducts(textS)
            binding.shimmerFrameLayout.startShimmerAnimation()
            binding.shimmerFrameLayout.visibility = View.VISIBLE
            binding.rvSearchProducts.visibility = View.GONE
            binding.rvSearchProducts.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(activity)
            }
        }

        viewModel.SProductsResponse.observe(viewLifecycleOwner, Observer { response ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visible(response is Resource.Loading)
            binding.rvSearchProducts.visibility = View.VISIBLE
            when (response) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        for (pro in response.value) {
                            productList.add(TestProductModel(
                                    pro.phProductId,
                                    pro.active,
                                    pro.apiImagePath,
                                    pro.apiPath,
                                    pro.barcode,
                                    pro.bodysys,
                                    pro.bodysys2,
                                    pro.categoryName,
                                    pro.description,
                                    pro.dosage,
                                    pro.extension,
                                    pro.fileName,
                                    pro.fullPath,
                                    pro.groupName,
                                    pro.localAgent,
                                    pro.manufacturer,
                                    pro.originCou,
                                    pro.originalFileName,
                                    pro.pack,
                                    pro.phProdCatId,
                                    pro.phProdGroupId,
                                    pro.phProdUnitId,
                                    pro.phSaleUnitId,
                                    pro.phWarehouseId,
                                    pro.prescClass,
                                    pro.price,
                                    pro.productCode,
                                    pro.productName,
                                    pro.productUnit,
                                    pro.qrcode,
                                    pro.saleUnit,
                                    pro.sciName,
                                    pro.theraputic,
                                    pro.unitMeasure,
                                    pro.wareHouseName,
                                    false,
                                    1
                            )
                            )

                        }

                        viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
                            for (testProduct in productList) {
                                for (dbProduct in it) {
                                    if (dbProduct.phProductId == testProduct.phProductId) {
                                        testProduct.removeBtn = true
                                    }
                                }
                            }
                            productAdapter.differ.submitList(productList)
                        })



                        productAdapter.setOnItemClickListener {
                            it.removeBtn = true
                            viewModel.saveProduct(it)
                            (activity as HomeActivity).addCartBadge()
                        }

                        productAdapter.setOnItemClickListener2 {
                            it.removeBtn = false
                            for (product in productList) {
                                if (it.phProductId == product.phProductId) {
                                    product.quantity = 1
                                }
                            }
                            viewModel.deleteProduct(it)


                        }

                    }
                }
                is Resource.Failure -> handleApiError(response) { getSearchProducts(textS) }
            }
        })
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

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentSearchBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)



}