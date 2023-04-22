package com.component.pharma.ui.onboarding

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.component.pharma.R
import com.component.pharma.data.RetrofitInstance
import com.component.pharma.data.UserPreferences
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.databinding.ActivityNameBinding
import com.component.pharma.databinding.ActivityOnboardingBinding
import com.component.pharma.ui.auth.AuthActivity
import com.component.pharma.ui.auth.AuthViewModel
import com.component.pharma.ui.base.ViewModelFactory
import com.component.pharma.ui.slideLeft
import com.component.pharma.ui.slideRight
import com.component.pharma.ui.startNewActivity
import com.component.pharma.ui.visible
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class OnboardingActivity : AppCompatActivity() {

    private lateinit var db: ProductDatabase
    private lateinit var binding: ActivityOnboardingBinding
    protected val retrofitInstance = RetrofitInstance()
    private lateinit var viewModel: AuthViewModel
    var count: Int = 1


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        val userPreferences = UserPreferences(this)
        db = ProductDatabase.invoke(this)

        val repository = AuthRepository(retrofitInstance.buildApi(AuthApi::class.java), userPreferences)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        var done: Boolean = false
        var done2: Boolean = false
       binding.mainBack.setOnTouchListener(@SuppressLint("ClickableViewAccessibility")
       object : OnSwipeTouchListener(this@OnboardingActivity) {
           override fun onSwipeLeft() {
               super.onSwipeLeft()
               if (count != 3) {
                   count ++
               }
               if (count == 1) {
//                   binding.backBtn1.visible(false)
//                   binding.nextBtn1.visible(true)
//                   binding.nextBtn2.visible(false)
                   binding.helloText.visible(true)
                   binding.step.text = "Step 1"
                   binding.finishBtn.visibility = View.GONE
                   binding.skipBtn.visibility = View.VISIBLE
                   binding.images.setImageResource(R.drawable.illust1)
                   binding.firstIndicator.setImageResource(R.drawable.viewpager_selected)
                   binding.secIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.thirdIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   done = false
//                   binding.finishBtn.visible(false)
               }else if (count == 2) {
//                   binding.nextBtn1.visible(false)
//                   binding.nextBtn2.visible(true)
//                   binding.backBtn2.visible(false)
//                   binding.backBtn1.visible(true)
                   binding.helloText.visible(false)
                   binding.finishBtn.visibility = View.GONE
                   binding.skipBtn.visibility = View.VISIBLE
                   binding.step.text = "Step 2"
                   binding.images.setImageResource(R.drawable.illust222)
                   binding.secIndicator.setImageResource(R.drawable.viewpager_selected)
                   binding.secIndicator.slideRight(320L, 0)
                   binding.firstIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.thirdIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   done = false

//                   binding.finishBtn.visible(false)
               } else if (count == 3) {
//                   binding.nextBtn1.visible(false)
//                   binding.nextBtn2.visible(false)
//                   binding.backBtn2.visible(true)
//                   binding.backBtn1.visible(false)
                   binding.helloText.visible(false)
                   binding.step.text = "Step 3"
                   binding.finishBtn.visibility = View.VISIBLE
                   binding.skipBtn.visibility = View.GONE
                   binding.images.setImageResource(R.drawable.illust33png)
                   binding.firstIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.secIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.thirdIndicator.setImageResource(R.drawable.viewpager_selected)
                   if (!done) {
                       binding.thirdIndicator.slideRight(320L, 0)
                       done = true
                   }
//                   binding.finishBtn.visible(true)
               }
           }
           override fun onSwipeRight() {
               super.onSwipeRight()
               if (count != 1) {
                   count --
               }
               if (count == 1) {
//                   binding.backBtn1.visible(false)
//                   binding.nextBtn1.visible(true)
//                   binding.nextBtn2.visible(false)
                   binding.helloText.visible(true)
                   binding.step.text = "Step 1"
                   binding.images.setImageResource(R.drawable.illust1)
                   binding.firstIndicator.setImageResource(R.drawable.viewpager_selected)
                   if (!done2) {
                       binding.firstIndicator.slideLeft(320L, 0)
                       done2 = true
                   }
                   binding.secIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.thirdIndicator.setImageResource(R.drawable.viewpager_not_selected)
//                   binding.finishBtn.visible(false)
               }else if (count == 2) {
//                   binding.nextBtn1.visible(false)
//                   binding.nextBtn2.visible(true)
//                   binding.backBtn2.visible(false)
//                   binding.backBtn1.visible(true)
                   binding.helloText.visible(false)
                   binding.step.text = "Step 2"
                   binding.images.setImageResource(R.drawable.illust222)
                   binding.firstIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.secIndicator.setImageResource(R.drawable.viewpager_selected)
                       binding.secIndicator.slideLeft(320L, 0)

                   binding.thirdIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   done2 = false
//                   binding.finishBtn.visible(false)
               } else if (count == 3) {
//                   binding.nextBtn1.visible(false)
//                   binding.nextBtn2.visible(false)
//                   binding.backBtn2.visible(true)
//                   binding.backBtn1.visible(false)
                   binding.helloText.visible(false)
                   binding.step.text = "Step 3"
                   binding.images.setImageResource(R.drawable.illust33png)
                   binding.firstIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.secIndicator.setImageResource(R.drawable.viewpager_not_selected)
                   binding.thirdIndicator.setImageResource(R.drawable.viewpager_selected)
                   done2 = false
//                   binding.finishBtn.visible(true)
               }
           }
       })




//        binding.backBtn1.visible(false)
//        binding.backBtn2.visible(false)
//        binding.nextBtn1.visible(true)
//        binding.nextBtn2.visible(false)
//        binding.finishBtn.visible(false)
        binding.helloText.visible(true)
        binding.firstIndicator.setImageResource(R.drawable.viewpager_selected)
        binding.step.text = "Step 1"


//        binding.finishBtn.setOnClickListener {
//            lifecycleScope.launch {
//                viewModel.onboardingFinished(finshed = "finished")
//                startNewActivity(AuthActivity::class.java)
//            }
//        }
        binding.skipBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onboardingFinished(finshed = "finished")
                startNewActivity(AuthActivity::class.java)
            }
        }
        binding.finishBtn.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onboardingFinished(finshed = "finished")
                startNewActivity(AuthActivity::class.java)
            }
        }


//        binding.backBtn1.setOnClickListener {
////            viewPager?.currentItem = 0
//            count = 1
//            binding.backBtn1.visible(false)
//            binding.nextBtn1.visible(true)
//            binding.nextBtn2.visible(false)
//            binding.helloText.visible(true)
//            binding.step.text = "Step 1"
//            binding.images.setImageResource(R.drawable.illust1)
//            binding.finishBtn.visible(false)
//
//
//
//        }
//        binding.backBtn2.setOnClickListener {
////            viewPager?.currentItem = 1
//            count = 2
//            binding.backBtn2.visible(false)
//            binding.backBtn1.visible(true)
//            binding.nextBtn1.visible(false)
//            binding.nextBtn2.visible(true)
//            binding.helloText.visible(false)
//            binding.step.text = "Step 2"
//            binding.images.setImageResource(R.drawable.illust222)
//            binding.finishBtn.visible(false)
//        }
//        binding.nextBtn1.setOnClickListener {
////            viewPager?.currentItem = 1
//            count = 2
//            binding.nextBtn1.visible(false)
//            binding.nextBtn2.visible(true)
//            binding.backBtn2.visible(false)
//            binding.backBtn1.visible(true)
//            binding.helloText.visible(false)
//            binding.step.text = "Step 2"
//            binding.images.setImageResource(R.drawable.illust222)
//            binding.finishBtn.visible(false)
//        }
//        binding.nextBtn2.setOnClickListener {
////            viewPager?.currentItem = 1
//            count = 3
//            binding.nextBtn1.visible(false)
//            binding.nextBtn2.visible(false)
//            binding.backBtn2.visible(true)
//            binding.backBtn1.visible(false)
//            binding.helloText.visible(false)
//            binding.step.text = "Step 3"
//            binding.images.setImageResource(R.drawable.illust33png)
//            binding.finishBtn.visible(true)
//
//        }


    }
}