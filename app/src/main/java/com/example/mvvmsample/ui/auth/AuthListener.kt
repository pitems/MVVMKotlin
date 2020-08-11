package com.example.mvvmsample.ui.auth

import androidx.lifecycle.LiveData
import com.example.mvvmsample.data.db.entities.User

interface AuthListener {
    //This function will know when to show the progress bar once the button is clicked and a network operation is starting it will take some time to get the data thus a progressbar
    fun onStarted()
    //This will be called if the auntentication is successfull
    fun onSuccess(loginResponse: User)

    fun onFailure(message:String)
}