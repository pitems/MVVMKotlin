package com.example.mvvmsample.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmsample.data.db.AppDatabase
import com.example.mvvmsample.data.db.entities.Quote
import com.example.mvvmsample.data.network.MyApi
import com.example.mvvmsample.data.network.SafeApiRequest
import com.example.mvvmsample.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuotesRepository (
    private val api : MyApi,
    private val db: AppDatabase
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

    private fun isFetchNeeded():Boolean{
        return true
    }

    private suspend fun fetchQuotes(){
        if(isFetchNeeded()){
            val response= apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun saveQuotes(quotes: List<Quote>){
        Coroutines.io {
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}