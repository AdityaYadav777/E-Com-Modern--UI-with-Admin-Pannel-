package com.aditya.a_kart.views

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.aditya.a_kart.MainActivity
import com.aditya.a_kart.R
import com.aditya.a_kart.databinding.ActivityIntroBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class IntroActivity : AppCompatActivity() {
    lateinit var binding:ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val user=Firebase.auth.currentUser
        if (user!=null){
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.nextBtn.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
        changeStatusBarColor()
    }
    private fun changeStatusBarColor() {
        val window = this.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.yellow)
        window?.decorView?.let {
            WindowCompat.getInsetsController(window, it).apply {
                isAppearanceLightStatusBars = true
            }
        }
    }
}