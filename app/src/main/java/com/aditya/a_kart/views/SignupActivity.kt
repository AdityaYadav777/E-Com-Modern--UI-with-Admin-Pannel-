package com.aditya.a_kart.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.aditya.a_kart.MyViewModel
import com.aditya.a_kart.R
import com.aditya.a_kart.databinding.ActivitySignupBinding
import com.example.button_morphing.customview.MorphButton
import es.dmoral.toasty.Toasty

class SignupActivity : AppCompatActivity() {
    lateinit var viewModel:MyViewModel
    lateinit var binding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

      initBtn()

viewModel=ViewModelProvider(this)[MyViewModel::class.java]

        viewModel.authState.observe(this){
            if (it){
                Toasty.success(this,"Account Created",Toasty.LENGTH_SHORT).show()
                binding.signupBtn.setUIState(MorphButton.UIState.Button)
                startActivity(Intent(this,LoginActivity::class.java))
            }else{
                Toasty.error(this,"Failed",Toasty.LENGTH_SHORT).show()
                binding.nameEdt.isEnabled=true
                binding.passEdt .isEnabled=true
                binding.emailEdt.isEnabled=true
                binding.signupBtn.setUIState(MorphButton.UIState.Button)
            }
        }

        binding.signupBtn.setOnClickListener {

            if (binding.emailEdt.text!!.isEmpty()||binding.passEdt.text!!.isEmpty()||binding.nameEdt.text!!.isEmpty()){
                Toasty.error(this,"Enter Data Correctly",Toasty.LENGTH_SHORT).show()
            }else
                createUserProcess()

        }


    }

    private fun createUserProcess() {
        binding.signupBtn.setUIState(MorphButton.UIState.Loading)
        binding.nameEdt.isEnabled=false
        binding.passEdt .isEnabled=false
        binding.emailEdt.isEnabled=false
        viewModel.createUsers(binding.nameEdt.text.toString(),binding.emailEdt.text.toString(),binding.passEdt.text.toString(),this)

    }

    private fun initBtn() {
        binding.signupBtn.toBgColor=getColor(R.color.bluish)
        binding.signupBtn.text="SignUp"
    }
}