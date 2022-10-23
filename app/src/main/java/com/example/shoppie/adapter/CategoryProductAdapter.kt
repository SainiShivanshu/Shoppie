package com.example.shoppie.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shoppie.MainActivity
import com.example.shoppie.activity.ProductDetailActivity
import com.example.shoppie.databinding.ItemCategoryProductLayoutBinding

import com.example.shoppie.model.AddProductModel
import com.example.shoppie.roomdb.AppDatabase
import com.example.shoppie.roomdb.ProductDao
import com.example.shoppie.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CategoryProductAdapter (val context: Context, val list: ArrayList<AddProductModel>)
    : RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){

    inner class CategoryProductViewHolder(val binding: ItemCategoryProductLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
        val productDao = AppDatabase.getInstance(context).productDao()
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView2.text = list[position].productName
        holder.binding.textView3.text =  list[position].productCategory
        holder.binding.textView4.text = "₹ " + list[position].productMrp
        holder.binding.button.text="₹ " +list[position].productSp
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)

        }
        if (productDao.isExit(list[position].productId!!) != null) {
            holder.binding.button2.text = "Go to Cart"
        } else {
            holder.binding.button2.text = "Add to Cart"
        }
        holder.binding.button2.setOnClickListener {

            cartAction(
                holder.binding,
                list[position].productId,
                list[position].productName,
                list[position].productSp,
                list[position].productCoverImg
            )
        }
    }

        private fun cartAction(
            binding: ItemCategoryProductLayoutBinding,
            productId: String?,
            productName: String?,
            productSp: String?,
            productCoverImg: String?
        ) {
            val productDao = AppDatabase.getInstance(context).productDao()

            if (productDao.isExit(productId!!) != null) {
                openCart()
            } else {
                addToCart(
                    binding,
                    productDao,
                    productId,
                    productName as String?,
                    productSp as String?,
                    productCoverImg!!
                )
            }

        }


        private fun openCart() {
            val preference = context.getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
            val editor = preference.edit()
            editor.putBoolean("isCart", true)
            editor.apply()

            context.startActivity(Intent(context, MainActivity::class.java))

        }

        private fun addToCart(
            binding: ItemCategoryProductLayoutBinding,
            productDao: ProductDao,
            productId: String,
            productName: String?,
            productSp: String?,
            productCoverImg: String
        ) {
            val data = ProductModel(productId, productName!!, productCoverImg!!, productSp!!)


            GlobalScope
                .launch(Dispatchers.IO) {
                    productDao.insertProduct(data)
                    binding.button2.text = "Go to Cart"
                }
        }


        override fun getItemCount(): Int {
       return list.size
    }

}