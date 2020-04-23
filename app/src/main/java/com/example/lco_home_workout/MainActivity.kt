package com.example.lco_home_workout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()


        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        val currentUer = auth.currentUser
        Toast.makeText(this, currentUer?.displayName, Toast.LENGTH_LONG).show()
    }
}
