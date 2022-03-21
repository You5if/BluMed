package com.component.pharma.ui.home.city

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
import com.component.pharma.databinding.FragmentCityBinding
import com.component.pharma.databinding.FragmentFourthNavBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel
import kotlinx.coroutines.launch


class CityFragment : BaseFragment<HomeViewModel, FragmentCityBinding, HomeRepository>() {

    lateinit var cityAdapter: CityAdapter

//    lateinit var bundle: Bundle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getApiCity()

        viewModel.CityResponse.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        binding.rvCities.apply {
                            cityAdapter = CityAdapter(response.value)
                            adapter = cityAdapter
                            layoutManager = LinearLayoutManager(context)
                        }
                        cityAdapter.setOnItemClickListener {
//                            bundle = Bundle().apply {
//                                putString("cityPrice", it.price.toString())
//                                putString("cityId", it.phLocId.toString())
////                                putString("totalP2", arguments?.getString("totalP"))
//                            }
                            (activity as HomeActivity).setLocationPrice(it.price, it.phLocId, it.cityName)
                            findNavController().navigate(
                                R.id.action_cityFragment_to_confirmFragment
                            )
                        }
                    }
                }
                is Resource.Failure -> handleApiError(response){getApiCity()}
            }
        })

    }




    private fun getApiCity(){
        viewModel.getApiCity()
    }
    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentCityBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)



}