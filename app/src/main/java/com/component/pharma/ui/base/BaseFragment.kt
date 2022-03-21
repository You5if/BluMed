package com.component.pharma.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.viewbinding.ViewBinding
import com.component.pharma.MainActivity
import com.component.pharma.data.RetrofitInstance
import com.component.pharma.data.UserPreferences
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.data.db.promtionsdp.PromotionsDatabase
import com.component.pharma.data.network.UserApi
import com.component.pharma.ui.startNewActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

abstract class BaseFragment<VM: BaseViewModel, VB: ViewBinding, BR: BaseRepository> : Fragment() {

    protected lateinit var userPreferences: UserPreferences
    protected lateinit var binding: VB
    protected lateinit var db: ProductDatabase
    protected lateinit var db2: PromotionsDatabase
    protected lateinit var viewModel: VM


    protected val retrofitInstance = RetrofitInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userPreferences = UserPreferences(requireContext())
        db = ProductDatabase.invoke(requireContext())
        db2 = PromotionsDatabase.invoke(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        lifecycleScope.launch { userPreferences.authToken.first() }

        return binding.root
    }

    fun logout() = lifecycleScope.launch {
        val authToken = userPreferences.authToken.first()
        val api = retrofitInstance.buildApi(UserApi::class.java, authToken)
        viewModel.logout(api)
//        userPreferences.clear()
        userPreferences.saveAuthToken("")
        requireActivity().startNewActivity(MainActivity::class.java)
        Toast.makeText(context, "Successfully logged out", Toast.LENGTH_SHORT).show()

    }

    abstract fun getViewModel() : Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    abstract fun getFragmentRepository(): BR

}