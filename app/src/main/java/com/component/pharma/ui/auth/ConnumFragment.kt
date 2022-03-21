package com.component.pharma.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.databinding.FragmentAuthBinding
import com.component.pharma.databinding.FragmentConnumBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.PharmUserModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.visible
import java.util.*


class ConnumFragment : BaseFragment<AuthViewModel, FragmentConnumBinding, AuthRepository>() {


    var mobNum :String? = ""
    lateinit var bundle: Bundle
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mobNum = arguments?.getString("mobileN")
        bundle = Bundle().apply {
            putString("mobileN2", mobNum)
        }
        binding.number.text = mobNum
//        binding.cpPbar.visible(false)
        binding.editBtn.setOnClickListener {
            findNavController().navigate(R.id.authFragment)
        }
//        viewModel.loginResponse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
//
//            when(it) {
//                is Resource.Success -> {
//                }
//                is Resource.Failure -> handleApiError(it) { login() }
//
//            }
//        })
        binding.confirmNum.setOnClickListener {

            login()
            findNavController().navigate(R.id.OTPFragment,
                    bundle
            )



        }
    }

    private fun login(){
        val pharmUserId = 0
        val registrationStatus = 23001
        val active = 1
        val auditColumns = AuditColumns(1100001, 201, 10001, "Win32", 1, "unidentified", "unidentified", "unidentified", "26")
        val mobile = mobNum
        val currentDate = Calendar.getInstance().time
        val entryMode = "A"
        val readOnly = false



        viewModel.login(
            PharmUserModel(active,
            auditColumns,
            entryMode,
            mobile!!,
            pharmUserId,
            readOnly,
            currentDate,
            registrationStatus
        )
        )

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentConnumBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.buildApi(AuthApi::class.java), userPreferences)



}