package com.example.mvvmsample.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Coroutines{
    //OK What the hell is this well this function takes a work that will be a suspend method and the CoroutineScope will choose which
    //thread to use to work this suspend function we have (Min,IO,Unconfined,Default) each of this threads won't do anything to the UI thread where
    //our application reside so this function will return us the suspend work we did effectivly executing it on another thread
    fun main(work:suspend()->Unit) = CoroutineScope(Dispatchers.Main).launch {
        work()
    }

}