package io.hikarilan.bubbles

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import kotlin.random.Random

class BubbleBroadcastReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        RemoteInput.getResultsFromIntent(intent)?.getCharSequence(KEY_TEXT_BLOWING)?.let {
            val blowingNotification = NotificationCompat.Builder(context, CHANNEL_NORMAL_BUBBLE.id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(it)
                .build()

            context.showMainNotification()

            with(NotificationManagerCompat.from(context)) {
                notify(intent.getIntExtra("notificationId", Random.nextInt()), blowingNotification)
            }
        }
    }
}