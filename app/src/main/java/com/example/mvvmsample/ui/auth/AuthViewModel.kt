package com.example.mvvmsample.ui.auth

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
    var email:String ?=null
    var password:String ?=null

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
}