package io.hikarilan.bubbles

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

const val KEY_TEXT_BLOWING = "key_text_blowing"

private var notificationId = 2

fun Activity.grantNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            1
        )
    } else {
        Toast.makeText(
            this,
            getString(R.string.require_notification_permission_toast),
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent().apply {
            action = "android.settings.APP_NOTIFICATION_SETTINGS"
            putExtra("app_package", packageName)
            putExtra("app_uid", applicationInfo.uid)
            putExtra("android.provider.extra.APP_PACKAGE", packageName)
        }
        startActivity(intent)
    }
}

@SuppressLint("MissingPermission")
fun Context.showMainNotification() {

    val replyLabel: String = resources.getString(R.string.blowing_label)
    val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_BLOWING).run {
        setLabel(replyLabel)
        build()
    }

    val blowingPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            Intent(applicationContext, BubbleBroadcastReceiver::class.java).apply {
                action = "io.hikarilan.bubbles.intent.action.BLOWING_BUBBLE"
                putExtra("notificationId", notificationId)
            },
            PendingIntent.FLAG_MUTABLE
        )
    notificationId++

    val blowingAction: NotificationCompat.Action =
        NotificationCompat.Action.Builder(
            R.drawable.ic_launcher_foreground,
            getString(R.string.blowing_bubbles), blowingPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

    val notification = NotificationCompat.Builder(this, CHANNEL_MAIN.id)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(getString(R.string.main_notification_title))
        .setContentText(getString(R.string.main_notification_text))
        .setPriority(CHANNEL_MAIN.priority)
        .setAutoCancel(false)
        .setOngoing(true)
        .setOnlyAlertOnce(true)
        .addAction(blowingAction)

    with(NotificationManagerCompat.from(this)) {
        notify(1, notification.build())
    }
}