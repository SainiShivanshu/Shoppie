package com.example.shoppie.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppie.R
import com.example.shoppie.adapter.CategoryProductAdapter
import com.example.shoppie.model.AddProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        getProducts(intent.getStringExtra("cat"))

    }

    private fun getProducts(category: String?) {
        val list = ArrayList<AddProductModel>()

        supportActionBar?.title= category
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
                recyclerView.adapter = CategoryProductAdapter(this,list)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}