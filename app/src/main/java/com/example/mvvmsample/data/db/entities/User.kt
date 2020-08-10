package com.example.mvvmsample.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID=0

//This is part of ROOM once MVVM is done i should study ROOM better
@Entity
data class User(
    var id:Int?=null,
    var name:String?=null,
    var email:String?=null,
    var password:String?=null,
    var email_verified_at:String?=null,
    var created_at:String?=null,
    var updated_at:String?=null
){
    //This primary key will not be autogenerated because we will have only one user on this experiment if a new user is added the old user will be
    //deleted and replace by the new authenticated user
    @PrimaryKey(autoGenerate = false)
    var uid:Int= CURRENT_USER_ID
}