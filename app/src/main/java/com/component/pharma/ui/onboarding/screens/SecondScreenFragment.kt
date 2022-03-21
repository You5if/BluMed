package com.component.pharma.ui.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.component.pharma.R
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.databinding.FragmentSecondScreenBinding
import com.component.pharma.ui.auth.AuthActivity
import com.component.pharma.ui.auth.AuthViewModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.startNewActivity
import kotlinx.coroutines.launch


class SecondScreenFragment : BaseFragment<AuthViewModel, FragmentSecondScreenBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)


//        binding.Next.setOnClickListener {
//            viewPager?.currentItem = 2
//        }


//        binding.Next.setOnClickListener {
//            viewPager?.currentItem = 2
//        }
//
//        binding.Pre.setOnClickListener {
//            viewPager?.currentItem = 0
//        }
//        binding.Skip.setOnClickListener {
//            lifecycleScope.launch {
//                viewModel.onboardingFinished(finshed = "finished")
//                requireActivity().startNewActivity(AuthActivity::class.java)
//            }
//
//        }
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSecondScreenBinding.inflate(
        inflater,
        container,
         false
    )

    override fun getFragmentRepository() = AuthRepository(
        retrofitInstance.buildApi(AuthApi::class.java), userPreferences
    )


}