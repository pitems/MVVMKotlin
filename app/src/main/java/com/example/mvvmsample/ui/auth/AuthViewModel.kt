package com.example.mvvmsample.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.repositories.UserRepository

class AuthViewModel:ViewModel() {
    //These will get the data from the UI
    var email:String ?=null
    var password:String ?=null

    var authListener:AuthListener?=null
    //The parameter view is important because this will be clickable
    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            //Show Error message on UI
            authListener?.onFailure("Invalid email or password")
            return
        }
                //TRhis is a LiveData and a bad practice we should not create instance of other classes inside another class
            val loginResponse = UserRepository().userLogin(email!!,password!!)
        authListener?.onSuccess(loginResponse)
        //Success
        authListener?.onSuccess(loginResponse)
    }
}