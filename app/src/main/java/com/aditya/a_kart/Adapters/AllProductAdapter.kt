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
import com.aditya.a_kart.Model.AllProductModel
import com.aditya.a_kart.ProductPreviewActivity
import com.aditya.a_kart.R

class AllProductAdapter(val requireContext: Context, val listOfAllProducts: ArrayList<AllProductModel>) :RecyclerView.Adapter<AllProductAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val img=itemView.findViewById<ImageView>(R.id.productImg)
        val name=itemView.findViewById<TextView>(R.id.productName)
        val prise=itemView.findViewById<TextView>(R.id.productPrise)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
      return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_all_product_view,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfAllProducts.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.img.load(listOfAllProducts[position].url)
        holder.prise.text="${listOfAllProducts[position].prise} Rs"
        holder.name.text=listOfAllProducts[position].name
        holder.itemView.setOnClickListener {
        ProductPreviewActivity.dataFromProduct=listOfAllProducts[position]
            requireContext.startActivity(Intent(requireContext,ProductPreviewActivity::class.java))
        }

    }
}