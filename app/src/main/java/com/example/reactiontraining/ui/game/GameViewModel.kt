package com.example.reactiontraining.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reactiontraining.shared.domain.engine.ReactionGameEngine
import com.example.reactiontraining.shared.domain.model.GameScreenState
import kotlinx.coroutines.flow.StateFlow

class GameViewModel : ViewModel() {

    private val engine = ReactionGameEngine(viewModelScope)

    val screenState: StateFlow<GameScreenState> = engine.screenState

    fun handleButtonClick() {
        engine.handlePrimaryButtonClick()
    }
}
