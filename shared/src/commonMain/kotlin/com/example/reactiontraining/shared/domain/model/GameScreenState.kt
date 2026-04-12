package com.example.reactiontraining.shared.domain.model

data class GameScreenState(
    val phase: GameState,
    val litLampIndex: Int,
    val elapsedMs: Long,
) {
    companion object {
        val Initial = GameScreenState(
            phase = GameState.IDLE,
            litLampIndex = -1,
            elapsedMs = 0L,
        )
    }
}
