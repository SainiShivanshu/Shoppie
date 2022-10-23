package com.example.shoppie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoppie.R
import com.example.shoppie.adapter.ProductAdapter
import com.example.shoppie.databinding.ActivitySeeAllProductBinding
import com.example.shoppie.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SeeAllProductActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySeeAllProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySeeAllProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="All Products"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getProducts()
    }
    private fun getProducts() {
        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRecycler.adapter = ProductAdapter(this,list)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}