package concept.githubfavoriterepo.ui.activity

import androidx.lifecycle.MutableLiveData

/**
 * General State of all ViewModel: Idle, Data or Error
 */
sealed class ViewModelState

object StateIdle : ViewModelState()
object ImagesLoaded : ViewModelState()
object StateProgress : ViewModelState()
data class StateError(val throwable: Throwable) : ViewModelState()

/**
 * ViewModelState LiveData - to init default state value
 */
class StateLiveData(state: ViewModelState = StateIdle) : MutableLiveData<ViewModelState>() {
    init {
        value = state
    }
}

fun ViewModelState.name() = "state = ${this.javaClass.simpleName}"