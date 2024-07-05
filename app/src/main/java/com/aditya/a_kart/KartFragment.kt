package com.aditya.a_kart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.a_kart.Adapters.KartAdapter
import com.aditya.a_kart.Model.OrderModel
import com.aditya.a_kart.databinding.FragmentKartBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

  class KartFragment : Fragment() {

    lateinit var binding: FragmentKartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKartBinding.inflate(layoutInflater)


        getBadge()

        return binding.root
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
             binding.kartRec.adapter=KartAdapter(this,listOfData)
                binding.kartRec.layoutManager=LinearLayoutManager(requireContext())
            } else {
                Log.d("Hero2", "Current data: null")
            }

        }
    }
  }