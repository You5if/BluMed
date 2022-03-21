package com.component.pharma.ui.name

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.RetrofitInstance
import com.component.pharma.data.UserPreferences
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.databinding.ActivityNameBinding
import com.component.pharma.model.AuditColumns
import com.component.pharma.model.responses.UserProfileModel
import com.component.pharma.ui.auth.AuthViewModel
import com.component.pharma.ui.base.ViewModelFactory
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.startNewActivity
import com.component.pharma.ui.visible
import kotlinx.coroutines.launch

class NameActivity : AppCompatActivity() {

    private lateinit var db: ProductDatabase
    private lateinit var binding: ActivityNameBinding
    protected val retrofitInstance = RetrofitInstance()
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val userPreferences = UserPreferences(this)
        db = ProductDatabase.invoke(this)

        val repository = AuthRepository(retrofitInstance.buildApi(AuthApi::class.java), userPreferences)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        super.onCreate(savedInstanceState)
        binding = ActivityNameBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.cpPbar.visible(false)
        binding.etFirstName.requestFocus()
        binding.saveName.isEnabled = false
        binding.saveName.setBackgroundResource(R.drawable.roundback)
        binding.saveName.setTextColor(ContextCompat.getColor(binding.saveName.context, R.color.white))

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

        viewModel.userProfileResponse.observe(this, Observer {
            binding.cpPbar.visible(it is Resource.Loading)
            when(it) {
                is Resource.Success -> {
                    lifecycleScope.launch {

                            viewModel.saveFirstName(binding.etFirstName.text.trim().toString())
                            viewModel.saveLastName(binding.etLastName.text.trim().toString())


                    }
                    startNewActivity(HomeActivity::class.java)

                }
                is Resource.Failure -> {}
            }
        })

        binding.saveName.setOnClickListener {
            lifecycleScope.launch {
                val name = binding.etFirstName.text.trim().toString()
                val name2 = binding.etLastName.text.trim().toString()
                userPreferences.userProfileId.asLiveData().observe(this@NameActivity, Observer { phUserProfileId ->
                    userPreferences.userId.asLiveData().observe(this@NameActivity, Observer { userId ->
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
            }
        }
    }

    private fun saveUserProfile(user: UserProfileModel) {
        viewModel.saveUserProfile(user)
    }
}