package ltd.aliothstar.blackshoresbox.ui.composable

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ltd.aliothstar.blackshoresbox.R

enum class NetworkState {
    LOADING,
    FAILED,
    SUCCESS
}

@Composable
fun NetworkLoadingPlaceholder(
    modifier: Modifier = Modifier,
    networkState: NetworkState,
    reloadEnable: Boolean = false,
    onReload: () -> Unit = {}
) {
    if (networkState != NetworkState.SUCCESS) {
        Surface(
            modifier = Modifier
                .then(modifier),
            onClick = onReload,
            enabled = reloadEnable
        ) {
            AnimatedContent(
                targetState = networkState,
                transitionSpec = {
                    fadeIn().togetherWith(fadeOut())
                }
            ) {
                when (it) {
                    NetworkState.LOADING -> LoadingPlaceholder()
                    NetworkState.FAILED ->
                        FailedPlaceholder(
                            reloadEnable = reloadEnable
                        )
                    else -> {}
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private fun LoadingPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularWavyProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun FailedPlaceholder(
    reloadEnable: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(35.dp),
            imageVector = Icons.Filled.Error,
            contentDescription = null
        )
        Spacer(
            modifier = Modifier
                .requiredHeight(8.dp)
        )
        Text(
            text = stringResource(R.string.composable_network_loading_placeholder_failed_notice)
        )

        if (reloadEnable) {
            Text(
                text = stringResource(R.string.composable_network_loading_placeholder_failed_reload),
                fontSize = 8.sp
            )
        }
    }
}