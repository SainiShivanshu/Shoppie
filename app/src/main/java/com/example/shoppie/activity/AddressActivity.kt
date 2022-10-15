package com.example.shoppie.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shoppie.R
import com.example.shoppie.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddressBinding
    private lateinit var preferences : SharedPreferences

    private lateinit var totalCost : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Delivery Address"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        preferences=this.getSharedPreferences("user", MODE_PRIVATE)
    totalCost= intent.getStringExtra("totalCost")!!
        loadUserInfo()

        binding.proceed.setOnClickListener {
            validateData(
                binding.userName.text.toString(),
                binding.userNumber.text.toString(),
                binding.userHouse.text.toString(),
                binding.userCity.text.toString(),
                binding.userState.text.toString(),
                binding.pincode.text.toString()
            )
        }

    }

    private fun validateData(name: String, number: String, houseNo: String, city: String, state: String, pinCode: String) {
         if (number.isEmpty()||name.isEmpty()||houseNo.isEmpty()||city.isEmpty()||state.isEmpty()||pinCode.isEmpty()){
             Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()

         }
        else{
            storeData(houseNo,city,state,pinCode)
         }
    }

    private fun storeData( houseNo: String, city: String, state: String, pinCode: String) {
  val map = hashMapOf<String,Any>()
        map["houseNo"]=houseNo
        map["city"]=city
        map["state"]=state
        map["pinCode"]=pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                val b =Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)
                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)


            startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userHouse.setText(it.getString("houseNo"))
                binding.userCity.setText(it.getString("city"))
                binding.userState.setText(it.getString("state"))
                binding.pincode.setText(it.getString("pinCode"))
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}