package com.example.shoppie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.shoppie.activity.AboutUs
import com.example.shoppie.activity.LoginActivity
import com.example.shoppie.activity.ProfileActivity
import com.example.shoppie.databinding.ActivityLoginBinding
import com.example.shoppie.databinding.ActivityMainBinding
import com.example.shoppie.fragment.CartFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var i=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        if(FirebaseAuth.getInstance().currentUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController= navHostFragment!!.findNavController()

        val popupMenu = PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)

//        navController.addOnDestinationChangedListener(object : NavController.OnDestinationChangedListener{
//            override fun onDestinationChanged(
//                controller: NavController,
//                destination: NavDestination,
//                arguments: Bundle?
//            ) {
//              title =when(destination.id){
//                  R.id.cartFragment->"Cart"
//                  R.id.profileFragment -> "Profile"
//                  else -> "Shoppie"
//              }
//            }
//
//        })

        binding.bottomBar.onItemSelected={
            when(it){
                0->{
                    i=0;
                    navController.navigate(R.id.homeFragment)
                }
                1->{
                    i=1;

                }
                2->i=2
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(i==0){
            finish()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile-> {

                var intent =Intent(this, ProfileActivity::class.java)

                startActivity(intent)
            }
            R.id.About-> {

                var intent =Intent(this, AboutUs::class.java)

                startActivity(intent)
            }
            R.id.logout-> {
                val user = FirebaseAuth.getInstance()
                user.signOut()

                var intent =Intent(this, LoginActivity::class.java)

                startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}