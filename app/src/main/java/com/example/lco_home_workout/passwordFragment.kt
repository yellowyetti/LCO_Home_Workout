package com.example.lco_home_workout

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.navArgs

class passwordFragment : Fragment() {

    lateinit var v: View
    lateinit var textPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_password, container, false)
        textPassword = v.findViewById(R.id.text_password)

        Log.d("passwordFragment", requireArguments().getString("email").toString())

        return v
    }
}
