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
import ltd.aliothstar.blackshoresbox.usecase.KuroApiUseCase
import ltd.aliothstar.blackshoresbox.util.kuroWikiDateTimeToTimestamp

data class HomePageState(
    val currentBanner: CurrentBanner? = null
) : State() {
    data class CurrentBanner(
        val selectBannerIndex: Int = 0,
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
                val starTimestamp: Long,
                val endTimestamp: Long
            )
        }
    }
}

sealed class HomePageIntent : Intent() {
    data class SelectBanner(
        val index: Int
    ) : HomePageIntent()
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
        viewModelScope.launch(IO) {
            loadBanner()
        }
    }

    override fun initializeState(): HomePageState = HomePageState()

    override fun handleIntent(intent: HomePageIntent) {
        when (intent) {
            is HomePageIntent.SelectBanner -> selectBanner(intent.index)
        }
    }

    private fun selectBanner(index: Int) {
        setState {
            copy(
                currentBanner = currentBanner?.copy(
                    selectBannerIndex = index
                )
            )
        }
    }

    private suspend fun loadBanner() {
        kuroApiUseCase
            .getCurrentSideModule()
            .catch {
                emitEffect {
                    HomePageEffect.SnackBarMessage(it.message ?: "Unknown Error")
                }
            }
            .firstOrNull()?.flatMap { sideModule ->
                sideModule.content.flatMap { content ->
                    content.tabs ?: emptyList()
                }
            }?.map {
                HomePageState.CurrentBanner.Banner(
                    title = it.name,
                    countdown = HomePageState.CurrentBanner.Banner.CountDown(
                        startTime = it.countDown.dateRange[0],
                        endTime = it.countDown.dateRange[1],
                        starTimestamp = it.countDown.dateRange[0].kuroWikiDateTimeToTimestamp(),
                        endTimestamp = it.countDown.dateRange[1].kuroWikiDateTimeToTimestamp()
                    ),
                    images = it.imgs.filter { img ->
                        img.img.isNotBlank()
                    }.map { img -> img.img }
                )
            }?.let {
                setState {
                    copy(
                        currentBanner = HomePageState.CurrentBanner(
                            banners = it
                        )
                    )
                }
            }
    }
}