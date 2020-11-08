package com.github.gotify.connector

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import java.util.concurrent.ThreadLocalRandom

/**
 * This service is a default service to receive
 * notifications from gotify (once registered)
 */
var defaultServiceName = "com.github.gotify.connector.DefaultServiceNotification"

class DefaultServiceNotification : Service() {
    /** For showing and hiding our notification.  */
    private var gNM: NotificationManager? = null

    private var channelId = "gotifyChannelID"

    /**
     * Handler of incoming messages from clients.
     */
    internal inner class gHandler : GotifyServiceHandler(this) {
        override fun onMessage(msg: Message) {
            // In this sample, we'll use the same text for the ticker and the expanded notification
            val text = msg.data?.getString("message")!!
            var title = msg.data?.getString("title")!!
            if (title.isBlank()) {
                title = applicationInfo.name
            }
            var priority = msg.data!!.getInt("priority")
            sendNotification(title,text,priority)
        }
    }

    private fun sendNotification(title: String,text: String, priority: Int){
        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this.applicationContext, channelId)
        } else {
            Notification.Builder(this.applicationContext)
        }

        val notification =
                notificationBuilder.setSmallIcon(applicationInfo.icon) // the status icon
                        .setTicker(text) // the status text
                        .setWhen(System.currentTimeMillis()) // the time stamp
                        .setContentTitle(title) // the label of the entry
                        .setContentText(text) // the contents of the entry
                        .setPriority(priority)
                        .build()

        gNM!!.notify(ThreadLocalRandom.current().nextInt(), notification)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && channelId.isNotEmpty()) {
            val name = packageName
            val descriptionText = "gotify"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Target we publish for clients to send messages to gHandler.
     */
    private val gMessenger = Messenger(gHandler())

    /**
     * When binding to the service, we return an interface to our messenger
     * for sending messages to the service.
     */
    override fun onBind(intent: Intent): IBinder? {
        return gMessenger.binder
    }

    override fun onCreate() {
        channelId = packageName
        createNotificationChannel()
        gNM = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}
