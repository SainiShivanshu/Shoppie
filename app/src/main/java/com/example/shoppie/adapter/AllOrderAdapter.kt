package com.example.shoppie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppie.databinding.AllOrderItemLayoutBinding
import com.example.shoppie.model.AllOrderModel

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapter(val context:Context,val list:ArrayList<AllOrderModel>)
    : RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>(){

    inner class AllOrderViewHolder(val binding : AllOrderItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text=list[position].name
        holder.binding.productPrice.text=list[position].price
        holder.binding.productStatus.text=list[position].status
        holder.binding.cancelButton.setOnClickListener {

            holder.binding.cancelButton.visibility=GONE
            updateStatus("Cancelled",list[position].orderId!!)
            holder.binding.productStatus.text=list[position].status
        }
        when(list[position].status){
            "Ordered" ->{



                    holder.binding.productStatus.text=list[position].status


            }
            "Dispatched"->{
                holder.binding.productStatus.text=list[position].status
            }
            "Delivered"->{
                holder.binding.cancelButton.visibility=GONE
                holder.binding.productStatus.text=list[position].status

            }
            "Cancelled"->{
                holder.binding.cancelButton.visibility=GONE
                holder.binding.productStatus.text=list[position].status
            }
        }
    }

    fun updateStatus(str:String,doc:String){
        val data = hashMapOf<String,Any>()
        data["status"]=str
        Firebase.firestore.collection("allOrders")
            .document(doc).update(data).addOnSuccessListener {
                Toast.makeText(context,"Status Updated",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
            }
    }

    override fun getItemCount(): Int {
      return list.size
    }
}