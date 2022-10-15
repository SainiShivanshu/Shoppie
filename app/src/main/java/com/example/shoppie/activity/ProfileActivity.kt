package com.example.shoppie.activity


import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppie.databinding.ActivityProfileBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)
supportActionBar?.title="Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        preferences=this.getSharedPreferences("user", MODE_PRIVATE)
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
            Toast.makeText(this,"Please fill all fields", Toast.LENGTH_SHORT).show()

        }
        else{
            storeData(name,houseNo,city,state,pinCode)
        }
    }
    private fun storeData( name:String, houseNo: String, city: String, state: String, pinCode: String) {
        val map = hashMapOf<String,Any>()
        map["userName"]=name
        map["houseNo"]=houseNo
        map["city"]=city
        map["state"]=state
        map["pinCode"]=pinCode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","")!!)
            .update(map).addOnSuccessListener {
                Toast.makeText(this,"Profile Updated!!",Toast.LENGTH_SHORT).show()
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