package com.example.lco_home_workout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception

class MainActivity : Activity(), View.OnClickListener {

    private lateinit var buttonSignOut: Button
    private lateinit var textDisplayName: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val displayName = auth.currentUser?.displayName

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        buttonSignOut = findViewById(R.id.button_sign_out)
        textDisplayName = findViewById(R.id.text_display_name)

        if(displayName != null) {
            textDisplayName.text = displayName
        }
        else textDisplayName.text = auth.currentUser?.email

        buttonSignOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.button_sign_out -> {
                try {
                    this.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        mGoogleSignInClient.signOut()
    }
}
