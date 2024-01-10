package com.kzdev.appnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import com.kzdev.appnotification.databinding.ActivityMainBinding
import kotlin.random.Random

// Primeira parte do projeto é:
// criar projeto
// escolher metodo de mostrar a notificação
// cria uma função que mostre a notificação

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // escolher metodo de mostrar a notificação
        criarNotificacao()

    }

    // cria uma função que mostre a notificação
    private fun criarNotificacao() {
        val title = HtmlCompat.fromHtml(
            "<strong> Titulo da Notificação</strong>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        val description = "descricao da notificacao"
        val body = "corpo da notificacao"

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
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_email_24)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        } else {
            builder = NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_email_24)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(id, builder.build())
    }
}
