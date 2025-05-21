package ltd.aliothstar.blackshoresbox.ui.screen.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import ltd.aliothstar.blackshoresbox.repository.UserAuthStateRepository
import ltd.aliothstar.blackshoresbox.ui.base.BaseViewModel
import ltd.aliothstar.blackshoresbox.ui.base.Effect
import ltd.aliothstar.blackshoresbox.ui.base.Intent
import ltd.aliothstar.blackshoresbox.ui.base.State
import ltd.aliothstar.blackshoresbox.usecase.KuroApiUseCase

class SplashScreenState() : State()

sealed class SplashScreenIntent : Intent()

sealed class SplashScreenEffect : Effect() {
    object NavigateToIndexScreen : SplashScreenEffect()
}

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val userAuthStateRepository: UserAuthStateRepository,
    private val kuroApiUseCase: KuroApiUseCase
) : BaseViewModel<SplashScreenState, SplashScreenIntent, SplashScreenEffect>() {

    init {
        viewModelScope.launch(IO) {
            userAuthStateRepository
                .getAuthState()
                .firstOrNull()?.let { userAuthState ->
                    kuroApiUseCase.updateUserAuthState(
                        token = userAuthState.token
                    ).catch{}.collect()
                }
            emitEffect { SplashScreenEffect.NavigateToIndexScreen }
        }
    }

    override fun initializeState(): SplashScreenState = SplashScreenState()

    override fun handleIntent(intent: SplashScreenIntent) {}

}