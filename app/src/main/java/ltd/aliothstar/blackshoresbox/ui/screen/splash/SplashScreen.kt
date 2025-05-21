package ltd.aliothstar.blackshoresbox.ui.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.Serializable
import ltd.aliothstar.blackshoresbox.R

@Serializable
class SplashScreenRoute

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    navigateToIndexScreen: () -> Unit = {},
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when(it) {
                is SplashScreenEffect.NavigateToIndexScreen -> navigateToIndexScreen()
            }
        }
    }

    Scaffold(
        modifier = modifier
            .then(modifier)
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center),
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.xml_icon
                ),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = null
            )
        }
    }
}