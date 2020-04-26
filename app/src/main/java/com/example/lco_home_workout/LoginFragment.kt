package com.example.lco_home_workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.*
import java.lang.Exception

class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleApiClient: GoogleApiClient
    private lateinit var mGoogleSignInButton: com.google.android.gms.common.SignInButton
    private lateinit var signOutButton: Button
    private val RC_SIGN_IN: Int = 800
    private lateinit var v: View

    override fun onCreate(savedInstanceState: Bundle?) {
        // firebase authentication and options
        auth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false)

        mGoogleSignInButton = v.findViewById(R.id.google_sign_in_button)
        signOutButton = v.findViewById(R.id.button_sign_out)

        mGoogleSignInButton.setOnClickListener(this)
        signOutButton.setOnClickListener(this)

        return v
    }


    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.google_sign_in_button -> {
                try {
                    this.signIn()
                    Toast.makeText(context, "Signed in", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context,"GOOGLE SIGN IN Button FAILED", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.button_sign_out -> {
                try {
                    this.signOut()
                    Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            else -> {
                return
            }
        }
        return
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

    private fun signOut() {
        auth.signOut()
        mGoogleSignInClient.signOut()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returning from google sign in
        if(requestCode == RC_SIGN_IN) {
            val task: GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            try {
                //Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount? = task!!.signInAccount
                firebaseAuthWithGoogle(account!!)
            } catch(e: ApiException) {
                Log.w("LOGIN_ACTIVITY", "Google sign in failed", e)
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            Toast.makeText(context, "USER IS ALREADY LOGGED IN", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(context, "USER is not LOGGED IN", Toast.LENGTH_SHORT).show()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LOGIN_ACTIVITY", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential: AuthCredential = GoogleAuthProvider.getCredential(acct.idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener(this.requireActivity()) {
            if(it.isSuccessful) {
                Log.d("LOGIN_FRAGMENT", "signInWithCredential:success")
                Toast.makeText(context, auth.currentUser!!.displayName, Toast.LENGTH_SHORT).show()
            }
            else {
                Log.w("LOGIN_ACTIVITY", "signInWithCredential:failure")
                    Toast.makeText(context, "Authentication failed: ${auth.currentUser}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

