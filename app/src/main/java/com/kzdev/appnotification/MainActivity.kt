package com.kzdev.appnotification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kzdev.appnotification.databinding.ActivityMainBinding

// Primeira fase do projeto:

// criar projeto
// escolher metodo de mostrar a notificação
// cria uma função que mostre a notificação


// Segunda fase do projeto:

// inserir serviços no manifest
// criar classe PushFireBaseMessagingService
// alterar os metodos onMessageReceive e onNewToken


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}