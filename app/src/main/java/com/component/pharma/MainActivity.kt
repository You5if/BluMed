package com.component.pharma

import android.app.IntentService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.component.pharma.data.UserPreferences
import com.component.pharma.ui.auth.AuthActivity
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.name.NameActivity
import com.component.pharma.ui.onboarding.OnboardingActivity
import com.component.pharma.ui.service.MyService
import com.component.pharma.ui.startNewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val userPreferences = UserPreferences(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val activity = AuthActivity::class.java

        userPreferences.onboardingFinished.asLiveData().observe(this, Observer {
            if (it == "finished") {
                userPreferences.authToken.asLiveData().observe(this, Observer { token ->
                    if (token == null || token == "") {
                        val activity = AuthActivity::class.java
                        startNewActivity(activity)
                    } else {
                        userPreferences.firstName.asLiveData().observe(this, Observer { firstName ->
                            if (firstName.isNullOrEmpty()) {
                                val activity2 = NameActivity::class.java
                                startNewActivity(activity2)
                            }else {
                                val activity3 = HomeActivity::class.java
                                startNewActivity(activity3)
                            }
                        })

                    }

                })

            } else {
                val activity = OnboardingActivity::class.java
                startNewActivity(activity)
            }
        })

    }
}

