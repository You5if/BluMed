package com.component.pharma.ui.home.thirdpage.orders.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentOrderBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.thirdpage.orders.OrderAdapter
import com.component.pharma.ui.home.thirdpage.orders.OrdersAdapter
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch

class OrderFragment : BaseFragment<HomeViewModel, FragmentOrderBinding, HomeRepository>() {


    lateinit var invoiceId: String
    lateinit var orderAdapter: OrderAdapter
//    lateinit var phoneNumber: String
//    lateinit var invoiceDate: String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        invoiceId = arguments?.getString("invoiceId")!!

    getOrderDet(invoiceId)
//        phoneNumber = arguments?.getString("phoneNumber")!!
//        invoiceDate = arguments?.getString("invoiceDate")!!



    viewModel.OrderDetResponse.observe(viewLifecycleOwner, Observer { response ->
        binding.shimmerFrameLayout.stopShimmerAnimation()
        binding.shimmerFrameLayout.visible(response is Resource.Loading)
        binding.rvOrder.visibility = View.VISIBLE
        when (response) {
            is Resource.Success -> {
                if (response.value.isNotEmpty()) {
                    binding.inoviceDate.text = response.value[0].invoiceDate
                    binding.phoneNumber.text = response.value[0].phoneNumber
                    binding.city.text = response.value[0].city
                    binding.inoviceNo.text = response.value[0].invoiceNo
                    binding.rvOrder.apply {
                        orderAdapter = OrderAdapter(response.value)
                        adapter = orderAdapter
                        layoutManager = LinearLayoutManager(context)
                    }
                }else {
                    binding.rvOrder.visibility = View.GONE
                    binding.shimmerFrameLayout.visible(true)
                    binding.shimmerFrameLayout.startShimmerAnimation()
                }
            }
            is Resource.Failure -> handleApiError(response) {
                viewModel.getOrderDet(invoiceId)
            }
        }
    })

    }

    private fun getOrderDet(id: String) {
        lifecycleScope.launch {

                viewModel.getOrderDet(id)

        }
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

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentOrderBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)

}