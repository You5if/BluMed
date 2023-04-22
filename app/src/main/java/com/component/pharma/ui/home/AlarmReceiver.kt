package com.component.pharma.ui.home

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.component.pharma.R

class AlarmReceiver : BroadcastReceiver() {

    val CHANNEL_ID = "channelID"
    val NOTIFICATION_ID = 0


    override fun onReceive(context: Context?, intent: Intent?) {
        val i = Intent(context, HomeActivity::class.java)

        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, 0, i,  PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, i,  PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(context!!, CHANNEL_ID)
                .setContentTitle("BlueMed")
                .setContentText("Finally testing the notification")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)

        val notificaManager  = NotificationManagerCompat.from(context)

        notificaManager.notify(NOTIFICATION_ID, builder.build())
    }
}