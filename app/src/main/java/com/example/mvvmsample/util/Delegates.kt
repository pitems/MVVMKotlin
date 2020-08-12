package com.example.mvvmsample.util

import kotlinx.coroutines.*

//This will ask for a block of code that will be used under the scope of a lazy start and will be invoke inside a coroutine
fun<T> lazyDeferred(block: suspend CoroutineScope.()-> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}