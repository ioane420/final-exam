package com.example.myapplication.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.PersonInfo
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registration: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("USER_INFO")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstName=view.findViewById(R.id.editTextPersonFirstName)
        lastName=view.findViewById(R.id.editTextPersonLastName)
        email = view.findViewById(R.id.editTextEmail2)
        password = view.findViewById(R.id.editTextPassword2)
        registration = view.findViewById(R.id.registrationButton2)

        registration.setOnClickListener {
            val userFirstName = firstName.text.toString()
            val userLastName = lastName.text.toString()

            val userEmail = email.text.toString()
            val userPassword = password.text.toString()

            if (userEmail.isEmpty() || userPassword.isEmpty() || userFirstName.isEmpty() || userLastName.isEmpty()) {
                Toast.makeText(context, "Empty!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val personInfo = PersonInfo(userFirstName, userLastName)


            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        db.child(auth.currentUser?.uid!!).setValue(personInfo)
                        val action = RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment()
                        findNavController().navigate(action)
                    }else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_LONG).show()
                    }
                }
        }


    }
}