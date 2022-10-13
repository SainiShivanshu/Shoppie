package com.example.shoppie.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppie.R
import com.example.shoppie.adapter.AllOrderAdapter
import com.example.shoppie.databinding.FragmentProfileBinding
import com.example.shoppie.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private lateinit var list: ArrayList<AllOrderModel>
    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(layoutInflater)
        list= ArrayList()
        val preferences = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)


        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId",preferences.getString("number","")!! )
            .get()
            .addOnSuccessListener {
                list.clear()
                for(doc in it){
                    val data = doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }
                binding.recyclerView.adapter= AllOrderAdapter(requireContext(),list)
            }

        return binding.root
    }



}