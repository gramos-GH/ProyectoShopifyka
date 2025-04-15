package com.example.proyectoshopifyka.view.home

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.ui.AppBarConfiguration
import com.example.proyectoshopifyka.R
import com.example.proyectoshopifyka.databinding.ActivityHomeBinding
import com.example.proyectoshopifyka.utils.FragmentComunicator
import com.google.firebase.FirebaseApp


class HomeActivity : AppCompatActivity(), FragmentComunicator {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showLoader(value: Boolean) {
        binding.loaderContainerView.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun enviarMensaje(mensaje: String) {

    }
}