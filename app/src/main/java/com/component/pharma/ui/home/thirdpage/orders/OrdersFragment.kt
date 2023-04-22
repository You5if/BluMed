package com.component.pharma.ui.home.thirdpage.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.component.pharma.databinding.FragmentNotificationBinding
import com.component.pharma.databinding.FragmentOrdersBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.thirdpage.notification.NotificationAdapter
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class OrdersFragment : BaseFragment<HomeViewModel, FragmentOrdersBinding, HomeRepository>() {


    lateinit var ordersAdapter: OrdersAdapter

    lateinit var bundle: Bundle


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllOrders()

        viewModel.AllOrdersResponse.observe(viewLifecycleOwner, Observer { response ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visible(response is Resource.Loading)


            when (response) {
                is Resource.Success -> {
                    if (response.value.isEmpty()) {
                        binding.noOrders.visibility = View.VISIBLE
                    }else {
                        binding.rvOrders.visibility = View.VISIBLE
                    }
                    lifecycleScope.launch {
                        binding.rvOrders.apply {
                            ordersAdapter = OrdersAdapter(response.value)
                            adapter = ordersAdapter
                            layoutManager = LinearLayoutManager(context)
                        }

                        ordersAdapter.setOnItemClickListener { order ->
                            bundle = Bundle().apply {

                                putString("invoiceId", order.phInvoiceId.toString())
                                putString("phoneNumber", order.phoneNumber)
                                putString("invoiceDate", order.invoiceDate)

                            }
                            findNavController().navigate(
                                    R.id.action_ordersFragment_to_orderFragment,
                                    bundle
                            )
                        }
                    }
                }
                is Resource.Failure -> handleApiError(response) {
                    lifecycleScope.launch {
                        userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer { userId ->
                            viewModel.getAllNotifications(userId.toString())
                        })
                    }
                }
            }
        })
    }

    private fun getAllOrders() {
        lifecycleScope.launch {
            userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer { userId ->
                viewModel.getAllOrders(userId.toString())
            })
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

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentOrdersBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)

}