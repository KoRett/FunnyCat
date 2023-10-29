package com.korett.funnycat.model

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.korett.funnycat.R
import com.korett.funnycat.presentation.MainActivity
import kotlin.random.Random


private const val CHANNEL_ID = "funny_cat_chanel"

class NotificationWorker(
    val context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {

    @SuppressLint("RestrictedApi")
    override fun doWork(): Result {
        val textTitle = "Funny Cat"
        val textContent =
            "Hi, dear friend! You haven't logged into the app for a long time :c \nCome in soon, the cats are bored!"

        val id = Random.nextInt()

        val resultIntent = Intent(context, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
            context, 0, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_happy_cat)
            .setContentTitle(textTitle)
            .setContentText(textContent)
            .setContentIntent(resultPendingIntent)
            .setStyle(NotificationCompat.BigTextStyle().bigText(textContent))
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = createNotificationManager()

        notificationManager.notify(id, builder.build())

        return Result.Success()
    }

    private fun createNotificationManager(): NotificationManager {
        val name = "Funny cat"
        val descriptionText = "Notification chanel of Funny cat."

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            (context as Application).getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        return notificationManager
    }

}