package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth



class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (FirebaseAuth.getInstance().currentUser != null){
            val action = LoginFragmentDirections.actionLoginFragmentToProfileFragment()
            findNavController().navigate(action)
        }

        super.onViewCreated(view, savedInstanceState)




        binding.loginButton.setOnClickListener {
            val userEmail = binding.editTextEmail.text.toString()
            val userPassword = binding.editTextPassword.text.toString()


            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(context, "Empty!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val action = LoginFragmentDirections.actionLoginFragmentToProfileFragment()
                        findNavController().navigate(action)
                    }else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show()
                    }
                }



        }

        binding.registrationButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}