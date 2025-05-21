package ltd.aliothstar.blackshoresbox.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ltd.aliothstar.blackshoresbox.ui.screen.index.IndexScreen
import ltd.aliothstar.blackshoresbox.ui.screen.index.IndexScreenRoute
import ltd.aliothstar.blackshoresbox.ui.screen.splash.SplashScreen
import ltd.aliothstar.blackshoresbox.ui.screen.splash.SplashScreenRoute
import ltd.aliothstar.blackshoresbox.ui.theme.BlackShoresBoxTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlackShoresBoxTheme {
                val navHostController = rememberNavController()

                MainNavHost(
                    modifier = Modifier
                        .fillMaxSize(),
                    navHostController = navHostController
                )
            }
        }
    }
}

@Composable
private fun MainNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = SplashScreenRoute()
    ) {
        composable<SplashScreenRoute> {
            SplashScreen(
                modifier = Modifier
                    .fillMaxSize(),
                navigateToIndexScreen = {
                    navHostController.navigate(IndexScreenRoute())
                }
            )
        }
        composable<IndexScreenRoute> {
            IndexScreen(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}