package com.aditya.a_kart.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.a_kart.Models.CategoryModel
import com.aditya.a_kart.R
import com.aditya.a_kart.views.AllCategoryActivity
import com.aditya.a_kart.views.ProductByCategoryActivity

class showAllcategoryAdapter(
   val allCategoryActivity: AllCategoryActivity,
val    listOfData: ArrayList<CategoryModel>
) :RecyclerView.Adapter<showAllcategoryAdapter.myViewHolder>() {
    class myViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val name=itemView.findViewById<TextView>(R.id.name)
        val img=itemView.findViewById<ImageView>(R.id.imgCate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
     return myViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_categhory_all_view,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        holder.name.text=listOfData[position].name
        holder.img.load(listOfData[position].url)
        holder.itemView.setOnClickListener {
            val i =Intent(allCategoryActivity,ProductByCategoryActivity::class.java)
            i.putExtra("category_name",listOfData[position].name)
            allCategoryActivity.startActivity(i)
        }
    }
}