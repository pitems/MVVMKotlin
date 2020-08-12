package com.example.mvvmsample.data.network

import com.example.mvvmsample.data.network.responses.AuthResponse
import okhttp3.OkHttpClient

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email:String,
        @Field("password")password:String
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password")password: String
    ):Response<AuthResponse>

    companion object{
        operator fun invoke( networkConnectionInterceptor: NetworkConnectionInterceptor): MyApi{
            //To not create an instance of networkinterceptor we are adding it to the invoke function so we can create it elsewhere and add the context as well
            val okkHttpClient = OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()

            return Retrofit.Builder().client(okkHttpClient).baseUrl("https://api.simplifiedcoding.in/course-apis/mvvm/")
                .addConverterFactory(GsonConverterFactory.create()).build().create(MyApi::class.java)
        }
    }

    //Check whether we have internet or not

}