package com.kzdev.appnotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.kzdev.appnotification.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mensagem = "50% de desconto"
        val titulo = "Promoção"

        // Configuração do canal de notificação
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notificacao" // Use apenas caracteres ASCII padrão
            val channelName = "Canal de Notificação"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)

            // Configurações adicionais do canal (opcional)
            channel.description = "Descrição do Canal"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Configuração do Intent para abrir a atividade quando a notificação for clicada
        val intent = Intent(this, NotificationActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Configuração da notificação
        val notification = NotificationCompat.Builder(this, "notificacao")
            .setContentTitle(titulo)
            .setContentText(mensagem)
            .setSmallIcon(R.drawable.baseline_email_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setColor(ContextCompat.getColor(this, R.color.white))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)  // Adiciona o PendingIntent à notificação
            .build()

        // Exibição da notificação
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(9000, notification)
    }
}
