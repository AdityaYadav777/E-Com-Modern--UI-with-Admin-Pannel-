package com.aditya.a_kart.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aditya.a_kart.MainActivity
import com.aditya.a_kart.MyViewModel
import com.aditya.a_kart.R
import com.aditya.a_kart.databinding.ActivityLoginBinding
import com.example.button_morphing.customview.MorphButton
import com.example.button_morphing.customview.MorphButton.*
import es.dmoral.toasty.Toasty

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        setupButton()


       viewModel.authState.observe(this, Observer { isAuthenticated ->
            if (isAuthenticated) {
                Toasty.success(this, "Success!", Toast.LENGTH_SHORT, true).show()
                startActivity(Intent(this, MainActivity::class.java))
                binding.loginBtn.setUIState(MorphButton.UIState.Button)
            } else {
                binding.emailEdt.isEnabled=true
                binding.passEdt.isEnabled=true
                Toasty.error(this, "Check Email or Password", Toast.LENGTH_SHORT, true).show()
                binding.loginBtn.setUIState(MorphButton.UIState.Button)
            }
        })

        binding.loginBtn.setOnClickListener {
            if(binding.emailEdt.text!!.isEmpty() || binding.passEdt.text!!.isEmpty()){
                binding.passEdt.error="Enter Data"
                binding.emailEdt.error="Enter Data"
            }else
            loginProcess()
        }
binding.gotoSignup.setOnClickListener {
    startActivity(Intent(this,SignupActivity::class.java))
}

    }


    private fun loginProcess() {

        binding.loginBtn.setUIState(UIState.Loading)
        binding.emailEdt.isEnabled=false
        binding.passEdt.isEnabled=false

        viewModel.authenticateUsers(
            binding.emailEdt.text.toString(),
            binding.passEdt.text.toString(),
            this,
        )
    }



    private fun setupButton() {
        binding.loginBtn.toBgColor = getColor(R.color.bluish)
        binding.loginBtn.text = "LOGIN"
    }
}