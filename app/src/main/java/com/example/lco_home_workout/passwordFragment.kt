package com.example.lco_home_workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth

class passwordFragment : Fragment() {

    lateinit var v: View
    lateinit var textPassword: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_password, container, false)

        auth = FirebaseAuth.getInstance()
        val email = requireArguments().getString("email").toString()
        // val username = requireArguments().getString("username").toString()
        textPassword = v.findViewById(R.id.text_password)
        textPassword.setOnEditorActionListener { _, actionId, _ ->
            authenticate(email, textPassword.text.toString())
            true
        }
        return v
    }

    private fun authenticate(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if(task.isSuccessful) {
                    Log.d("passwordFragment", "createUserWithEmail:succes")
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Log.w("passwordFragment", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, task.exception.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}
