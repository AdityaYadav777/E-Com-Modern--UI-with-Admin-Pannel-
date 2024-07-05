package com.aditya.a_kart.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.aditya.a_kart.KartFragment
import com.aditya.a_kart.Model.OrderModel
import com.aditya.a_kart.databinding.KartItemViewBinding

class KartAdapter(val kartFragment: KartFragment,val  listOfData: ArrayList<OrderModel>) :RecyclerView.Adapter<KartAdapter.myViewHolder>() {

    class myViewHolder(private val binding:KartItemViewBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(dataModel: OrderModel){
            binding.apply {
                orderedImg.load(dataModel.productUrl)
                orderTitle.text=dataModel.product
                orderStatus.text= "Status: ${ dataModel.status}"
                orderedQuantity.text="X${dataModel.quantity}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
      return myViewHolder(KartItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return listOfData.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
      holder.bind(listOfData[position])
    }
}