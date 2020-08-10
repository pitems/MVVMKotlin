package com.example.mvvmsample.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsample.R
import com.example.mvvmsample.databinding.ActivityLoginBinding
import com.example.mvvmsample.util.hide
import com.example.mvvmsample.util.show
import com.example.mvvmsample.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() ,AuthListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ActivityLoginBinding was automatically generated to bind this class to the activity_login
        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        //This is the new way to call a View Model Provider as of now august 2020
        val viewModel =ViewModelProvider(this).get(AuthViewModel::class.java)
        binding.viewmodel= viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        progress_bar.show()
    }
    //Momentary Login probelalkhan@gmail.com    //pass 123456
    override fun onSuccess(loginResponse: LiveData<String>) {

        loginResponse.observe(this, Observer {
            progress_bar.hide()
            toast(it)
        })
    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        toast(message)
    }
}