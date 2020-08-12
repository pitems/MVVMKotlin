package com.example.mvvmsample.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.repositories.QuotesRepository
import com.example.mvvmsample.util.lazyDeferred

class QuotesViewModel(
    repository: QuotesRepository
): ViewModel() {
    //initialise only when needed, create a custom lazy routine using coroutine -> can be found in util
    val quotes by lazyDeferred{
        repository.getQuotes()
    }
}