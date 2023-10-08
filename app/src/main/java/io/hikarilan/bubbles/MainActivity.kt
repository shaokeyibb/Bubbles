package io.hikarilan.bubbles

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import io.hikarilan.bubbles.ui.theme.BubblesTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerChannels()
        with(NotificationManagerCompat.from(this)) {
            if (!areNotificationsEnabled()) {
                grantNotificationPermission()
            }
        }
        showMainNotification()

        setContent {
            BubblesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = getString(R.string.app_name),
                                    style = MaterialTheme.typography.headlineSmall
                                )
                            }
                        )
                    },
                ) {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                            ) {
                                if (NotificationManagerCompat.from(this@MainActivity)
                                        .areNotificationsEnabled()
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.CheckCircle,
                                            contentDescription = getString(R.string.successfully_running_title),
                                            modifier = Modifier.padding(end = 20.dp)
                                        )
                                        Column {
                                            Text(
                                                text = getString(R.string.successfully_running_title),
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Text(
                                                text = getString(R.string.successfully_running_text),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Warning,
                                            contentDescription = getString(R.string.action_required_title),
                                            modifier = Modifier.padding(end = 20.dp)
                                        )
                                        Column {
                                            Text(
                                                text = getString(R.string.action_required_title),
                                                style = MaterialTheme.typography.titleLarge
                                            )
                                            Text(
                                                text = getString(R.string.action_required_text),
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}