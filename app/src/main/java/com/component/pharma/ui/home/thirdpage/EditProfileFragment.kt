package com.component.pharma.ui.home.thirdpage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentEditProfileBinding
import com.component.pharma.databinding.FragmentThirdNavBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.ui.auth.AuthViewModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.home.HomeViewModel
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch


class EditProfileFragment : BaseFragment<AuthViewModel, FragmentEditProfileBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etFirstName.requestFocus()
        binding.cpPbar.visible(false)
        binding.saveName.isEnabled = false
        binding.saveName.setBackgroundResource(R.drawable.roundback)
        binding.saveName.setTextColor(ContextCompat.getColor(binding.saveName.context, R.color.white))

        userPreferences.firstName.asLiveData().observe(viewLifecycleOwner, Observer { firstName ->
            userPreferences.lastName.asLiveData().observe(viewLifecycleOwner, Observer { lastName ->
                binding.etFirstName.setText(firstName.toString())
                binding.etLastName.setText(lastName.toString())
            })

        })

        binding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().trim().isEmpty()) {


                    binding.saveName.isEnabled = false
                    binding.saveName.setBackgroundResource(R.drawable.roundback)
                    binding.saveName.setTextColor(ContextCompat.getColor(binding.saveName.context, R.color.white))


                }else{
                    binding.saveName.isEnabled = true
                    binding.saveName.setBackgroundResource(R.drawable.roundblue)
                    binding.saveName.setTextColor(ContextCompat.getColor(binding.saveName.context, R.color.white))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


            }
        })

        viewModel.userProfileResponse.observe(viewLifecycleOwner, Observer {
            binding.cpPbar.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    lifecycleScope.launch {

                            viewModel.saveFirstName(binding.etFirstName.text.trim().toString())
                            viewModel.saveLastName(binding.etLastName.text.trim().toString())
                            findNavController().navigate(R.id.thirdNavFragment)

                    }

                }
                is Resource.Failure -> {}
            }
        })

        binding.saveName.setOnClickListener {
            lifecycleScope.launch {
                val name = binding.etFirstName.text.trim().toString()
                val name2 = binding.etLastName.text.trim().toString()
                userPreferences.userProfileId.asLiveData().observe(viewLifecycleOwner, Observer { phUserProfileId ->
                    userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer { userId ->
                        userPreferences.pic.asLiveData().observe(viewLifecycleOwner, Observer {pic ->
                            saveUserProfile(user = UserProfileModel(
                                    AuditColumns(1100001, 201, 10001, "Win32", 1, "unidentified", "unidentified", "unidentified", "26"),
                                    phUserProfileId!!,
                                    name,
                                    name2,
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    userId?.toInt()!!,
                                    "",
                                    false,
                                    true
                            ))
                        })
                    })

                })


            }
        }


    }


    private fun saveUserProfile(user: UserProfileModel) {
        viewModel.saveUserProfile(user)
    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentEditProfileBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.buildApi(AuthApi::class.java), userPreferences)


}