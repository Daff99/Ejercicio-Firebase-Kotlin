package com.example.ejerciciofirebasekotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ejerciciofirebasekotlin.databinding.ActivityAuthBinding
import com.example.ejerciciofirebasekotlin.databinding.ActivityInitSesionBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class InitSesionActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100
    private lateinit var binding: ActivityInitSesionBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInitSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.buttonInitSesion.setOnClickListener {
            var em: String = binding.etCorreoInit.text.toString()
            var pw: String = binding.etPwInit.text.toString()
            if ((em.isEmpty()) && (pw.isEmpty())) {
                Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show()
            } else {
                loginUser(em, pw)
            }
        }

        binding.buttonGoogle.setOnClickListener {
            sessionGoogle() //Comentario de prueba para el push
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            finish()
                            Toast.makeText(this, "Inicio de sesión con Google", Toast.LENGTH_LONG).show()
                            var intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    private fun loginUser(email: String, pw: String) {
        auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener {
            if (it.isSuccessful) {
                finish()
                Toast.makeText(this, "Inicio de sesión realizado con éxito", Toast.LENGTH_LONG).show()
                var intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("Email", binding.etCorreoInit.text.toString())
                intent.putExtra("Contraseña", binding.etPwInit.text.toString())
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun sessionGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail().build()
        val cliente = GoogleSignIn.getClient(this, googleConf)
        cliente.signOut() //Util por si tenemos otra cuenta de google con la que hayamos iniciado sesion anteriormente
        startActivityForResult(cliente.signInIntent, GOOGLE_SIGN_IN)
    }
}