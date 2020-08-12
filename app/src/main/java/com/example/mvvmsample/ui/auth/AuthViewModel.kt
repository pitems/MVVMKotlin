package com.example.mvvmsample.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.repositories.UserRepository
import com.example.mvvmsample.util.ApiException
import com.example.mvvmsample.util.Coroutines
import com.example.mvvmsample.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository
):ViewModel() {
    //These will get the data from the UI
    var name:String?=null
    var email:String ?=null
    var password:String ?=null
    var passwordconfirm:String?=null
    var authListener:AuthListener?=null
    //The parameter view is important because this will be clickable

    fun getUser()= repository.getUser()
    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            //Show Error message on UI
            authListener?.onFailure("Invalid email or password")
            return
        }
        //Remember Coroutines is an object class so it can be called virtually anywhere without the need of instantiating the class
        Coroutines.main {
            try {
                val authResponse= repository.userLogin(email!!,password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e:ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e:NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }
    }

    fun onLogin(view:View){
        Intent(view.context,LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
    fun onSignup(view: View){//This is a click function
        Intent(view.context,SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
    fun onSignupButtonClick(view: View){
        authListener?.onStarted()
        if(name.isNullOrEmpty()){
            authListener?.onFailure("Name is Required")
        }
        if(email.isNullOrEmpty()){
            //Show Error message on UI
            authListener?.onFailure("Email is Required ")
            return
        }
        if(password.isNullOrEmpty()){
            authListener?.onFailure("Please enter a password")
        }

        if(password!=passwordconfirm){

            authListener?.onFailure("password did not match")
        }
        //Remember Coroutines is an object class so it can be called virtually anywhere without the need of instantiating the class
        Coroutines.main {
            try {
                val authResponse= repository.userSignup(name!!,email!!,password!!)
                authResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch (e:ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e:NoInternetException){
                authListener?.onFailure(e.message!!)
            }




        }


    }
}