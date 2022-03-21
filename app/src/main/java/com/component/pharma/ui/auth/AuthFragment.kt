package com.component.pharma.ui.auth


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.component.pharma.R
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.databinding.FragmentAuthBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.PharmUserModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch
import java.util.*


class AuthFragment : BaseFragment<AuthViewModel, FragmentAuthBinding, AuthRepository>() {

    private lateinit var modal: PharmUserModel
    lateinit var bundle: Bundle





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onActivityCreated(savedInstanceState)

//        binding.imageView2.apply {
//            Glide.with(binding.root).load("https://koenig-media.raywenderlich.com/uploads/2019/08/ResponsiveLayout-feature.png").into(binding.imageView2)
//        }


//        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
//            when(it) {
//                is Resource.Success -> {
//                    lifecycleScope.launch {
//                        viewModel.saveMobile(mobile)
//                    }
//                    Toast.makeText(requireContext(), "login success", Toast.LENGTH_SHORT).show()
//                }
//                is Resource.Failure -> handleApiError(it){login()}
//            }
//        })

        binding.invalidPhoneNum.visible(false)




//        binding.number.requestFocus()


        binding.materialButton2.setOnClickListener {
            if(binding.number.length() != 9) {
                binding.invalidPhoneNum.visible(true)
            }else {
                binding.invalidPhoneNum.visible(false)
                val mobileT = binding.number.text.toString()
//
                lifecycleScope.launch {
                    viewModel.saveMobile(mobileT)
                }

                bundle = Bundle().apply {
                    putString("mobileN", mobileT)
                }



                findNavController().navigate(
                        R.id.connumFragment,
                        bundle
                )

            }
            }

    }


    fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    fun View.hideKeyboard() {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.buildApi(AuthApi::class.java), userPreferences)

}