package com.example.mvvmsample.data.network

import com.example.mvvmsample.util.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest{
    //Invoke will get any Response when called like this example  apiRequest{MyApi.UserLogin.etc} in this case The api is sent to the call variable
    //and then the method because is asynchronus does the rest of the job checking whether it is susccessfull or not
    suspend fun <T:Any> apiRequest(call:suspend()->Response<T>):T{
        //Here ApiRequest will call the interface MyApi from here
        val response= call.invoke()
        if(response.isSuccessful){
            return response.body()!!
        }else{ //This is part of the Error
            val error = response.errorBody()?.string()
            val message=StringBuilder()
            error?.let{
                try {
                    //Extract the value of the error Body message and append it to the String Builder
                    message.append(JSONObject(it).getString("message"))
                }catch (e:JSONException){ }
                message.append("\n")
            }

            message.append("Error Code: ${response.code()}")

            throw ApiException(message.toString())
        }
    }
}