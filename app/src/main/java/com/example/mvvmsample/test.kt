package com.example.mvvmsample

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mvvmsample.util.returnHours

import kotlinx.coroutines.delay
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
fun main(args: Array<String>) {
   var localdatetime =LocalDateTime.now()
    var datetime = Calendar.getInstance().time
    println(localdatetime)
    println("====")
    val timeStamp: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS").format(Date())
    val timeDate = Calendar.getInstance(TimeZone.getTimeZone("GMT+10"))
    println(datetime)
    println("====")
    val date = System.currentTimeMillis()
    val seconds = date /1000

    val cal1 = Calendar.getInstance()
    cal1.set(2007,Calendar.APRIL,3)
 val cal2 = Calendar.getInstance()
   cal2.set(2007,Calendar.APRIL,4)
 val cal3 = Calendar.getInstance()
 cal3.set(2020,Calendar.AUGUST,14)
 cal3.set(Calendar.HOUR_OF_DAY,4)
 cal3.set(Calendar.MINUTE,30)

 val millis0 = Calendar.getInstance().timeInMillis.toString()
 val millis1 = cal1.timeInMillis
 val millis2 = cal2.timeInMillis
 println("Milli 1 $cal3")
 val diff = cal3.timeInMillis - Calendar.getInstance().timeInMillis
 val diffseconds = diff /1000
 println("Seconds $diffseconds")
 val diffminutes = diff / (60 * 1000)
 println("Minutes $diffminutes")
 val  diffhours = diff / (60 *60 * 1000)
 println("Hours $diffhours")
 println(returnHours(cal3.timeInMillis.toString(),6))
}

fun wasteTime(){
 for (i in 1..3 ){
  println("Wait")
 }
}