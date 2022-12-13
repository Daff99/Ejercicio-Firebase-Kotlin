package com.example.ejerciciofirebasekotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ejerciciofirebasekotlin.databinding.ActivityAuthBinding
import com.example.ejerciciofirebasekotlin.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("Email")
        val contraseña: String? = bundle?.getString("Contraseña")
        binding.tvCorreoBlank.text = email
        binding.tvProveedorBlank.text = contraseña

        binding.buttonLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, InitSesionActivity::class.java))
            finish()
        }
    }
}