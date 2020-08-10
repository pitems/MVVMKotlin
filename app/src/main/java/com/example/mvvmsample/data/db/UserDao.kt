package com.example.mvvmsample.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvmsample.data.db.entities.CURRENT_USER_ID
import com.example.mvvmsample.data.db.entities.User

@Dao
interface UserDao{
    //This will update and insert data into the database it can be called whatever but here will be a mix between update and insert
    //The long return will give us the row where the object was inserted this will help us know if it was OK
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User):Long

    //This Query will return us a live data of type user when we do a search
    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getUser():LiveData<User>
}