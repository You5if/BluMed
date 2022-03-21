package com.component.pharma.ui.home.firstpage.search.group

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
import com.component.pharma.databinding.FragmentCategoryBinding
import com.component.pharma.databinding.FragmentGroupsBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.home.firstpage.search.category.CategoryAdapter
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class GroupsFragment : BaseFragment<HomeViewModel, FragmentGroupsBinding, HomeRepository>() {

    lateinit var groupsAdapter: GroupsAdapter

    lateinit var bundle: Bundle

    lateinit var categoryId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = arguments?.getString("categoryId")!!
        getGroups(categoryId)

        viewModel.GroupsResponse.observe(viewLifecycleOwner, Observer { response ->
            binding.shimmerFrameLayout.stopShimmerAnimation()
            binding.shimmerFrameLayout.visible(response is Resource.Loading)
            binding.rvGroups.visibility = View.VISIBLE
            when(response) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        binding.rvGroups.apply {
                            groupsAdapter = GroupsAdapter(response.value)
                            adapter = groupsAdapter
                            layoutManager = LinearLayoutManager(context)
                        }
                        groupsAdapter.setOnItemClickListener {
                            bundle = Bundle().apply {

                                putString("groupId", it.phProdGroupId.toString())

                            }
                            findNavController().navigate(
                                    R.id.action_groupFragment_to_productsFragment,
                                bundle
                            )
                        }
                    }
                }
                is Resource.Failure -> handleApiError(response){getGroups(categoryId)}
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


    private fun getGroups(id: String){
        viewModel.getGroups(id)
    }
    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentGroupsBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)




}