package io.hikarilan.bubbles

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat

val CHANNEL_MAIN = ChannelInfo(
    id = "io.hikarilan.bubbles.notification.MAIN",
    rName = R.string.main_channel_name
)

val CHANNEL_NORMAL_BUBBLE = ChannelInfo(
    id = "io.hikarilan.bubbles.notification.NORMAL_BUBBLE",
    rName = R.string.normal_bubble_channel_name,
    importance = NotificationManager.IMPORTANCE_LOW,
    priority = NotificationCompat.PRIORITY_LOW
)

val CHANNEL_IMPORTANT_BUBBLE = ChannelInfo(
    id = "io.hikarilan.bubbles.notification.IMPORTANT_BUBBLE",
    rName = R.string.important_bubble_channel_name,
    importance = NotificationManager.IMPORTANCE_HIGH,
    priority = NotificationCompat.PRIORITY_HIGH
)

fun Context.registerChannels() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

    fun registerChannel(channel: ChannelInfo) {
        val name = getString(channel.rName)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val mChannel = NotificationChannel(channel.id, name, importance)
        channel.rDescription?.let { mChannel.description = getString(it) }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(mChannel)
    }

    registerChannel(CHANNEL_MAIN)
    registerChannel(CHANNEL_NORMAL_BUBBLE)
    registerChannel(CHANNEL_IMPORTANT_BUBBLE)
}

data class ChannelInfo(
    val id: String,
    val rName: Int,
    val rDescription: Int? = null,
    val importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
    val priority: Int = NotificationCompat.PRIORITY_DEFAULT
)