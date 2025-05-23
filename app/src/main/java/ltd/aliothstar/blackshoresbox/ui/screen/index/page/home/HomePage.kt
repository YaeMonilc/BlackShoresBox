package ltd.aliothstar.blackshoresbox.ui.screen.index.page.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ltd.aliothstar.blackshoresbox.R
import ltd.aliothstar.blackshoresbox.ui.composable.OutlinedCardAreaContent
import ltd.aliothstar.blackshoresbox.util.timeStampToProcess

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = Modifier
            .then(modifier),
        title = {
            Text(
                text = stringResource(R.string.screen_index_page_home_top_bar_title)
            )
        }
    )
}

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onSnackBarMessage: (String) -> Unit = {},
    viewModel: HomePageViewModel = hiltViewModel()
) {
    val state by viewModel.state

    LaunchedEffect(Unit) {
        viewModel.effect.collect {
            when (it) {
                is HomePageEffect.SnackBarMessage -> onSnackBarMessage(it.message)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(
                horizontal = 15.dp
            )
            .then(modifier)
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth(),
            visible = state.currentBanner != null &&
                    state.currentBanner!!.banners.isNotEmpty(),
            enter = fadeIn()
        ) {
            state.currentBanner?.let {
                CurrentBannerContent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    currentBanner = it,
                    onTabSelect = { index ->
                        viewModel.emitIntent { HomePageIntent.SelectBanner(index) }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun CurrentBannerContent(
    modifier: Modifier = Modifier,
    currentBanner: HomePageState.CurrentBanner,
    onTabSelect: (Int) -> Unit = {}
) {
    OutlinedCardAreaContent(
        modifier = Modifier
            .then(modifier),
        title = {
            Text(
                text = stringResource(R.string.screen_index_page_home_current_banner_title)
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                PrimaryScrollableTabRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    selectedTabIndex = currentBanner.selectBannerIndex,
                    edgePadding = 0.dp
                ) {
                    currentBanner.banners.forEachIndexed { index, item ->
                        Tab(
                            selected = index == currentBanner.selectBannerIndex,
                            onClick = { onTabSelect(index) },
                            text = {
                                Text(
                                    text = item.title
                                )
                            }
                        )
                    }
                }
                currentBanner.banners.getOrNull(currentBanner.selectBannerIndex)
                    ?.let { banner ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(2f)
                                    .fillMaxHeight()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                ProvideTextStyle(
                                    value = TextStyle.Default.copy(
                                        fontSize = 11.sp
                                    )
                                ) {
                                    Text(
                                        text = stringResource(R.string.screen_index_page_home_current_banner_start_time)
                                    )
                                    Text(banner.countdown.startTime)
                                    Text(
                                        text = stringResource(R.string.screen_index_page_home_current_banner_end_time)
                                    )
                                    Text(banner.countdown.endTime)
                                    Text(
                                        text = stringResource(R.string.screen_index_page_home_current_banner_progress)
                                    )
                                    LinearWavyProgressIndicator(
                                        progress = {
                                            timeStampToProcess(
                                                startTimestamp = banner.countdown.starTimestamp,
                                                endTimestamp = banner.countdown.endTimestamp
                                            )
                                        }
                                    )
                                }
                            }
                            VerticalDivider(
                                modifier = Modifier
                                    .fillMaxHeight()
                            )
                            Row(
                                modifier = Modifier
                                    .weight(3f)
                                    .fillMaxHeight()
                                    .padding(10.dp)
                            ) {
                                AsyncImage(
                                    modifier = Modifier
                                        .weight(2f)
                                        .fillMaxHeight(),
                                    model = banner.images.getOrNull(0),
                                    contentDescription = null
                                )
                                banner.images.subList(1, banner.images.size).let { subList ->
                                    if (subList.isNotEmpty()) {
                                        Spacer(
                                            modifier = Modifier
                                                .requiredWidth(10.dp)
                                        )
                                        LazyHorizontalGrid(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight(),
                                            rows = GridCells.Fixed(3)
                                        ) {
                                            items(
                                                items = subList
                                            ) {
                                                AsyncImage(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .height((150 / subList.size).dp),
                                                    model = it,
                                                    contentDescription = null
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
    )
}