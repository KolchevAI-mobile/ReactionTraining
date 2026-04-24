package com.example.reactiontraining.shared.ui

import com.example.reactiontraining.shared.domain.model.GameState

object GameStrings {
    const val appTitle: String = "REACTION"

    fun buttonLabel(phase: GameState): String = when (phase) {
        GameState.IDLE -> "Старт"
        GameState.WAIT -> "Сброс"
        GameState.STARTED -> "Стоп"
    }

    fun statusTitle(phase: GameState, litLampIndex: Int): String = when (phase) {
        GameState.IDLE -> "Готовы?"
        GameState.WAIT -> when (litLampIndex) {
            1 -> "Раз…"
            2 -> "Два…"
            3 -> "Скоро сигнал"
            else -> "Сосредоточьтесь"
        }
        GameState.STARTED -> "СЕЙЧАС!"
    }

    fun formatTimerMs(ms: Long): String = "$ms"

    const val timerUnit: String = "мс"
}
