package com.component.pharma.ui.home.confirming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.FragmentConfirmBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.CompleteObj
import com.component.pharma.model.PhInvProdEntry
import com.component.pharma.model.PhInvoiceEntry
import com.component.pharma.model.responses.GetCitiesItem
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ConfirmFragment : BaseFragment<HomeViewModel, FragmentConfirmBinding, HomeRepository>() {

//    private var STot :String? = ""
    private var Total: Double? = 0.00
    private var GTotal: Double? = 0.00
    private var record:Double? = 0.00

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        record = arguments?.getString("cityPrice")?.toDouble().toString()
        record = (activity as HomeActivity).locationPrice
//        STot = (activity as HomeActivity).subTotal.toString()
//        STot = String.format("%.2f", STot).toDouble().toString()

        binding.locationName.text = (activity as HomeActivity).locationName
        binding.locationPrice.text = (activity as HomeActivity).locationPrice.toString()
            binding.tvSubTotal.text = (activity as HomeActivity).subTotal.toString()

            binding.tvDelivery.text = record.toString()

//        if (STot.isNullOrEmpty()) {
//            Total = 0.00
//        }
        Total = (activity as HomeActivity).subTotal?.plus(record!!)
        GTotal = Total
        binding.tvTotal.text = Total.toString()
//      1  binding.tvGrandTotal.text = GTotal.toString()
//
//        binding.btnApply.setOnClickListener {
//            var pCode = binding.etPromo.text.toString()
//            getPromoData(pCode)
//        }

        binding.locationBtn.setOnClickListener {
            findNavController().navigate(R.id.action_confirmFragment_to_cityFragment)
        }
        binding.btnOrder.setOnClickListener {
            viewModel.getSavedProduct().observe(viewLifecycleOwner, Observer {
                val currentDate = Calendar.getInstance().time
                var locId = (activity as HomeActivity).locationId
                val auditColumns = AuditColumns(1100001, 201, 10001, "Win32", 1, "unidentified", "unidentified", "unidentified", "26")
                userPreferences.mobileNum.asLiveData().observe(viewLifecycleOwner, Observer {mob ->
                    userPreferences.userId.asLiveData().observe(viewLifecycleOwner, Observer {userId ->
                    var phInvoiceEntry = PhInvoiceEntry(
                        0,
                        "",
                        currentDate,
                        "",
                            userId?.toInt()!!,
                        1,
                        1,
                        1,
                        locId!!,
                        "",
                        0.0,
                        0.0,
                        "",
                        1,
                        "",
                        1,
                        1,
                        mob.toString(),
                        "",
                        "",
                        "",
                        true,
                        auditColumns
                    )

                    var phInvProdEntry = ArrayList<PhInvProdEntry>()
                    for (ttt in it) {
                        phInvProdEntry.add(
                            PhInvProdEntry(0,
                                0,
                                ttt.phProductId,
                                ttt.quantity.toDouble(),
                                1,
                                ttt.price,
                                ttt.quantity.toDouble() * ttt.price,
                                1,
                                1,
                                true,
                                auditColumns)
                        )
                    }

                    var completeObj = CompleteObj(
                        phInvoiceEntry,
                        phInvProdEntry
                    )

                    sendOrder(completeObj)
                })



            })})
        }

        viewModel.orderResponse.observe(viewLifecycleOwner, Observer {res ->
            when (res) {
                is Resource.Success -> {
                    findNavController().navigate(R.id.endOrderFragment)
                }
                is Resource.Failure -> {
                    Toast.makeText(context, "there is an error, Order later", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.promoResponse.observe(viewLifecycleOwner, Observer { response ->


                when(response) {
                    is Resource.Success -> {
//                   1     lifecycleScope.launch {
//                            if (response.value.discountType == 27001) {
//                                var dAmount = (activity as HomeActivity).subTotal?.times((response.value.discountAmount / 100))
//
//                                dAmount = String.format("%.2f", dAmount).toDouble()
//                                binding.tvDiscount.text = dAmount.toString()
//                                GTotal = ((activity as HomeActivity).subTotal?.minus(dAmount!!))?.plus(
//                                        record?.toDouble()!!
//                                )
//                                GTotal = String.format("%.2f", GTotal).toDouble()
//                                binding.tvGrandTotal.text = GTotal.toString()
//
//                            }else {
//                                binding.tvDiscount.text = response.value.discountAmount.toString()
//                                GTotal = ((activity as HomeActivity).subTotal?.minus(response.value.discountAmount))?.plus(
//                                        record!!
//                                )
//                                GTotal = String.format("%.2f", GTotal).toDouble()
//                                binding.tvGrandTotal.text = GTotal.toString()
//                            }
//                        }
                    }
                    is Resource.Failure -> {
//                 1       binding.tvDiscount.text = "0.00"
//                        GTotal = Total
//                        binding.tvGrandTotal.text = GTotal.toString()
                    }
                }
            })

    }


    private fun getPromoData(pCode :String){
        viewModel.getPromoData(pCode)
    }

    private fun sendOrder(order:CompleteObj) {
        viewModel.sendOrder(order)
    }

    override fun getViewModel() = HomeViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentConfirmBinding.inflate(
        inflater,
        container,
        false
    )

    override fun getFragmentRepository() = HomeRepository(retrofitInstance.buildApi(HomeApi::class.java), userPreferences, db, db2)



}