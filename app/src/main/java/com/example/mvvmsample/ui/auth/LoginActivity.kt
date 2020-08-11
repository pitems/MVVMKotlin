package com.example.mvvmsample.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsample.R
import com.example.mvvmsample.data.db.AppDatabase
import com.example.mvvmsample.data.db.entities.User
import com.example.mvvmsample.data.network.MyApi
import com.example.mvvmsample.data.network.NetworkConnectionInterceptor
import com.example.mvvmsample.data.repositories.UserRepository
import com.example.mvvmsample.databinding.ActivityLoginBinding
import com.example.mvvmsample.ui.home.HomeActivity
import com.example.mvvmsample.util.hide
import com.example.mvvmsample.util.show
import com.example.mvvmsample.util.snackbar
import com.example.mvvmsample.util.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() ,AuthListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Instances required all of this will be changed later to dependency injection !!This should not be used ever
        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
        val api = MyApi(networkConnectionInterceptor)
        val db = AppDatabase(this)
        val repository=UserRepository(api,db)
        val factory= AuthViewModelFactory(repository)
        //ActivityLoginBinding was automatically generated to bind this class to the activity_login
        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        //This is the new way to call a View Model Provider as of now august 2020
        //We give added the factory to the ViewModel so it has the repository injected as a dependency
        val viewModel =ViewModelProvider(this,factory).get(AuthViewModel::class.java)
        binding.viewmodel= viewModel

        viewModel.authListener = this

        viewModel.getUser().observe(this, Observer { user -> if(user!=null){
            Intent(this,HomeActivity::class.java).also {
                it.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        } })

    }

    override fun onStarted() {
        progress_bar.show()
    }
    //Momentary Login probelalkhan@gmail.com    //pass 123456
    override fun onSuccess(user: User) {
        progress_bar.hide()

    }

    override fun onFailure(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)

    }
}