package com.example.reactiontraining.ui.game

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reactiontraining.data.models.GameState
import kotlinx.coroutines.Runnable

class GameViewModel : ViewModel() {
    var currentState: GameState = GameState.IDLE
    var startTime: Long = 0L

    private val handler = Handler(Looper.getMainLooper())

    private val _gameState = MutableLiveData<GameState>(GameState.IDLE)
    val gameState: LiveData<GameState> = _gameState

    private val _litLampIndex = MutableLiveData<Int>(-1)
    val litLampIndex: LiveData<Int> = _litLampIndex

    private var runnable1: Runnable? = null
    private var runnable2: Runnable? = null
    private var runnable3: Runnable? = null
    private var timerRunnable: Runnable? = null

    private val _currentTime = MutableLiveData<Long>(0L)
    val currentTime: LiveData<Long> = _currentTime

    fun startTrafficLightSequence() {
        _litLampIndex.value = 0

        _litLampIndex.value = 1

        runnable1 = Runnable {
            _litLampIndex.value = 2
        }
        handler.postDelayed(runnable1!!, 500)

        runnable2 = Runnable {
            _litLampIndex.value = 3
        }
        handler.postDelayed(runnable2!!, 1000)

        val randomDelay = (1000..3000).random().toLong()

        runnable3 = Runnable {
            _litLampIndex.value = -1
            startTime = System.currentTimeMillis()
            updateState(GameState.STARTED)
            startTimeTicker()
        }
        handler.postDelayed(runnable3!!, 1000 + randomDelay)
    }

    private fun startTimeTicker() {
        timerRunnable = object : Runnable {
            override fun run() {
                if (gameState.value == GameState.STARTED) {
                    _currentTime.value = System.currentTimeMillis() - startTime
                    handler.postDelayed(this, 10)
                }
            }
        }
        handler.post(timerRunnable!!)
    }

    fun stopAllProcesses(clearTimer: Boolean) {
        handler.removeCallbacksAndMessages(null)
        _litLampIndex.value = -1
        if (clearTimer) {
            _currentTime.value = 0L
        }
    }

    fun updateState(state: GameState) {
        _gameState.value = state
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }

    fun handleButtonClick() {
        when (gameState.value) {
            GameState.IDLE -> {
                _currentTime.value = 0L
                updateState(GameState.WAIT)
                startTrafficLightSequence()
            }

            GameState.WAIT -> {
                stopAllProcesses(clearTimer = true)
                updateState(GameState.IDLE)
            }

            GameState.STARTED -> {
                stopAllProcesses(clearTimer = false)
                updateState(GameState.IDLE)
            }
        }
    }
}