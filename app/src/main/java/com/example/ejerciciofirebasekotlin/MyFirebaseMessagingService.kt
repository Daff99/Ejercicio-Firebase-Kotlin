package com.example.ejerciciofirebasekotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        var title: String = message.notification?.title ?: "" //Hace lo mismo que lateinit
        var body: String? = message.notification?.body
        val CHANNEL_ID: String = "HEADS_UP_NOTIFICATION"
        var channel: NotificationChannel =
            NotificationChannel(CHANNEL_ID, "Heads Up Notification", NotificationManager.IMPORTANCE_DEFAULT)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        var notification = Notification.Builder(this, CHANNEL_ID).setContentTitle(title)
            .setContentText(body).setSmallIcon(R.drawable.ic_launcher_background).setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(1, notification.build())
    }
}