package com.aditya.a_kart.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aditya.a_kart.Adapters.showAllcategoryAdapter
import com.aditya.a_kart.Models.CategoryModel
import com.aditya.a_kart.R
import com.aditya.a_kart.databinding.ActivityAllCategoryBinding
import com.google.firebase.firestore.FirebaseFirestore

class AllCategoryActivity : AppCompatActivity() {
    lateinit var binding:ActivityAllCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityAllCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        FirebaseFirestore.getInstance().collection("Categories").addSnapshotListener { value, error ->

            val listOfData= arrayListOf<CategoryModel>()
            val data=value?.toObjects(CategoryModel::class.java)
            listOfData.addAll(data!!)
            binding.showAllCateRec.adapter=showAllcategoryAdapter(this,listOfData)
            binding.showAllCateRec.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        }



    }
}