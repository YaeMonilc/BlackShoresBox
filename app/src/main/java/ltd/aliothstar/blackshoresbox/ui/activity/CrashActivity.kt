package ltd.aliothstar.blackshoresbox.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import ltd.aliothstar.blackshoresbox.APPLICATION_CRASH_VALUE_KEY
import ltd.aliothstar.blackshoresbox.R
import ltd.aliothstar.blackshoresbox.ui.theme.BlackShoresBoxTheme

@AndroidEntryPoint
class CrashActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val crashValue = intent.getStringExtra(APPLICATION_CRASH_VALUE_KEY) ?: "Unknown"

        setContent {
            BlackShoresBoxTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(stringResource(R.string.activity_crash_top_bar_title))
                            }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(9f)
                                .padding(15.dp),
                            value = crashValue,
                            onValueChange = {},
                            textStyle = TextStyle.Default.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 8.sp
                            ),
                            readOnly = true
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(15.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                FilledTonalButton(
                                    modifier = Modifier
                                        .weight(5f),
                                    onClick = {}
                                ) {
                                    Text(stringResource(R.string.activity_crash_button_copy))
                                }
                                Spacer(
                                    modifier = Modifier
                                        .weight(0.5f)
                                )
                                FilledTonalButton(
                                    modifier = Modifier
                                        .weight(5f),
                                    onClick = {
                                        finish()
                                    }
                                ) {
                                    Text(stringResource(R.string.activity_crash_button_exit))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}