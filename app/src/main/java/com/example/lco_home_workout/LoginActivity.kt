package com.example.lco_home_workout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : Activity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInButton: com.google.android.gms.common.SignInButton
    private val RC_SIGN_IN: Int = 800

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // firebase authentication and options
        auth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        setContentView(R.layout.activity_login)
        mGoogleSignInButton = findViewById(R.id.google_sign_in_button)

        mGoogleSignInButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.google_sign_in_button -> {
                this.signIn()
            }
            else -> {
                return
            }
        }

        return
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returning from google sign in
        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch(e: ApiException) {
                Log.w("LOGIN_ACTIVITY", "Google sign in failed", e)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        //Check if user is already signed in
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            Toast.makeText(this, "USER IS ALREADY LOGGED IN", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(this, "USER is not LOGGED IN", Toast.LENGTH_SHORT).show()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LOGIN_ACTIVITY", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    // Sign in success, update UI ith signed-in user's information
                    Log.d("LOGIN_ACTIVITY", "signInWithCredential:success")
                    Toast.makeText(this, auth.currentUser!!.displayName, Toast.LENGTH_SHORT).show()
                }
                else {
                    // Sign in failed
                    Log.w("LOGIN_ACTIVITY", "signInWithCredential:failure", task.exception)
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
