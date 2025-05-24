package ltd.aliothstar.blackshoresbox.ui.screen.index.page.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ltd.aliothstar.blackshoresbox.ui.base.BaseViewModel
import ltd.aliothstar.blackshoresbox.ui.base.Effect
import ltd.aliothstar.blackshoresbox.ui.base.Intent
import ltd.aliothstar.blackshoresbox.ui.base.State
import ltd.aliothstar.blackshoresbox.ui.composable.NetworkState
import ltd.aliothstar.blackshoresbox.usecase.KuroApiUseCase
import ltd.aliothstar.blackshoresbox.util.kuroWikiDateTimeToTimestamp
import ltd.aliothstar.blackshoresbox.util.timeStampToProcess

data class HomePageState(
    val currentBanner: CurrentBanner = CurrentBanner()
) : State() {
    data class CurrentBanner(
        val networkState: NetworkState = NetworkState.LOADING,
        val selectBannerIndex: Int = 0,
        val data: Data? = null
    ) {
        data class Data(
            val banners: List<Banner>
        ) {
            data class Banner(
                val title: String,
                val countdown: CountDown,
                val images: List<String>
            ) {
                data class CountDown(
                    val startTime: String,
                    val endTime: String,
                    val progress: Float
                )
            }
        }
    }
}

sealed class HomePageIntent : Intent() {
    data class SelectBanner(
        val index: Int
    ) : HomePageIntent()

    object LoadBanners : HomePageIntent()
}

sealed class HomePageEffect : Effect() {
    data class SnackBarMessage(
        val message: String
    ) : HomePageEffect()
}

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val kuroApiUseCase: KuroApiUseCase
) : BaseViewModel<HomePageState, HomePageIntent, HomePageEffect>() {

    init {
        loadBannersWithCoroutines()
    }

    override fun initializeState(): HomePageState = HomePageState()

    override fun handleIntent(intent: HomePageIntent) {
        when (intent) {
            is HomePageIntent.SelectBanner -> selectBanner(intent.index)
            is HomePageIntent.LoadBanners -> loadBannersWithCoroutines()
        }
    }

    private fun selectBanner(index: Int) {
        setState {
            copy(
                currentBanner = currentBanner.copy(
                    selectBannerIndex = index
                )
            )
        }
    }

    private fun loadBannersWithCoroutines() {
        viewModelScope.launch(IO) {
            loadBanners()
        }
    }

    private suspend fun loadBanners() {
        setState {
            copy(
                currentBanner = currentBanner.copy(
                    networkState = NetworkState.LOADING
                )
            )
        }

        kuroApiUseCase
            .getCurrentSideModule()
            .catch {
                emitEffect {
                    HomePageEffect.SnackBarMessage(it.message ?: "Unknown Error")
                }
                setState {
                    copy(
                        currentBanner = currentBanner.copy(
                            networkState = NetworkState.FAILED
                        )
                    )
                }
            }
            .firstOrNull()?.flatMap { sideModule ->
                sideModule.content.flatMap { content ->
                    content.tabs ?: emptyList()
                }
            }?.map {
                val startTime = it.countDown.dateRange[0]
                val endTime = it.countDown.dateRange[1]

                HomePageState.CurrentBanner.Data.Banner(
                    title = it.name,
                    countdown = HomePageState.CurrentBanner.Data.Banner.CountDown(
                        startTime = startTime,
                        endTime = endTime,
                        progress = timeStampToProcess(
                            startTimestamp = startTime.kuroWikiDateTimeToTimestamp(),
                            endTimestamp = endTime.kuroWikiDateTimeToTimestamp()
                        )
                    ),
                    images = it.imgs.filter { img ->
                        img.img.isNotBlank()
                    }.map { img -> img.img }
                )
            }?.let {
                setState {
                    copy(
                        currentBanner = currentBanner.copy(
                            networkState = NetworkState.SUCCESS,
                            data = HomePageState.CurrentBanner.Data(
                                banners = it
                            )
                        )
                    )
                }
            } ?: setState {
                    copy(
                        currentBanner = currentBanner.copy(
                            networkState = NetworkState.FAILED,
                            data = null
                        )
                    )
                }
    }
}