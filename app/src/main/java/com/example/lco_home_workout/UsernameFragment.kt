package com.example.lco_home_workout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

class UsernameFragment : Fragment() {

    private lateinit var v: View
    private lateinit var textUsername: EditText
    private val args: UsernameFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_username, container, false)
        textUsername = v.findViewById(R.id.text_username)
        textUsername.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO) {
                transition()
            }
            false
        }
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun transition() {
        val email = args.email
        val username = textUsername.text.toString()
        val action = UsernameFragmentDirections.actionUsernameFragmentToPasswordFragment(username, email)
        v.findNavController().navigate(action)
    }
}
