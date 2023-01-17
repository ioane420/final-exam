package com.example.myapplication.fragments2

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.PersonInfo
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var userFirstName: TextView
    private lateinit var userLastName: TextView
    private lateinit var logOut: Button

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().getReference("USER_INFO")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userFirstName = view.findViewById(R.id.textView3)
        userLastName = view.findViewById(R.id.textView2)
        logOut = view.findViewById(R.id.button2)


        db.child(auth.currentUser?.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(PersonInfo::class.java) ?: return
                userFirstName.text = userInfo.firstName
                userLastName.text = userInfo.lastName;

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        logOut.setOnClickListener {

            AlertDialog.Builder(context)
                .setTitle("LOG OUT")
                .setMessage("Are you sure?")
                .setPositiveButton("YES") { dialog, i ->
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_global_loginFragment)
                    dialog.dismiss()
                }
                .setNegativeButton("NO") {dialog, i ->
                    dialog.dismiss()
                }

                .show()


        }



    }

}