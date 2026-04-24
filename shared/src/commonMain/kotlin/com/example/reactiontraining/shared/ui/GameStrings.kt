package com.example.reactiontraining.shared.ui

import com.example.reactiontraining.shared.domain.model.GameState

object GameStrings {
    const val appTitle: String = "Reaction Training"

    fun buttonLabel(phase: GameState): String = when (phase) {
        GameState.IDLE -> "Begin"
        GameState.WAIT -> "Waiting"
        GameState.STARTED -> "Stop"
    }

    fun formatTimerMs(ms: Long): String = "$ms ms"
}
