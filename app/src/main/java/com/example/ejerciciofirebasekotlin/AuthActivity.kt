package com.example.ejerciciofirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.ejerciciofirebasekotlin.databinding.ActivityAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.getInstance
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {
            var email: String = binding.etCorreo.text.toString()
            var password: String = binding.etPw.text.toString()
            var passwordRepeat: String = binding.etPwRepite.text.toString()
            if ((email.isEmpty()) && (password.isEmpty()) && (passwordRepeat.isEmpty())) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
            } else if ((!password.equals(passwordRepeat))) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            } else {
                createAccount(email, password)
            }
        }

        binding.buttonInit.setOnClickListener {
            var intent = Intent(this, InitSesionActivity::class.java)
            intent.putExtra("Email", binding.etCorreo.text.toString())
            intent.putExtra("Contraseña", binding.etPw.text.toString())
            startActivity(intent)
        }
    }

    private fun createAccount(correo: String, contraseña: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contraseña).
                addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
    }

}