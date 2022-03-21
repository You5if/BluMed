package com.component.pharma.ui.home.firstpage.promotions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentPromotionsBinding
import com.component.pharma.model.responses.GetPromItems
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeViewModel
import kotlinx.coroutines.launch


class PromotionsFragment : BaseFragment<HomeViewModel, FragmentPromotionsBinding, HomeRepository>() {


    val promsList = mutableListOf(
        GetPromItems(1, "Title 1", "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/b912b970366287.5ba13839c689b.jpg"),
        GetPromItems(2, "Title 2", "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/ea112670366287.5ba13839c51aa.jpg"),
        GetPromItems(3, "Title 3", "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/627e3470366287.5ba13839c6166.jpg"),
        GetPromItems(4, "Title 4", "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/9607c370366287.5ba13839c5a9e.jpg"),
        GetPromItems(5, "Title 5", "https://mir-s3-cdn-cf.behance.net/project_modules/max_1200/1919be71266293.5bbf4faf1665a.jpg"),
    )

    lateinit var promotionsAdapter: PromotionsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getProfile()

        for (prom in promsList) {
            viewModel.saveProm(prom)
        }
        val recyclerView = binding.rvPromotionImg as RecyclerView

        var layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper: SnapHelper = PagerSnapHelper()
        recyclerView.layoutManager = layoutManager
        snapHelper.attachToRecyclerView(recyclerView)

        viewModel.getSavedProm().observe(viewLifecycleOwner, Observer {
            binding.rvPromotionImg.apply {
                promotionsAdapter = PromotionsAdapter(it)
                adapter = promotionsAdapter
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            }
        })


        viewModel.ProfileResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveProfileType(response.value.profileType)
                        if (response.value.profileType == "word") {
                            viewModel.saveInitial(response.value.profileSource)
                        } else if (response.value.profileType == "pic") {
                            viewModel.savePic(response.value.profileSource)
                        }
                    }
                }
                is Resource.Failure -> handleApiError(response){getProfile()}
            }
        })




    }


    fun getProfile() {
        userPreferences.userId.asLiveData().observe(requireActivity(), Observer {
            val id = it.toString()
            viewModel.getProfile(id)
        })
    }


    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentPromotionsBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)


}