package com.kzdev.appnotification.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.kzdev.appnotification.NotificationActivity
import com.kzdev.appnotification.R
import kotlin.random.Random

class PushFireBaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.i("Notification_APP", "Novo token recebido: $s")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage){
        super.onMessageReceived(remoteMessage).toString()
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        handlerShowNotification(applicationContext,title,body)
    }

    private fun handlerShowNotification(context: Context,title: String?,message:String?) {

        val title = HtmlCompat.fromHtml(
            "<strong> $title </strong>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )

        val notificationChannel: NotificationChannel
        val builder: NotificationCompat.Builder
        val channelId = "com.kzdev.appnotification"

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = Random.nextInt(1, 100)

        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, message, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_email_24)
                .setContentTitle(title)
                //.setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText(message)
                    .setSummaryText("Summary")
                    .setBigContentTitle("Big Text")
                )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        } else {
            builder = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_email_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(id, builder.build())
    }
}