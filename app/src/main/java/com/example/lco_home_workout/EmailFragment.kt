package com.example.lco_home_workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 */
class EmailFragment : Fragment() {

    private lateinit var v: View
    private lateinit var textEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_email, container, false)
        textEmail = v.findViewById(R.id.text_email)
        textEmail.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                transition()
            }
            false
        }
        return v
    }

    private fun transition() {
        val action = EmailFragmentDirections.actionEmailFragmentToUsernameFragment()
        v.findNavController().navigate(action)
    }
}
