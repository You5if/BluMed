package com.component.pharma.ui.home.confirming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.component.pharma.R
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentConfirmBinding
import com.component.pharma.databinding.FragmentEndOrderBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel


class EndOrderFragment : BaseFragment<HomeViewModel, FragmentEndOrderBinding, HomeRepository>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as HomeActivity).setLocationPrice(0.00, 0, "Choose a location")
        viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
            for (ttt in it) {
                viewModel.deleteProduct(ttt)
            }
        })

        binding.shoppingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_endOrderFragment_to_firstNavFragment)
        }
    }


    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentEndOrderBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)


}