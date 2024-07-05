package com.aditya.a_kart.views

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.aditya.a_kart.Adapters.AllProductAdapter
import com.aditya.a_kart.Model.AllProductModel
import com.aditya.a_kart.R
import com.aditya.a_kart.databinding.ActivityProductByCategoryBinding
import com.google.android.material.transition.MaterialContainerTransform.ProgressThresholds
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ProductByCategoryActivity : AppCompatActivity() {
    lateinit var binding:ActivityProductByCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityProductByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val category_name=intent.getStringExtra("category_name")
                val daialog=ProgressDialog(this)
                daialog.setTitle("Wait")
                daialog.show()
        val db=Firebase.firestore
        db.collection("Categories").document(category_name!!).collection("Product").addSnapshotListener { value, error ->
            val listOfAllData= arrayListOf<AllProductModel>()
            val data=value?.toObjects(AllProductModel::class.java)
            listOfAllData.addAll(data!!)
            binding.cateAllProduct.adapter=AllProductAdapter(this,listOfAllData)
            binding.cateAllProduct.layoutManager=GridLayoutManager(this,2)
            daialog.dismiss()
        }

    }
}