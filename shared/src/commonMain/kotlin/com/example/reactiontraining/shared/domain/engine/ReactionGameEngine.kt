package com.example.reactiontraining.shared.domain.engine

import com.example.reactiontraining.shared.domain.model.GameScreenState
import com.example.reactiontraining.shared.domain.model.GameState
import com.example.reactiontraining.shared.platform.epochMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ReactionGameEngine(
    private val scope: CoroutineScope,
    private val trafficLightScheduler: TrafficLightScheduler = DefaultTrafficLightScheduler,
) {
    private val _screenState = MutableStateFlow(GameScreenState.Initial)
    val screenState: StateFlow<GameScreenState> = _screenState.asStateFlow()

    private var trafficJob: Job? = null
    private var tickerJob: Job? = null
    private var reactionStartEpochMs: Long = 0L

    fun handlePrimaryButtonClick() {
        when (_screenState.value.phase) {
            GameState.IDLE -> startFromIdle()
            GameState.WAIT -> cancelAndReturnToIdle(clearElapsed = true)
            GameState.STARTED -> cancelAndReturnToIdle(clearElapsed = false)
        }
    }

    private fun startFromIdle() {
        stopTrafficAndTicker()
        _screenState.value = GameScreenState(
            phase = GameState.WAIT,
            litLampIndex = -1,
            elapsedMs = 0L,
        )

        trafficJob = scope.launch {
            _screenState.update { it.copy(litLampIndex = 1) }
            delay(500)
            if (!isActive) return@launch
            _screenState.update { it.copy(litLampIndex = 2) }
            delay(500)
            if (!isActive) return@launch
            _screenState.update { it.copy(litLampIndex = 3) }
            delay(trafficLightScheduler.reactionWindowDelayMs())
            if (!isActive) return@launch

            reactionStartEpochMs = epochMillis()
            _screenState.update {
                it.copy(
                    phase = GameState.STARTED,
                    litLampIndex = -1,
                )
            }
            startTicker()
        }
    }

    private fun startTicker() {
        tickerJob?.cancel()
        tickerJob = scope.launch {
            while (isActive && _screenState.value.phase == GameState.STARTED) {
                val elapsed = epochMillis() - reactionStartEpochMs
                _screenState.update { it.copy(elapsedMs = elapsed) }
                delay(10)
            }
        }
    }

    private fun cancelAndReturnToIdle(clearElapsed: Boolean) {
        stopTrafficAndTicker()
        val elapsed = if (clearElapsed) 0L else _screenState.value.elapsedMs
        _screenState.value = GameScreenState(
            phase = GameState.IDLE,
            litLampIndex = -1,
            elapsedMs = elapsed,
        )
    }

    private fun stopTrafficAndTicker() {
        trafficJob?.cancel()
        trafficJob = null
        tickerJob?.cancel()
        tickerJob = null
    }
}
