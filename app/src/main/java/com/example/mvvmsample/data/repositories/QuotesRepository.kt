package com.example.mvvmsample.data.repositories

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsample.data.db.AppDatabase
import com.example.mvvmsample.data.db.entities.Quote
import com.example.mvvmsample.data.network.MyApi
import com.example.mvvmsample.data.network.SafeApiRequest
import com.example.mvvmsample.data.preferences.PreferenceProvider
import com.example.mvvmsample.util.Coroutines
import com.example.mvvmsample.util.returnHours
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

private val MINIMUM_INTERVAL = 6

class QuotesRepository (
    private val api : MyApi,
    private val db: AppDatabase,
    private val prefs:PreferenceProvider
):SafeApiRequest(){

    private val quotes = MutableLiveData<List<Quote>>()

    init{
        quotes.observeForever {
            saveQuotes(it)
        }
    }

    //Function for View model
    suspend fun getQuotes():LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuoteDao().getQuote()
        }
    }

    private suspend fun fetchQuotes(){
        val lastSavedAt = prefs.getLastSavedAt()
        if(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                lastSavedAt== null || isFetchNeeded(LocalDateTime.parse(lastSavedAt).toString())
            } else {
                lastSavedAt==null || isFetchNeeded(lastSavedAt)
            }
        ){
            //without the try catch block our safeapi which calls the api which has an interceptor whic call error in case of no internet connection will crash
            //because it throws back an error and the error isn't catched by anything so the try catch is exactly for that, again no internet it will go search for the
            //data inside the database sql
            try{
                val response= apiRequest { api.getQuotes() }
                quotes.postValue(response.quotes)
            }catch (e : Exception){
                e.printStackTrace()
            }

        }
    }


    private fun isFetchNeeded(savedAt: String?):Boolean{
         return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTime = LocalDateTime.parse(savedAt)
            ChronoUnit.HOURS.between(dateTime,LocalDateTime.now()) > MINIMUM_INTERVAL
        } else {
            returnHours(savedAt, MINIMUM_INTERVAL)
        }
    }


    private fun saveQuotes(quotes: List<Quote>){
        Coroutines.io {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                prefs.savelastSavedAt(LocalDateTime.now().toString())

            }else{
                    //Check this one latert TODO
                prefs.savelastSavedAt(Calendar.getInstance().timeInMillis.toString())
            }
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}