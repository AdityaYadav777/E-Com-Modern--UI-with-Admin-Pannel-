package com.aditya.a_kart.Adapters

import android.content.Context
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
import com.aditya.a_kart.views.ProductByCategoryActivity

class categoriesAdapter(val homeFragment: Context, val listOfCategory: ArrayList<CategoryModel>) :RecyclerView.Adapter<categoriesAdapter.myViewHolders>() {

    class myViewHolders(itemView: View) :RecyclerView.ViewHolder(itemView){
     val img=itemView.findViewById<ImageView>(R.id.cateImg)
        val name=itemView.findViewById<TextView>(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolders {
        return myViewHolders(LayoutInflater.from(parent.context).inflate(R.layout.item_cate_view,parent,false))
    }

    override fun getItemCount(): Int {
       return listOfCategory.size
    }

    override fun onBindViewHolder(holder: myViewHolders, position: Int) {
        holder.img.load(listOfCategory[position].url)
        holder.name.text = listOfCategory[position].name.capitalize()
        holder.itemView.setOnClickListener {
            val i = Intent(homeFragment, ProductByCategoryActivity::class.java)
            i.putExtra("category_name",listOfCategory[position].name)
            homeFragment.startActivity(i)
        }
    }
}