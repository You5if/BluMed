package com.component.pharma.ui.home.fourthpage

import android.os.Bundle
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
import com.component.pharma.databinding.FragmentFourthNavBinding
import com.component.pharma.model.TestProductModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.firstpage.FirstNavViewModel
import com.component.pharma.ui.home.firstpage.ProductAdapter
import com.google.android.material.badge.BadgeDrawable
import kotlinx.coroutines.launch


class FourthNavFragment : BaseFragment<HomeViewModel, FragmentFourthNavBinding, HomeRepository>() {

    lateinit var cartAdapter: CartAdapter
    var totalPrice: Double = 0.00
//    lateinit var bundle: Bundle
//    var productList = mutableListOf(
//            TestProductModel(1, 1, "Panadol", "box", "https://i-cf65ch.gskstatic.com/content/dam/cf-consumer-healthcare/panadol/en_ie/ireland-products/panadol-tablets/MGK5158-GSK-Panadol-Tablets-455x455.png", 20, "good", false, 1),
//            TestProductModel(2, 2, "Capsule", "box", "https://image.made-in-china.com/202f0j00JCSRoKudMTck/Ampicillin-Capsule.jpg", 300, "good", false, 1),
//            TestProductModel(3, 3, "dry-Cough", "box", "https://www.benylin.ca/sites/benylin_ca/files/styles/product_image/public/product-images/dry-cough-es_250ml_e.png", 35, "good", false, 1)
//    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//        productList = mutableListOf()
        setupRecyclerView()

        (activity as HomeActivity).setLocationPrice(0.00, 0, "Choose a location")

        viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {

            if (!it.isNullOrEmpty()) {
                binding.rvCart.visibility = View.VISIBLE
                binding.cardView.visibility = View.VISIBLE
                binding.cartillust.visibility = View.GONE
                binding.shoppingBtn.visibility = View.GONE

                cartAdapter.setData(it)
                totalPrice = 0.00
                binding.total.text = totalPrice.toString()
                for (product in it){
                    totalPrice += product.price * product.quantity
                    binding.total.text = totalPrice.toString()
                }
            } else {
                binding.rvCart.visibility = View.GONE
                binding.cardView.visibility = View.GONE
                binding.cartillust.visibility = View.VISIBLE
                binding.shoppingBtn.visibility = View.VISIBLE
            }
        })

        binding.shoppingBtn.setOnClickListener {
            findNavController().navigate(R.id.firstNavFragment)
        }


        cartAdapter.setOnItemClickListener2 { it2 ->
            if (it2.quantity <= 1) {
                viewModel.deleteProduct(it2)
                    viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
                        cartAdapter.setData(it)
                        totalPrice = 0.00
                        if (it.size == 0) {
                            totalPrice = 0.00
                            binding.total.text = totalPrice.toString()
                        }else{
                            for (product in it){
                                totalPrice += product.price * product.quantity
                                binding.total.text = totalPrice.toString()
                            }
                        }

                    })
//                }
            } else {

                it2.quantity -= 1
                cartAdapter.notifyDataSetChanged()
                viewModel.updateProduct(it2)
                viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
                    cartAdapter.setData(it)
                    totalPrice = 0.00
                    for (product in it){
                        totalPrice += product.price * product.quantity
                        binding.total.text = totalPrice.toString()
                    }
                })



            }

        }

        cartAdapter.setOnItemClickListener1 { it3 ->
            it3.quantity += 1
            cartAdapter.notifyDataSetChanged()
            viewModel.updateProduct(it3)
            viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
                cartAdapter.setData(it)
                totalPrice = 0.00
                for (product in it){
                    totalPrice += product.price * product.quantity
                    binding.total.text = totalPrice.toString()
                }
            })


        }
//        binding.total.text = totalPrice.toString()
        binding.total.text = String.format("%.2f", totalPrice).toDouble().toString()

        binding.tvConfirm.setOnClickListener {
//            bundle = Bundle().apply {
//                putString("totalP", totalPrice.toString())
//            }
            (activity as HomeActivity).setSubTotal(totalPrice)
            findNavController().navigate(R.id.action_fourthNavFragment_to_confirmFragment)
        }


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
//                                    pro.description,
//                                    pro.extension,
//                                    pro.fileName,
//                                    pro.fullPath,
//                                    pro.groupName,
//                                    pro.originalFileName,
//                                    pro.phProdCatId,
//                                    pro.phProdGroupId,
//                                    pro.phProdUnitId,
//                                    pro.phProductId,
//                                    pro.phSaleUnitId,
//                                    pro.phWarehouseId,
//                                    pro.price.toInt(),
//                                    pro.productCode,
//                                    pro.productName,
//                                    pro.productUnit,
//                                    pro.qrcode,
//                                    pro.saleUnit,
//                                    pro.sciName,
//                                    pro.totalPages,
//                                    pro.totalRecords,
//                                    pro.unitMeasure,
//                                    pro.wareHouseName,
//                                    false,
//                                    1))
//                            num +=1
//                        }
//
//
//
//                    }
//                }
//                is Resource.Failure -> {
//                    lifecycleScope.launch {
//                        Toast.makeText(context, "${response.errorCode}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
//        })
//

//        setupRecyclerView()
//        viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
//
//            cartAdapter.setData(it)
//            totalPrice = 0
//            for (product in it){
//                totalPrice += product.price * product.quantity
//                binding.total.text = totalPrice.toString()
//            }
//        })
//
//        cartAdapter.setOnItemClickListener2 { it2 ->
//            if (it2.quantity <= 1) {
//                for (product in productList) {
//                    if (it2.phProductId == product.phProductId) {
//                        product.removeBtn = false
//                        viewModel.deleteProduct(it2)
//                    }
//                    viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
//                        cartAdapter.setData(it)
//                        totalPrice = 0
//                        if (it.size == 0) {
//                            totalPrice = 0
//                            binding.total.text = totalPrice.toString()
//                        }else{
//                            for (product in it){
//                                totalPrice += product.price * product.quantity
//                                binding.total.text = totalPrice.toString()
//                            }
//                        }
//
//                    })
//                }
//            } else {
//
//                it2.quantity -= 1
//                cartAdapter.notifyDataSetChanged()
//                viewModel.updateProduct(it2)
//                viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
//                    cartAdapter.setData(it)
//                    totalPrice = 0
//                    for (product in it){
//                        totalPrice += product.price * product.quantity
//                        binding.total.text = totalPrice.toString()
//                    }
//                })
//
//
//
//            }
//
//        }
//
//        cartAdapter.setOnItemClickListener1 { it3 ->
//            it3.quantity += 1
//            cartAdapter.notifyDataSetChanged()
//            viewModel.updateProduct(it3)
//            viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
//                        cartAdapter.setData(it)
//                totalPrice = 0
//                for (product in it){
//                    totalPrice += product.price * product.quantity
//                    binding.total.text = totalPrice.toString()
//                }
//            })
//
//
//        }
//
//        binding.total.text = totalPrice.toString()

    }

    fun setupRecyclerView() {

        binding.rvCart.apply {
            cartAdapter = CartAdapter()
            adapter = cartAdapter
            layoutManager = LinearLayoutManager(activity)

        }
    }





    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentFourthNavBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)



}