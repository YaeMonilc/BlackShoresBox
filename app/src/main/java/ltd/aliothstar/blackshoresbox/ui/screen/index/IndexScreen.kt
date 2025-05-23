package ltd.aliothstar.blackshoresbox.ui.screen.index

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import ltd.aliothstar.blackshoresbox.R
import ltd.aliothstar.blackshoresbox.ui.screen.index.page.home.HomePage
import ltd.aliothstar.blackshoresbox.ui.screen.index.page.home.HomePageTopBar

@Serializable
class IndexScreenRoute

@Composable
fun IndexScreen(
    modifier: Modifier = Modifier,
    viewModel: IndexScreenViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    val state by viewModel.state

    val snackBarHostState = remember { SnackbarHostState() }

    val pageState = rememberPagerState(
        initialPage = state.page.tag.index,
        pageCount = { PageTag.entries.size }
    )

    fun onSnackBarMessage(message: String) {
        scope.launch {
            snackBarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(state.page.tag) {
        scope.launch {
            pageState.scrollToPage(state.page.tag.index)
        }
    }

    LaunchedEffect(pageState) {
        snapshotFlow { pageState.currentPage }.collect { index ->
            viewModel.emitIntent {
                IndexScreenIntent.ChangePage(PageTag.fromIndex(index))
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .then(modifier),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            )
        },
        topBar = {
            when (state.page.tag) {
                PageTag.HOME -> HomePageTopBar()
                PageTag.PROFILE -> {}
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = state.page.tag == PageTag.HOME,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.screen_index_navigation_bar_label_home)
                        )
                    },
                    onClick = { viewModel.emitIntent { IndexScreenIntent.ChangePage(PageTag.HOME) } }
                )
                NavigationBarItem(
                    selected = state.page.tag == PageTag.PROFILE,
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.screen_index_navigation_bar_label_profile)
                        )
                    },
                    onClick = { viewModel.emitIntent { IndexScreenIntent.ChangePage(PageTag.PROFILE) } }
                )
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            state = pageState,
            contentPadding = paddingValues
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (PageTag.fromIndex(index)) {
                    PageTag.HOME ->
                        HomePage(
                            modifier = Modifier
                                .fillMaxSize(),
                            onSnackBarMessage = { onSnackBarMessage(it) }
                        )
                    PageTag.PROFILE -> {}
                }
            }
        }
    }
}