package com.aditya.a_kart.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.a_kart.Adapters.AllProductAdapter
import com.aditya.a_kart.Adapters.categoriesAdapter
import com.aditya.a_kart.Model.AllProductModel
import com.aditya.a_kart.Models.CategoryModel
import com.aditya.a_kart.MyViewModel
import com.aditya.a_kart.databinding.FragmentHomeBinding
import com.aditya.a_kart.views.AllCategoryActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        db = Firebase.firestore
        val viewModel=ViewModelProvider(this)[MyViewModel::class.java]
        viewModel.getUserProfileData()

        val editor=this.activity?.getSharedPreferences("User_Name",Context.MODE_PRIVATE)
        binding.nameUser.text="Hello, ${editor!!.getString("name","Loading...").toString()}"

        getUserData()

        binding.carousel.addData(CarouselItem("https://static.vecteezy.com/system/resources/previews/002/822/446/large_2x/sale-banner-template-design-big-sale-special-offer-promotion-discount-banner-vector.jpg"))
        binding.carousel.addData(CarouselItem("https://static.vecteezy.com/system/resources/previews/000/178/364/original/super-sale-offer-and-discount-banner-template-for-marketing-and-vector.jpg"))
        binding.carousel.addData(CarouselItem("https://graphicsfamily.com/wp-content/uploads/edd/2022/12/E-commerce-Product-Banner-Design-1180x664.jpg"))

        getCategoryData()
        getAllProductData()
        binding.allCateBtn.setOnClickListener {
       startActivity(Intent(requireContext(),AllCategoryActivity::class.java))
}

        return binding.root
    }

    private fun getUserData() {
        db.collection("Users").document(FirebaseAuth.getInstance().currentUser!!.uid).get().addOnSuccessListener{
            val name=it.get("name").toString()
            val editor=activity?.getSharedPreferences("User_Name",Context.MODE_PRIVATE)!!.edit()
            editor.putString("name",name)
            editor.apply()
        }
    }

    private fun getAllProductData() {
        db.collection("AllProducts").addSnapshotListener { value, error ->
            val listOfAllProducts = arrayListOf<AllProductModel>()
            val data = value?.toObjects(AllProductModel::class.java)
            listOfAllProducts.addAll(data!!)
            binding.allProductRecyclerView.adapter =
                AllProductAdapter(requireContext(), listOfAllProducts)
            binding.allProductRecyclerView.layoutManager =
                GridLayoutManager(requireContext(),2)
        }
    }


    private fun getCategoryData() {
        db.collection("Categories").addSnapshotListener { value, error ->
            val listOfCategory = arrayListOf<CategoryModel>()
            val data = value!!.toObjects(CategoryModel::class.java)
            listOfCategory.addAll(data)
            binding.categhoriesRecyclerView.adapter =
                categoriesAdapter(requireContext(), listOfCategory)
            binding.categhoriesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

}