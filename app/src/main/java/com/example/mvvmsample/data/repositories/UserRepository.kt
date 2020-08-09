package com.example.mvvmsample.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsample.data.network.MyApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {

    fun userLogin(email:String,password:String):LiveData<String>{

        val loginResponse = MutableLiveData<String>()

        //This is a bad practice because this class is now dependant of MyApi this will be changed later with dependency injection
        MyApi().userLogin(email,password).enqueue(object :Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
               loginResponse.value=t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    loginResponse.value=response.body()?.string()
                }else{
                    loginResponse.value=response.errorBody()?.string()
                }
            }

        })

        return loginResponse
    }

}