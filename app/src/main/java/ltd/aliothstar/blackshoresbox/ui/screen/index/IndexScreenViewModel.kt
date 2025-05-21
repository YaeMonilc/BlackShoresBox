package ltd.aliothstar.blackshoresbox.ui.screen.index

import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import ltd.aliothstar.blackshoresbox.ui.base.BaseViewModel
import ltd.aliothstar.blackshoresbox.ui.base.Effect
import ltd.aliothstar.blackshoresbox.ui.base.Intent
import ltd.aliothstar.blackshoresbox.ui.base.State

enum class PageTag(
    val index: Int
) {
    HOME(0),
    PROFILE(1);

    companion object {
        fun fromIndex(index: Int): PageTag = entries.first { it.index == index }
    }
}

data class IndexScreenState(
    val page: Page = Page()
) : State() {
    data class Page(
        val tag: PageTag = PageTag.HOME
    )
}

sealed class IndexScreenIntent : Intent() {
    data class ChangePage(
        val tag: PageTag
    ) : IndexScreenIntent()
}

sealed class IndexScreenEffect : Effect()

@HiltViewModel
class IndexScreenViewModel @Inject constructor(

) : BaseViewModel<IndexScreenState, IndexScreenIntent, IndexScreenEffect>() {
    override fun initializeState(): IndexScreenState = IndexScreenState()

    override fun handleIntent(intent: IndexScreenIntent) {
        when (intent) {
            is IndexScreenIntent.ChangePage -> changePage(intent.tag)
        }
    }

    private fun changePage(tag: PageTag) {
        setState {
            copy(
                page = page.copy(
                    tag = tag
                )
            )
        }
    }

}