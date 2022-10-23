package com.example.shoppie.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.shoppie.adapter.CategoryAdapter
import com.example.shoppie.databinding.ActivitySeeAllCategoryBinding
import com.example.shoppie.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SeeAllCategoryActivity : AppCompatActivity() {
    private  lateinit var  binding: ActivitySeeAllCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySeeAllCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title="All Categories"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getCategories()
        binding.CategoryRecycler.layoutManager=GridLayoutManager(this,2)

    }
    private fun getCategories() {
        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories")
            .get().addOnSuccessListener {
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.CategoryRecycler.adapter = CategoryAdapter(this,list)
            }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}