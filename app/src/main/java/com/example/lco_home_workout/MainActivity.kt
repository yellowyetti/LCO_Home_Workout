package com.example.lco_home_workout

import android.app.Activity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth

class MainActivity : Activity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()


        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
    }
}
