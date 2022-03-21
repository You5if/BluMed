package com.component.pharma.ui.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.graphics.Color
import android.os.*
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.*
import com.component.pharma.R
import com.component.pharma.data.Resource
import com.component.pharma.data.RetrofitInstance
import com.component.pharma.data.UserPreferences
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.data.db.promtionsdp.PromotionsDatabase
import com.component.pharma.data.network.AuthApi
import com.component.pharma.data.network.HomeApi
import com.component.pharma.data.repository.AuthRepository
import com.component.pharma.data.repository.HomeRepository
import com.component.pharma.databinding.ActivityNameBinding
import com.component.pharma.databinding.FragmentFirstNavBinding
import com.component.pharma.ui.auth.AuthViewModel
import com.component.pharma.ui.base.BaseFragment
import com.component.pharma.ui.base.ViewModelFactory
import com.component.pharma.ui.home.HomeActivity
import com.component.pharma.ui.home.HomeViewModel
import kotlinx.coroutines.launch

class MyService: Service() {


//    val mainHandler = Handler(Looper.getMainLooper())
//
//    val post = mainHandler.post(object : Runnable {
//        override fun run() {
//
//            Toast.makeText(applicationContext, "background", Toast.LENGTH_SHORT).show()
//            mainHandler.postDelayed(this, 7000)
//        }
//    })


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)
        return super.onStartCommand(intent, flags, startId)
    }



    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

}
