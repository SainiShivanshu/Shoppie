package com.example.shoppie.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppie.R
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject


class CheckoutActivity : AppCompatActivity() , PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val checkout = Checkout()
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime
        checkout.setKeyID("rzp_test_deTONJhwSZzH53")

        try {
            val options = JSONObject()
            options.put("name", "Shoppie")
            options.put("description", "Ecommerce app by Shivanshu")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")

            options.put("theme.color", "#3450a1")
            options.put("currency", "INR")
            options.put("amount", "5000") //pass amount in currency subunits
            options.put("prefill.email", "shivanshusaini216@gmail.com")
            options.put("prefill.contact", "8006357070")

            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this,"Error in starting Razorpay Checkout",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Success",Toast.LENGTH_SHORT).show()
       
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Payment Failed",Toast.LENGTH_SHORT).show()
    }
}