package com.component.pharma.ui.home

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.RetrofitInstance
import com.component.pharma.data.UserPreferences
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.data.db.promtionsdp.PromotionsDatabase
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.ActivityHomeBinding
import com.component.pharma.ui.base.ViewModelFactory
import com.component.pharma.ui.visible
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.util.*
import java.util.function.DoubleUnaryOperator


class HomeActivity : AppCompatActivity() {

    private lateinit var bindingM: ActivityHomeBinding
    private lateinit var db: ProductDatabase
    protected lateinit var db2: PromotionsDatabase
    lateinit var mobUs: String
    protected val retrofitInstance = RetrofitInstance()
    private lateinit var userPreferences: UserPreferences
    private lateinit var viewModel: HomeViewModel

    private lateinit var alarmManager: AlarmManager

    private lateinit var pendingIntent: PendingIntent

    var subTotal: Double? = 0.00

    var locationPrice: Double? = 0.00

    var locationId: Int? = 0

    var locationName: String? = "Choose a location"

    var lastNotificationId: String = ""

    var lastNotificationIdForBadge: String = ""

    val CHANNEL_ID = "channelID"
    val CHANNEL_NAME = "channelName"
    val NOTIFICATION_ID = 0

    var badgeChecker:Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {


        val endStatus = 255

        db = ProductDatabase.invoke(this)
        db2 = PromotionsDatabase.invoke(this)
        userPreferences = UserPreferences(this)
        val repository = HomeRepository(
                retrofitInstance.buildApi(HomeApi::class.java),
                userPreferences,
                db,
                db2
        )
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

//        userPreferences.lastNotificationId.asLiveData().observeForever { lastNID ->
//            lastNotificationId = lastNID!!
//        }


        super.onCreate(savedInstanceState)

        bindingM = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bindingM.root)

        userPreferences.lastNotificationId.asLiveData().observeForever { lastNID ->
            lastNotificationId = lastNID.toString()
        }

        userPreferences.badgeStart.asLiveData().observe(this, Observer{ badger ->
            if (badger == "New") {
                val badge: BadgeDrawable = bindingM.navView.getOrCreateBadge(
                        R.id.thirdNavFragment)
                badge.isVisible = true
            }else if (badger == "Old") {
                bindingM.navView.removeBadge(R.id.thirdNavFragment)
            }else {
                bindingM.navView.removeBadge(R.id.thirdNavFragment)
            }
        })


        userPreferences.notifyBadge.asLiveData().observeForever {  cartBadge ->
            viewModel.getSavedProduct().observe(this, Observer {
                if (!it.isNullOrEmpty()) {
                    bindingM.navView.menu.getItem(3).setIcon(R.drawable.ic_cart)

                } else {
                    bindingM.navView.menu.getItem(3).setIcon(R.drawable.ic_add_cart)

                }
            })
        }

        val intent = Intent(applicationContext, HomeActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }


        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                .setContentTitle("BlueMed")
                .setContentText("New notification")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

        val notificaManager = NotificationManagerCompat.from(applicationContext)

        createNotificationChannel()

        notificaManager.cancelAll()

        val mainHandler = Handler(Looper.getMainLooper())
                val post = mainHandler.post(object : Runnable {

                    override fun run() {
                        userPreferences.userId.asLiveData().observeForever {
                            val id = it.toString()
                            viewModel.getAllNewNotify(id)
                            viewModel.AllNewNotify.observeForever { response ->
                                when (response) {
                                    is Resource.Success -> {
                                        if (lastNotificationId != response.value.notificationId.toString()) {
                                            lifecycleScope.launch {
                                                viewModel.saveLastNotificationId(response.value.notificationId.toString())
                                                notificaManager.notify(NOTIFICATION_ID, notification)
                                                viewModel.onBadgeStart("New")

//                                                findNavController(R.id.nav_home_host_fragment).navigate(R.id.thirdNavFragment)
                                            }
                                        }
                                    }
                                    is Resource.Failure -> {

                                    }
                                }
                            }
                        }
                        // why can't you call api here?
                        mainHandler.postDelayed(this, 7000)
                    }

//                    suspend fun getYousif() {
//                        val api: HomeApi
//                        var hello: String = "hello"
//                        val _AllNewNotify: MutableLiveData<Resource<NewNotify>> = MutableLiveData()
//                        val result: NewNotify
//                        val id = it.toString()
////                        viewModel.getAllNewNotify(id)
//
//                        _AllNewNotify.value = repository.getNewNotify(id)
//
//
//                        _AllNewNotify.observeForever { response ->
//                            when (response) {
//                                is Resource.Success -> {
//
//                                    hello = response.value.notification
//                                    if (response.value.notificationId.toString() != lastNID) {
//                                        lifecycleScope.launch {
//                                            viewModel.saveLastNotificationId(response.value.notificationId.toString())
//                                        }
//                                    }
//                                }
//                                is Resource.Failure -> {
//                                }
//
//                            }
//                        }
//
//                        var yo = _AllNewNotify.value.toString()
//
//
//                    }
                })

//
//        viewModel.AllNewNotify.observe(this@HomeActivity, Observer { response ->
//            when (response) {
//                is Resource.Success -> {
//                    notificaManager.notify(NOTIFICATION_ID, notification)
////                                    if (response.value.notificationId.toString() != lastNID) {
////                                        lifecycleScope.launch {
////                                            viewModel.saveLastNotificationId(response.value.notificationId.toString())
////                                        }
////                                    }
//                }
//                is Resource.Failure -> {
//                }
//            }
//        })


//        val mainHandler = Handler(Looper.getMainLooper())
//
//        val post = mainHandler.post(object : Runnable {
//            override fun run() {
//                Toast.makeText(this@HomeActivity, "timer", Toast.LENGTH_SHORT).show()
//                mainHandler.postDelayed(this, 5000)
//            }
//        })

//        val myIntent = Intent(this, HomeActivity::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0)
//
//        val calendar: Calendar = Calendar.getInstance()
//        calendar.setTimeInMillis(System.currentTimeMillis())
//        calendar.add(Calendar.SECOND, 60) // first time
//
//        val frequency = (60 * 1000).toLong() // in ms
//
//        applicationContext.getSystemService(ALARM_SERVICE)
//            .setInexactRepeating(
//                AlarmManager.RTC_WAKEUP,
//                calendar.getTimeInMillis(),
//                frequency,
//                pendingIntent
//            )



        findNavController(R.id.nav_home_host_fragment).addOnDestinationChangedListener { _, destination, _ ->

            if(destination.id == R.id.fourthNavFragment) {
                // your intro fragment will hide your bottomNavigationView
                lifecycleScope.launch {
                    viewModel.onNotifyCartBadge("Old")
                    val rBadge: BadgeDrawable = bindingM.navView.getOrCreateBadge(
                            R.id.fourthNavFragment)
                    if (rBadge != null) {
                        rBadge.isVisible = false
                    }

                }
            }else if(destination.id == R.id.thirdNavFragment) {
                // your intro fragment will hide your bottomNavigationView
                lifecycleScope.launch {
//                    viewModel.onBadgeStart("Old")
                    val rBadge: BadgeDrawable = bindingM.navView.getOrCreateBadge(
                            R.id.thirdNavFragment)
                    if (rBadge != null) {
                        rBadge.isVisible = false
                    }

                }
            }
        }


        bindingM.navView.background = null

        userPreferences.userStatus.asLiveData().observe(this, Observer {
            mobUs = it.toString()
            if (mobUs == "23002") {
                bindingM.card1.visible(true)
                bindingM.tail1.visible(true)
                bindingM.next1.visible(true)
                bindingM.skip1.visible(true)
                bindingM.text1.visible(true)
            } else {
                bindingM.card1.visible(false)
                bindingM.tail1.visible(false)
                bindingM.next1.visible(false)
                bindingM.skip1.visible(false)
                bindingM.text1.visible(false)
                bindingM.blockingView.visible(false)
            }
        })

        bindingM.card2.visible(false)
        bindingM.tail2.visible(false)
        bindingM.next2.visible(false)
        bindingM.prev2.visible(false)
        bindingM.skip2.visible(false)
        bindingM.text2.visible(false)

        bindingM.card3.visible(false)
        bindingM.tail3.visible(false)
        bindingM.tail32.visible(false)
        bindingM.prev3.visible(false)
        bindingM.next3.visible(false)
        bindingM.skip3.visible(false)
        bindingM.text3.visible(false)


        bindingM.card4.visible(false)
        bindingM.tail4.visible(false)
        bindingM.next4.visible(false)
        bindingM.prev4.visible(false)
        bindingM.skip4.visible(false)
        bindingM.text4.visible(false)

        bindingM.card5.visible(false)
        bindingM.tail5.visible(false)
        bindingM.prev5.visible(false)
        bindingM.finish5.visible(false)
        bindingM.text5.visible(false)

        bindingM.skip1.setOnClickListener {
            getUserSI()
            lifecycleScope.launch {
                viewModel.saveUserStatus(endStatus)
            }

            bindingM.blockingView.visible(false)
            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.skip2.setOnClickListener {

            getUserSI()
            lifecycleScope.launch {
                viewModel.saveUserStatus(endStatus)
            }
            bindingM.blockingView.visible(false)
            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.skip3.setOnClickListener {

            getUserSI()
            lifecycleScope.launch {
                viewModel.saveUserStatus(endStatus)
            }
            bindingM.blockingView.visible(false)
            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.skip4.setOnClickListener {

            getUserSI()
            lifecycleScope.launch {
                viewModel.saveUserStatus(endStatus)
            }
            bindingM.blockingView.visible(false)
            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.finish5.setOnClickListener {

            getUserSI()
            lifecycleScope.launch {
                viewModel.saveUserStatus(endStatus)
            }
            bindingM.blockingView.visible(false)
            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }

        bindingM.next1.setOnClickListener {


            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(true)
            bindingM.tail2.visible(true)
            bindingM.next2.visible(true)
            bindingM.prev2.visible(true)
            bindingM.skip2.visible(true)
            bindingM.text2.visible(true)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.next2.setOnClickListener {


            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(true)
            bindingM.tail3.visible(true)
            bindingM.tail32.visible(true)
            bindingM.prev3.visible(true)
            bindingM.next3.visible(true)
            bindingM.skip3.visible(true)
            bindingM.text3.visible(true)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.next3.setOnClickListener {


            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(true)
            bindingM.tail4.visible(true)
            bindingM.next4.visible(true)
            bindingM.prev4.visible(true)
            bindingM.skip4.visible(true)
            bindingM.text4.visible(true)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.next4.setOnClickListener {


            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(true)
            bindingM.tail5.visible(true)
            bindingM.prev5.visible(true)
            bindingM.finish5.visible(true)
            bindingM.text5.visible(true)
        }

        bindingM.prev5.setOnClickListener {


            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(true)
            bindingM.tail4.visible(true)
            bindingM.next4.visible(true)
            bindingM.prev4.visible(true)
            bindingM.skip4.visible(true)
            bindingM.text4.visible(true)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.prev4.setOnClickListener {


            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(true)
            bindingM.tail3.visible(true)
            bindingM.tail32.visible(true)
            bindingM.prev3.visible(true)
            bindingM.next3.visible(true)
            bindingM.skip3.visible(true)
            bindingM.text3.visible(true)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.prev3.setOnClickListener {


            bindingM.card1.visible(false)
            bindingM.tail1.visible(false)
            bindingM.next1.visible(false)
            bindingM.skip1.visible(false)
            bindingM.text1.visible(false)


            bindingM.card2.visible(true)
            bindingM.tail2.visible(true)
            bindingM.next2.visible(true)
            bindingM.prev2.visible(true)
            bindingM.skip2.visible(true)
            bindingM.text2.visible(true)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }
        bindingM.prev2.setOnClickListener {


            bindingM.card1.visible(true)
            bindingM.tail1.visible(true)
            bindingM.next1.visible(true)
            bindingM.skip1.visible(true)
            bindingM.text1.visible(true)


            bindingM.card2.visible(false)
            bindingM.tail2.visible(false)
            bindingM.next2.visible(false)
            bindingM.prev2.visible(false)
            bindingM.skip2.visible(false)
            bindingM.text2.visible(false)

            bindingM.card3.visible(false)
            bindingM.tail3.visible(false)
            bindingM.tail32.visible(false)
            bindingM.prev3.visible(false)
            bindingM.next3.visible(false)
            bindingM.skip3.visible(false)
            bindingM.text3.visible(false)


            bindingM.card4.visible(false)
            bindingM.tail4.visible(false)
            bindingM.next4.visible(false)
            bindingM.prev4.visible(false)
            bindingM.skip4.visible(false)
            bindingM.text4.visible(false)

            bindingM.card5.visible(false)
            bindingM.tail5.visible(false)
            bindingM.prev5.visible(false)
            bindingM.finish5.visible(false)
            bindingM.text5.visible(false)
        }



        val navView: BottomNavigationView = findViewById(R.id.nav_view)



        val navController = findNavController(R.id.nav_home_host_fragment)
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.promotionsFragment, R.id.secondNavFragment, R.id.firstNavFragment, R.id.fourthNavFragment))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)







    }

    public fun addCartBadge() {
        val cBadge: BadgeDrawable = bindingM.navView.getOrCreateBadge(
                R.id.fourthNavFragment)

        cBadge.isVisible = true
    }

    public fun removeCartBadge() {
        val cBadge: BadgeDrawable = bindingM.navView.getOrCreateBadge(
                R.id.fourthNavFragment)

        cBadge.isVisible = false
    }

//    public fun addNotificationBadge() {
//        val cBadge: BadgeDrawable = bindingM.navView.getOrCreateBadge(
//                R.id.thirdNavFragment)
//
//        cBadge.isVisible = true
//    }
//
//    public fun removeNotificationBadge() {
//        val cBadge: BadgeDrawable = bindingM.navView.getOrCreateBadge(
//                R.id.thirdNavFragment)
//
//        cBadge.isVisible = false
//    }



    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }


    fun getUserSI() {
        userPreferences.userId.asLiveData().observe(this, Observer {
            val id = it.toString()
            viewModel.getUserSI(id)
        })


    }

    fun setSubTotal(sub: Double) {
        subTotal = sub
    }

    fun setLocationPrice(loc: Double, locId: Int, locName: String) {
        locationPrice = loc
        locationId = locId
        locationName = locName
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController2 = findNavController(R.id.nav_home_host_fragment)
        val appBarConfiguration2 by lazy {
            AppBarConfiguration(
                    navController2.graph
            )
        }
        return navController2.navigateUp(appBarConfiguration2) || super.onSupportNavigateUp()
    }



}