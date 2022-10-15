package com.example.shoppie.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.shoppie.MainActivity
import com.example.shoppie.R
import com.example.shoppie.databinding.ActivityLoginBinding
import com.example.shoppie.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.button3.setOnClickListener { 
            if(binding.userOTP.text!!.isEmpty()){
                Toast.makeText(this,"Please provide OTP",Toast.LENGTH_SHORT).show()
            }
            else{
                verifyUser(binding.userOTP.text.toString())
            }
            
        }
    }

    private fun verifyUser(otp: String) {
        val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!,otp)

        var auth = FirebaseAuth.getInstance()
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading....")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        auth.signInWithCredential(credential).addOnCompleteListener {

            if(it.isSuccessful){
                builder.dismiss()


                val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                val editor = preferences.edit()

                editor.putString("number", intent.getStringExtra("number"))

                editor.apply()


                startActivity(Intent(this,MainActivity::class.java))
                finishAffinity()

            }
            else{
                builder.dismiss()
                Toast.makeText(this,"Error ${it.exception}",Toast.LENGTH_SHORT).show()

            }
        }
    }


}