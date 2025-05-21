package ltd.aliothstar.blackshoresbox.ui.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class State

open class Intent

open class Effect

abstract class BaseViewModel<S: State, I: Intent, E: Effect> : ViewModel() {
    abstract fun initializeState(): S

    init {
        viewModelScope.launch {
            intent.collect {
                handleIntent(it)
            }
        }
    }

    private val _state = mutableStateOf(initializeState())
    val state: androidx.compose.runtime.State<S> = _state

    protected fun setState(intent: S.() -> S) {
        _state.value = state.value.intent()
    }

    private val _intent by lazy { MutableSharedFlow<I>() }
    private val intent: SharedFlow<I> by lazy { _intent }

    abstract fun handleIntent(intent: I)

    fun emitIntent(intent: () -> I) = _intent.tryEmit(intent())

    private val _effect by lazy { Channel<E>() }
    val effect: SendChannel<E> by lazy { _effect }

    protected fun emitEffect(effect: () -> E) = _effect.trySend(effect())
}