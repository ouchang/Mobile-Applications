package com.example.calendar

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.temporal.ChronoField
import java.util.*
import kotlin.math.min

class EventEditActivity : AppCompatActivity() {
    lateinit var eventName : EditText
    lateinit var eventDate : TextView
    lateinit var eventTime : TextView
    var hour : Int = -1
    var minute : Int = -1

    var time : LocalTime = LocalTime.now()

    fun initWidgets() {
        eventName = findViewById(R.id.eventNameET)
        eventDate = findViewById(R.id.eventDateTV)
        eventTime = findViewById(R.id.eventTimeTV)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_edit)
        initWidgets()
        eventDate.text = "Date: " + CalendarUtilis.formattedDate(CalendarUtilis.selectedDate)
        eventTime.text = "Time: "

        createNotificationChannel()
    }

    fun saveEventAction(view: View) {
        var eventNameStr : String = eventName.text.toString()

        if(hour != -1 && minute != -1) {
            time = LocalTime.of(hour, minute)
        }
        var selectedDateStr = Event.dateFormatter.format(CalendarUtilis.selectedDate)
        var timeStr = Event.timeFormatter.format(time)
        var newEvent : Event = Event(eventNameStr, selectedDateStr, timeStr)
        EventUtils.eventList.add(newEvent)

        var db = Room.databaseBuilder(
            applicationContext,
            EventDatabase::class.java,
            "event_database"
        ).allowMainThreadQueries().build()
        var eventDao = db.eventDao()

        lifecycleScope.launch {
            eventDao.insertEvent(newEvent)
        }
        scheduleNotification()
        finish()
    }

    fun popTimePicker(view: View) {
        val onTimeSetListener = TimePickerDialog.OnTimeSetListener() { timePicker, selectedHour, selectedMinute ->
            hour = selectedHour
            minute = selectedMinute
            eventTime.text = "Time: " + String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        }

        val timePickerDialog : TimePickerDialog = TimePickerDialog(this, onTimeSetListener, hour, minute, true)
        timePickerDialog.setTitle("Select Time")
        timePickerDialog.show()
    }

    fun createNotificationChannel() {
        val name = "Notification"
        val desc = "Description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun scheduleNotification() {
        val intent = Intent(applicationContext, Notification::class.java)
        val title = eventName.text.toString()
        val message = "Don't forget about your event, sweetheart!"
        //intent.putExtra(titleExtra, title)
        //intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(applicationContext, channelID)
            .setSmallIcon(R.drawable.candy)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(Color.parseColor("#FB9AAC"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        builder.setContentIntent(pendingIntent).setAutoCancel(true)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        with(notificationManager) {
            notify(123, builder.build())
        }

        //val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val timeInMillis = getTime()

        Log.i("notification", "TIME_IN_MILLIS: " + timeInMillis)

        //alarmManager.setExactAndAllowWhileIdle(
          //  AlarmManager.RTC_WAKEUP,
            //timeInMillis,
            //pendingIntent
        //)


        //showAlert(time.getLong(ChronoField.MILLI_OF_SECOND), title, message)
    }

    fun getTime() : Long {
        val day = CalendarUtilis.selectedDate.dayOfMonth
        val month = CalendarUtilis.selectedDate.monthValue
        val year = CalendarUtilis.selectedDate.year

        Log.i("notification", "YEAR: " + year + " MONTH: " + month + " DAY: " + day + " HOUR: " + hour + " MINUTE: " + minute)

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, hour, minute)
        return calendar.timeInMillis
    }
    private fun showAlert(time : Long, title: String, message : String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage("Title: " + title + "\nMessage: " + message + "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

}