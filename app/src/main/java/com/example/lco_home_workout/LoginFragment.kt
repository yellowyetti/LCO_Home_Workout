package com.example.lco_home_workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mGoogleSignInButton: com.google.android.gms.common.SignInButton
    private val RC_SIGN_IN: Int = 800
    private lateinit var vg: View

    override fun onCreate(savedInstanceState: Bundle?) {
        // firebase authentication and options
        auth = FirebaseAuth.getInstance()
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vg = inflater.inflate(R.layout.fragment_login, container, false)

        mGoogleSignInButton = vg.findViewById(R.id.google_sign_in_button)
        mGoogleSignInButton.setOnClickListener(this)

        return vg
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

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null) {
            Toast.makeText(context, "USER IS ALREADY LOGGED IN", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(context, "USER is not LOGGED IN", Toast.LENGTH_SHORT).show()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LOGIN_ACTIVITY", "firebaseAuthWithGoogle:" + acct.id!!)

//        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
//
//        val task: Task<AuthResult> = auth.signInWithCredential(credential)
//        task.addOnCompleteListener { task ->
//            if(task.isSuccessful) {
//                Log.d("LOGIN_FRAGMENT", "signInWithCredential:success")
//                Toast.makeText(context, auth.currentUser!!.displayName, Toast.LENGTH_SHORT).show()
//            }
//            else {
//                Log.w("LOGIN_ACTIVITY", "signInWithCredential:failure", task.exception)
//                Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}
