package com.aditya.a_kart.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aditya.a_kart.MyViewModel
import com.aditya.a_kart.databinding.FragmentProfileBinding
import com.aditya.a_kart.views.AddressActivity

class ProfileFragment : Fragment() {
  lateinit var binding:FragmentProfileBinding
  lateinit var viewModel: MyViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProfileBinding.inflate(layoutInflater)


        viewModel=ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.getUserProfileData()

            viewModel._getData.observe(requireActivity()){
                binding.proName.text=it.name
                binding.proEmail.text=it.email
            }

        binding.addressBtn.setOnClickListener {
            startActivity(Intent(requireContext(),AddressActivity::class.java))
        }

        return binding.root
    }


}