package com.example.shoppie.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppie.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var verificationId:String
    private lateinit var dialog :AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button4.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }


        binding.button3.setOnClickListener {
            if(binding.userNumber.text!!.isEmpty()){
                Toast.makeText(this,"Enter Number",Toast.LENGTH_SHORT).show()
            }
            else{
                sendOtp(binding.userNumber.text.toString())
            }
        }

    }

    private fun sendOtp(number: String) {
         auth = FirebaseAuth.getInstance()
        var  builder = AlertDialog.Builder(this)
        builder.setMessage("Please Wait..")
        builder.setTitle("Loading")
        builder.setCancelable(false)
         dialog = builder.create()
        dialog.show()
        val phoneNumber = "+91" + number

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    dialog.dismiss()
                    Toast.makeText(this@LoginActivity,"Please try again",Toast.LENGTH_SHORT).show()


                }

                override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(p0, p1)

                    dialog.dismiss()
                    verificationId = p0
                    val intent = Intent (this@LoginActivity,OTPActivity::class.java)
                    intent.putExtra("verificationId",p0)
                    intent.putExtra("number",binding.userNumber.text.toString())

                    startActivity(intent)

                }
            }).build()
                PhoneAuthProvider.verifyPhoneNumber(options)
    }
}


