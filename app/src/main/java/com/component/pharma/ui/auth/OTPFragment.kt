package com.component.pharma.ui.auth

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Process
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.databinding.FragmentOTPBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.OtpArray
import com.component.pharma.model.OtpSend
import com.component.pharma.model.PharmUserModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.handleApiError
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.name.NameActivity
import com.component.pharma.ui.startNewActivity
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch
import okhttp3.internal.platform.android.AndroidLogHandler.close
import java.util.*


class OTPFragment : BaseFragment<AuthViewModel, FragmentOTPBinding, AuthRepository>() {


    lateinit var mob: String
    var mobNum :String? = ""
    var otpAll: String? = ""
    var arr : OtpArray = OtpArray("", "", "", "")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)







        binding.oneBox.requestFocus()
        binding.cpPbar.visible(false)
        binding.sendOtp.isEnabled = false
        binding.sendOtp.setBackgroundResource(R.drawable.roundback)
        binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))
        binding.invalidPhoneNum.visible(false)
        binding.editBtn.visible(false)
        binding.reSendBtn.visible(false)




        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
//        observeKeyEventChanges()
        var countTime: Int = 60
        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countTime -= 1
                binding.countTime.text = countTime.toString()
            }

            override fun onFinish() {
                binding.countTime.visibility = View.GONE
                binding.seconds.visibility = View.GONE
                binding.reSendBtn.visibility = View.VISIBLE
                binding.editBtn.visibility = View.VISIBLE
            }
        }
        timer.start()

        binding.reSendBtn.setOnClickListener {
            timer.cancel()
            binding.invalidPhoneNum.visible(false)
            login()
            binding.invalidPhoneNum.visible(false)
            binding.editBtn.visible(false)
            binding.reSendBtn.visible(false)
            binding.countTime.visible(true)
            binding.seconds.visible(true)
            countTime = 60
            timer.start()
        }



//        binding.oneBox.doAfterTextChanged {
//            if(it?.length == 1) {
//                arr.two = it.toString()
//                binding.twoBox.requestFocus()
//            }
//        }
//        binding.twoBox.doAfterTextChanged {
//            if(it?.length == 1) {
//                binding.threeBox.requestFocus()
//            }else if(it?.length == 0) {
//                binding.oneBox.requestFocus()
//            }
//        }
//        binding.threeBox.doAfterTextChanged {
//            if(it?.length == 1) {
//                binding.fourBox.requestFocus()
//            }else if(it?.length == 0) {
//                binding.twoBox.requestFocus()
//            }
//        }
//        binding.fourBox.doAfterTextChanged {
//            if(it?.length == 1) {
//                otpAll = binding.oneBox.text.toString() + binding.twoBox.text.toString() +binding.threeBox.text.toString() +binding.fourBox.text.toString()
//                Toast.makeText(context, otpAll, Toast.LENGTH_SHORT).show()
//            }else if(it?.length == 0) {
//                binding.threeBox.requestFocus()
//            }
//        }

        binding.oneBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().trim().isEmpty()) {
                    binding.oneBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            binding.oneBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))

                        }
                        false
                    })
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.oneBox.setOnKeyListener(View.OnKeyListener { v, keyCode1, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode1 == KeyEvent.KEYCODE_DEL) {
                            binding.oneBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))


                        }else {
                            binding.twoBox.requestFocus()
                        }
                        false
                    })
                }
            }
        })
        binding.twoBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().trim().isEmpty()) {
                    binding.twoBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            binding.oneBox.requestFocus()
                            binding.oneBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))

                        }
                        false
                    })
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.twoBox.setOnKeyListener(View.OnKeyListener { v, keyCode1, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode1 == KeyEvent.KEYCODE_DEL) {
                            binding.twoBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))


                        }else {
                            binding.threeBox.requestFocus()
                        }
                        false
                    })
                }
            }
        })
        binding.threeBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().trim().isEmpty()) {
                    binding.threeBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            binding.twoBox.requestFocus()
                            binding.twoBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))

                        }
                        false
                    })
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.threeBox.setOnKeyListener(View.OnKeyListener { v, keyCode1, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode1 == KeyEvent.KEYCODE_DEL) {
                            binding.threeBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))


                        }else {
                            binding.fourBox.requestFocus()
                        }
                        false
                    })
                }
            }
        })
        binding.fourBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if (s.toString().trim().isEmpty()) {
                    binding.fourBox.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            binding.threeBox.requestFocus()
                            binding.threeBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.invalidPhoneNum.visible(false)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))

                        }
                        false
                    })
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().trim().isEmpty()) {
                    binding.sendOtp.isEnabled = true
                    binding.sendOtp.setBackgroundResource(R.drawable.roundblue)
                    binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))
                    binding.fourBox.setOnKeyListener(View.OnKeyListener { v, keyCode1, event -> // You can identify which key pressed buy checking keyCode value
                        // with KeyEvent.KEYCODE_
                        if (keyCode1 == KeyEvent.KEYCODE_DEL) {
                            binding.fourBox.text = null
                            binding.sendOtp.isEnabled = false
                            binding.sendOtp.setBackgroundResource(R.drawable.roundback)
                            binding.sendOtp.setTextColor(ContextCompat.getColor(binding.sendOtp.context, R.color.white))


                        }
                        false
                    })
                }
            }
        })




        viewModel.otpResponse.observe(viewLifecycleOwner, Observer {
            binding.cpPbar.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAuthToken(it.value.token)
                        viewModel.saveUserStatus(it.value.userStatus)
                        viewModel.saveUserId(it.value.userId.toString())
                        viewModel.saveFirstName(it.value.firstName)
                        viewModel.saveUserProfileId(it.value.phUserProfileId)
                        timer.cancel()
                        if(it.value.firstName == "") {
                            requireActivity().startNewActivity(NameActivity::class.java)
                        }else {
                            requireActivity().startNewActivity(HomeActivity::class.java)
                        }

//                        userPreferences.userStatus.asLiveData().observe(viewLifecycleOwner, Observer {
//                            mob = it.toString()
//
//            Toast.makeText(requireContext(), mob, Toast.LENGTH_SHORT).show()
//                        })

                    }

                }
                is Resource.Failure -> {
                    handleApiError(it){getOtp(otpAll!!)}
                    binding.invalidPhoneNum.visible(true)
                    binding.editBtn.visible(true)
                    binding.reSendBtn.visible(true)
                    binding.countTime.visible(false)
                    binding.seconds.visible(false)
                }
            }
        })

        binding.editBtn.setOnClickListener {
            findNavController().navigate(R.id.authFragment)
        }

        binding.sendOtp.setOnClickListener {
            binding.editBtn.visible(false)
            binding.reSendBtn.visible(false)
            otpAll = binding.oneBox.text.toString() + binding.twoBox.text.toString() +binding.threeBox.text.toString() +binding.fourBox.text.toString()

            getOtp(otpAll!!)
        }
    }

//    private fun observeKeyEventChanges() {
//        viewModel.keyEvent.observe(viewLifecycleOwner) {
//            onBackPressed()
//
//        }
//    }





//    fun onBackPressed() {
//        //super.onBackPressed();
//        IsFinish("Want to close app?")
//    }
//
//    fun IsFinish(alertmessage: String?) {
//        val dialogClickListener =
//            DialogInterface.OnClickListener { dialog, which ->
//                when (which) {
//                    DialogInterface.BUTTON_POSITIVE -> findNavController().navigateUp()
//                    DialogInterface.BUTTON_NEGATIVE -> {
//                        close()
//                    }
//                }
//            }
//        val builder = AlertDialog.Builder(
//            context!!
//        )
//        builder.setMessage(alertmessage)
//            .setPositiveButton("Yes", dialogClickListener)
//            .setNegativeButton("No", dialogClickListener).show()
//    }
    private fun login(){
        mobNum = arguments?.getString("mobileN2")
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


//    fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exitByBackKey()
//
//            //moveTaskToBack(false);
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    private fun exitByBackKey() {
        val options2 = arrayOf("Edit number", "cancel")
        val askChoices = android.app.AlertDialog.Builder(requireContext())
            .setItems(options2){ _, pos ->
                when (pos) {
                    0 -> {
                        close()
                    }
                    1 -> {
                        close()
                    }

                }
            }
    }

    private fun getOtp(otp: String){
//        userPreferences.mobileNum.asLiveData().observe(viewLifecycleOwner, Observer {
//            mob = it.toString()
            mobNum = arguments?.getString("mobileN2")

        val intT: Int = otp.toInt()
            val sub = OtpSend(0, mobNum.toString(), intT)
            viewModel.getOtp(sub)
//            Toast.makeText(requireContext(), mob, Toast.LENGTH_SHORT).show()
//        })

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentOTPBinding.inflate(
            inflater,
            container,
            false
    )

    override fun getFragmentRepository() = AuthRepository(retrofitInstance.buildApi(AuthApi::class.java), userPreferences)


}