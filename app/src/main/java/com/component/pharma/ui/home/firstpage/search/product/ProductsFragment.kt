package com.component.pharma.ui.home.firstpage.search.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentFirstNavBinding
import com.component.pharma.databinding.FragmentProductsBinding
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.ProductsItem
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.firstpage.ProductAdapter
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class ProductsFragment : BaseFragment<HomeViewModel, FragmentProductsBinding, HomeRepository>() {

    lateinit var productAdapter: ProductAdapter
    lateinit var productList: MutableList<TestProductModel>

    lateinit var bundle: Bundle

    lateinit var groupId: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupId = arguments?.getString("groupId")!!
        getProducts(groupId)

        productList = mutableListOf()
        productAdapter = ProductAdapter()

        binding.rvProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel.ProductsResponse.observe(viewLifecycleOwner, Observer { response ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visible(response is Resource.Loading)
            binding.rvProducts.visibility = View.VISIBLE
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

                            lifecycleScope.launch {
                                it.removeBtn = true
                                viewModel.onNotifyCartBadge("Start")
                                (activity as HomeActivity).addCartBadge()
                                viewModel.saveProduct(it)

                            }
                        }

                        productAdapter.setOnItemClickListener2 {
                            it.removeBtn = false
                            for (product in productList) {
                                if (it.phProductId == product.phProductId) {
                                    product.quantity = 1
                                }
                            }
                            viewModel.deleteProduct(it)
                            viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
                                if (it.isNullOrEmpty()) {
                                    (activity as HomeActivity).removeCartBadge()
                                } })


                        }

                    }
                }
                is Resource.Failure -> handleApiError(response){ getProducts(groupId) }
            }
        })

    }

    private fun getProducts(id: String){
        viewModel.getProducts(id)
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

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentProductsBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)


}
