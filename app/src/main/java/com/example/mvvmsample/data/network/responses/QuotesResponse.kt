package com.example.mvvmsample.data.network.responses

import com.example.mvvmsample.data.db.entities.Quote

data class QuotesResponse(
    val isSuccesful: Boolean,
    val quotes: List<Quote>

)
