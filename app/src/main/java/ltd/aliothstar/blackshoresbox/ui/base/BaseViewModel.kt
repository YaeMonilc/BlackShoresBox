package ltd.aliothstar.blackshoresbox.ui.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

open class State

open class Intent

open class Effect

abstract class BaseViewModel<S: State, I: Intent, E: Effect> : ViewModel() {
    abstract fun initializeState(): S

    private val _state = mutableStateOf(initializeState())
    val state: androidx.compose.runtime.State<S> = _state

    protected fun setState(intent: S.() -> S) {
        _state.value = state.value.intent()
    }

    private val _intent by lazy { Channel<I>() }

    abstract fun handleIntent(intent: I)

    fun emitIntent(intent: () -> I) =
        viewModelScope.launch {
            _intent.send(intent())
        }

    private val _effect by lazy { MutableSharedFlow<E>() }
    val effect: SharedFlow<E> by lazy { _effect }

    protected fun emitEffect(effect: () -> E) =
        viewModelScope.launch {
            _effect.emit(effect())
        }

    init {
        viewModelScope.launch {
            _intent.consumeAsFlow().collect {
                handleIntent(it)
            }
        }
    }
}