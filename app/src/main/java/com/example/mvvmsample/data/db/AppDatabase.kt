package com.example.mvvmsample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvmsample.data.db.entities.Quote
import com.example.mvvmsample.data.db.entities.User

//Here we can define as many entities as we want
@Database(entities = [User::class, Quote::class],version = 1)
abstract class AppDatabase :RoomDatabase() {
    abstract fun getUserDao():UserDao
    abstract fun getQuoteDao():QuoteDao
    //https://kotlinlang.org/docs/tutorials/kotlin-for-py/objects-and-companion-objects.html check this if i forgot
    companion object{

        @Volatile //volatile means this variable is inmediatly visible to all other threads on the application
        private var instance:AppDatabase?=null

        //This val will be used so we don't create to instances of the database
        private val LOCK=Any()

        //This function is the one that will create the database , it will check if LOCK is not null if it's not null it will not be created again
        //This invoke essentially means that when we call for example AppDatabse() it will automatically do ONE THING and that's it creating an instance
        //of the database into our application
        operator fun invoke(context:Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }
        //This one here will create our database using the application context , this same class and the name of our database it can be whatever and then it
        //builds the database
         private fun buildDatabase(context: Context) = Room.databaseBuilder(
             context.applicationContext,
             AppDatabase::class.java,
             "MVVM.db"
         ).build()
    }
}