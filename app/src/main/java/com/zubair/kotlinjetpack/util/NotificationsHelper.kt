package com.zubair.kotlinjetpack.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zubair.kotlinjetpack.R
import com.zubair.kotlinjetpack.view.MainActivity

class NotificationsHelper(val context: Context){

    //multiple channels like products, updates, sales etc
    private val CHANNEL_ID = "dogs channel id"
    private val NOTIFICATION_ID = 123

    fun createNotification(){
        createNotificationChannel()
        val intent = Intent(context, MainActivity::class.java).apply {
            //This flag will either create a new task or clear the existing task that already exists
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        //What happens when the user clicks on the icon
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.ic_dog_breed)
        val notificationStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(icon)
            .bigLargeIcon(null)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_dog_breed_icon)
            .setLargeIcon(icon)
            .setContentTitle("Dog Retrieved")
            .setContentText("This notification has some content")
            .setStyle(notificationStyle)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)

    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = CHANNEL_ID
            val descriptionText = "channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

}