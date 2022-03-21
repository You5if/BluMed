package com.component.pharma.ui.home.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentHomeBinding
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.startNewActivity
import com.component.pharma.ui.upload.UploadActivity
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding, HomeRepository>() {
    lateinit var mobUs: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val endStatus = 255
//        userPreferences.userStatus.asLiveData().observe(viewLifecycleOwner, Observer {
//            mobUs = it.toString()
//            if (mobUs == "23002" ) {
//                binding.fsOne.cpBgView.visible(true)
//                binding.fsTwo.cpBgView.visible(false)
//                binding.fsThree.cpBgView.visible(false)
//                binding.fsOne.Next1.setOnClickListener {
//                    binding.fsOne.cpBgView.visible(false)
//                    binding.fsTwo.cpBgView.visible(true)
//                    binding.fsThree.cpBgView.visible(false)
//                    binding.fsTwo.Next2.setOnClickListener {
//                        binding.fsOne.cpBgView.visible(false)
//                        binding.fsTwo.cpBgView.visible(false)
//                        binding.fsThree.cpBgView.visible(true)
//                        binding.fsThree.Finish3.setOnClickListener {
//                            getUserSI()
//                            lifecycleScope.launch {
//                                viewModel.saveUserStatus(endStatus)
//                            }
//                            binding.fsOne.cpBgView.visible(false)
//                            binding.fsTwo.cpBgView.visible(false)
//                            binding.fsThree.cpBgView.visible(false)
//                        }
//                        binding.fsThree.Skip3.setOnClickListener {
//                            getUserSI()
//                            lifecycleScope.launch {
//                                viewModel.saveUserStatus(endStatus)
//                            }
//                            binding.fsOne.cpBgView.visible(false)
//                            binding.fsTwo.cpBgView.visible(false)
//                            binding.fsThree.cpBgView.visible(false)
//                        }
//                        binding.fsThree.Pre3.setOnClickListener {
//                            binding.fsOne.cpBgView.visible(false)
//                            binding.fsTwo.cpBgView.visible(true)
//                            binding.fsThree.cpBgView.visible(false)
//                        }
//                    }
//                    binding.fsTwo.Skip2.setOnClickListener {
//                        getUserSI()
//                        lifecycleScope.launch {
//                            viewModel.saveUserStatus(endStatus)
//                        }
//                        binding.fsOne.cpBgView.visible(false)
//                        binding.fsTwo.cpBgView.visible(false)
//                        binding.fsThree.cpBgView.visible(false)
//                    }
//                    binding.fsTwo.Pre2.setOnClickListener {
//                        binding.fsOne.cpBgView.visible(true)
//                        binding.fsTwo.cpBgView.visible(false)
//                        binding.fsThree.cpBgView.visible(false)
//                    }
//                }
//                binding.fsOne.Skip1.setOnClickListener {
//                    getUserSI()
//                    lifecycleScope.launch {
//                        viewModel.saveUserStatus(endStatus)
//                    }
//                    binding.fsOne.cpBgView.visible(false)
//                    binding.fsTwo.cpBgView.visible(false)
//                    binding.fsThree.cpBgView.visible(false)
//                }
//            }else {
//                binding.fsOne.cpBgView.visible(false)
//                binding.fsTwo.cpBgView.visible(false)
//                binding.fsThree.cpBgView.visible(false)
//            }
//
//
//        })
//


        binding.LogoutBtn.setOnClickListener {
            logout()
        }

        binding.btnUploadPage.setOnClickListener {
            requireActivity().startNewActivity(UploadActivity::class.java)
        }

    }
    private fun getUserSI() {
        userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer {
            val id = it.toString()
            viewModel.getUserSI(id)
        })


    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() =  HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)


}