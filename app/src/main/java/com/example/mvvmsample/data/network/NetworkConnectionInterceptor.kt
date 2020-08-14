package com.example.mvvmsample.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mvvmsample.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) :Interceptor{
    private val applicationContext= context.applicationContext
    //The chain will have our request
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
    if(!isInternetAvailable()){
            throw NoInternetException("Make sure you have an active data connection")
    }
        return chain.proceed(chain.request())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetAvailable():Boolean{
      var result :Boolean = false
      val connectivityManager= applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            //Check if we have an active network
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                //we get the network capability object
                result= when{
                    //this is exclusive to the new NetworkCapabilities it will allows us to set a value on our result to check if we have internet
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)-> true
                    else-> false
                }
            }
        }
    return result

    }
}