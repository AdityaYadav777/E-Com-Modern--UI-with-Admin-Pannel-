package com.aditya.a_kart

import android.os.Bundle
import android.util.Log

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.aditya.a_kart.Model.OrderModel
import com.aditya.a_kart.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navController=findNavController(R.id.fragment)
        getBadge()
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)


    }
    private fun getBadge() {
        val listOfData = arrayListOf<OrderModel>()
        val db = Firebase.firestore
        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).collection("Orders").addSnapshotListener { value, error ->

            if (error != null) {
                Log.w("Hero1", "Listen failed.", error)
                return@addSnapshotListener
            }
            if (value != null) {
                val data = value.toObjects(OrderModel::class.java)
                listOfData.clear()
                listOfData.addAll(data)
               binding.bottomNavigationView.getOrCreateBadge(R.id.kartFragment).number=listOfData.size
            } else {
                Log.d("Hero2", "Current data: null")
            }

        }
    }

}