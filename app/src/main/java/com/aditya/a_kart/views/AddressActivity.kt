package com.aditya.a_kart.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.aditya.a_kart.Model.AddressModel
import com.aditya.a_kart.MyViewModel
import com.aditya.a_kart.R
import com.aditya.a_kart.databinding.ActivityAddressBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import es.dmoral.toasty.Toasty

class AddressActivity : AppCompatActivity() {
    lateinit var viewModel: MyViewModel
    lateinit var binding: ActivityAddressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val db = Firebase.firestore
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]

        viewModel.authState.observe(this) {
            if (it == true) {
                Toasty.success(this, "Saved", Toasty.LENGTH_SHORT).show()
            } else {
                Toasty.error(this, "Not Saved", Toasty.LENGTH_SHORT).show()
            }
        }

        viewModel._getAddress.observe(this) {

            for (i in it) {
                binding.Fullname.setText(i.name)
                binding.phoneNumber.setText(i.phoneNo)
                binding.pincode.setText(i.pincode)
                binding.distric.setText(i.distric)
                binding.houseNo.setText(i.houseNo)
                binding.roadName.setText(i.roadName)

            }
        }
        viewModel.getAddress()

        binding.saveBtn.setOnClickListener {

            if (checkAllData()) {

                val fullName = binding.Fullname.text.toString().trim()
                val phNo = binding.phoneNumber.text.toString().trim()
                val pincode = binding.pincode.text.toString()
                val distric = binding.distric.text.toString()
                val houseNo = binding.houseNo.text.toString()
                val roadName = binding.roadName.text.toString()
                val docId =
                    db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid)
                        .collection("Address").document().id
                val address =
                    AddressModel(fullName, phNo, distric, houseNo, pincode, roadName, docId)

                viewModel.setAddress(address)

            } else {
                Toasty.error(this, "Enter Data Correctly", Toasty.LENGTH_SHORT).show()
            }

        }


    }

    private fun checkAllData(): Boolean {
        if (binding.Fullname.text!!.isEmpty()) {
            binding.Fullname.error = "Enter Name"
            return false
        }

        if (binding.phoneNumber.text!!.isEmpty()) {
            binding.phoneNumber.error = "Enter Number"
            return false
        }
        if (binding.distric.text!!.isEmpty()) {
            binding.distric.error = "Enter Distric"
            return false
        }
        if (binding.pincode.text!!.isEmpty()) {
            binding.pincode.error = "Enter Pincode"
            return false
        }

        if (binding.houseNo.text!!.isEmpty()) {
            binding.houseNo.error = "Enter these Data"
            return false
        }

        if (binding.roadName.text!!.isEmpty()) {
            binding.roadName.error = "Enter the Data"
            return false
        }

        return true
    }
}