package com.example.mvvmsample.util

import java.text.SimpleDateFormat
import java.util.*

//Check Difference on Calendar.getinstance.timeinmillis
//This will give me the option to check whether 8 hours or whatever time has passed
fun returnHours(timestamp:String?,timepass:Int):Boolean{
    val timestamplong = timestamp?.toLong()
    val now = Calendar.getInstance().timeInMillis
    val diff = timestamplong?.minus(now)
    val  diffhours = diff?.div((60 *60 * 1000))
    println(diffhours)
    var value:Boolean=false
    if (diffhours != null) {
        if (diffhours>=timepass){
            value=true
        }
    }
    return value
}