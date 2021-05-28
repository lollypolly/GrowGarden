package ru.itis.polypollya.growgarden.other

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.itis.polypollya.growgarden.MainActivity
import ru.itis.polypollya.growgarden.R

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val flowerName = intent?.getStringExtra("flowerName")
        val descFlower = intent?.getStringExtra("flowerDesc")
        val pushId = intent?.getIntExtra("flowerId", 0)
        showNotification(flowerName, descFlower, pushId, context!!)
    }

    private fun showNotification(
        nameOfFlower: String? = "",
        descFlower: String? = "",
        pushId: Int?,
        context: Context
    ) {
        createNotificationChannel(context)
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.putExtra("fromReceiver", true)
        val contentIntent = PendingIntent.getActivity(
            context,
            0, notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        val builder = NotificationCompat.Builder(
            context,
            "channel_id_grow_garden"
        )
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle(nameOfFlower)
            .setContentText(descFlower)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(contentIntent)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(descFlower)
            )
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.app_icon
                )
            )
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(context)
        if (pushId != null) {
            notificationManager.notify(
                pushId,
                builder.build()
            )
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "GROW"
            val description = "GARDEN"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                "channel_id_grow_garden",
                name,
                importance
            )
            channel.description = description
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}
