package com.component.pharma.ui.home.firstpage.search.category

import android.os.Bundle
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
import com.component.pharma.databinding.FragmentCategoryBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class CategoryFragment : BaseFragment<HomeViewModel, FragmentCategoryBinding , HomeRepository>() {

    lateinit var categoryAdapter: CategoryAdapter

    lateinit var bundle: Bundle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCategory()

        viewModel.CategoryResponse.observe(viewLifecycleOwner, Observer { response ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visible(response is Resource.Loading)
            binding.rvCategoties.visibility = View.VISIBLE
            when(response) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        binding.rvCategoties.apply {
                            categoryAdapter = CategoryAdapter(response.value)
                            adapter = categoryAdapter
                            layoutManager = LinearLayoutManager(context)
                        }
                        categoryAdapter.setOnItemClickListener {
                            bundle = Bundle().apply {

                                putString("categoryId", it.phProdCatId.toString())

                            }
                            findNavController().navigate(
                                    R.id.action_categoryFragment2_to_groupFragment,
                                    bundle
                            )
                        }
                    }
                }
                is Resource.Failure -> handleApiError(response){getCategory()}
            }
        })

    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }

    private fun getCategory(){
        viewModel.getCategory()
    }
    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentCategoryBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)




}