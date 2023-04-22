package com.component.pharma.ui.home.thirdpage.notification

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
import com.component.pharma.databinding.FragmentGroupsBinding
import com.component.pharma.databinding.FragmentNotificationBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.firstpage.search.group.GroupsAdapter
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class NotificationFragment  : BaseFragment<HomeViewModel, FragmentNotificationBinding, HomeRepository>() {


    lateinit var notificationAdapter: NotificationAdapter

    lateinit var bundle: Bundle


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllNotifications()

        lifecycleScope.launch {
            viewModel.onBadgeStart("Old")
        }

        viewModel.AllNotificationsResponse.observe(viewLifecycleOwner, Observer { response ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visible(response is Resource.Loading)

            when(response) {
                is Resource.Success -> {
                    if (response.value.isEmpty()) {
                        binding.noNotifications.visibility = View.VISIBLE
                    }else {
                        binding.rvNotification.visibility = View.VISIBLE
                    }
                    lifecycleScope.launch {
                        binding.rvNotification.apply {
                            notificationAdapter = NotificationAdapter(response.value)
                            adapter = notificationAdapter
                            layoutManager = LinearLayoutManager(context)
                        }
//                        notificationAdapter.setOnItemClickListener {
//                            bundle = Bundle().apply {
//
//                                putString("groupId", it.phProdGroupId.toString())
//
//                            }
//                            findNavController().navigate(
//                                    R.id.action_groupFragment_to_productsFragment,
//                                    bundle
//                            )
//                        }
                    }
                }
                is Resource.Failure -> handleApiError(response){
                    lifecycleScope.launch {
                    userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer { userId ->
                        viewModel.getAllNotifications(userId.toString())
                    })
                }
                }
            }
        })
    }

    private fun getAllNotifications(){
        lifecycleScope.launch {
            userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer { userId ->
                viewModel.getAllNotifications(userId.toString())
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

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentNotificationBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)




}