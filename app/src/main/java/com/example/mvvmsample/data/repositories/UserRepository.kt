package com.example.mvvmsample.data.repositories

import com.example.mvvmsample.data.db.AppDatabase
import com.example.mvvmsample.data.db.entities.User
import com.example.mvvmsample.data.network.MyApi
import com.example.mvvmsample.data.network.SafeApiRequest
import com.example.mvvmsample.data.network.responses.AuthResponse
import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val db:AppDatabase
):SafeApiRequest() {

    suspend fun userLogin(email:String,password:String):AuthResponse{

        return apiRequest {api.userLogin(email,password) }

    }

    suspend fun userSignup(
        name:String,
        email: String,
    password: String
    ):AuthResponse{
        return apiRequest { api.userSignup(name,email,password) }
    }

    suspend fun saveUser(user:User)= db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getUser()



}