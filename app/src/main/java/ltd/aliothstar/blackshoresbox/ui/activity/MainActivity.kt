package ltd.aliothstar.blackshoresbox.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import ltd.aliothstar.blackshoresbox.network.KuroApi
import ltd.aliothstar.blackshoresbox.network.KuroApiInterface
import ltd.aliothstar.blackshoresbox.ui.theme.BlackShoresBoxTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    @KuroApi
    lateinit var kuroApi: KuroApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlackShoresBoxTheme {

            }
        }
    }
}