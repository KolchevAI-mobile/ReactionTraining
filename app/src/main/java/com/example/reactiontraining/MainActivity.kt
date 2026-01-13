package com.example.reactiontraining

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reactiontraining.databinding.ActivityMainBinding
import kotlinx.coroutines.Runnable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lights: List<android.widget.ImageView>
    private var currentState: GameState = GameState.IDLE
    private var startTime: Long = 0
    private var endTime: Long = 0
    private val handler = android.os.Handler(android.os.Looper.getMainLooper())
    private var runnable1: Runnable? = null
    private var runnable2: Runnable? = null
    private var runnable3: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lights = listOf(binding.firstLight, binding.secondLight, binding.thirdLight)

        binding.button.setOnClickListener {
            handleButtonClick()
        }
    }

    fun resetLights() {
        val colorOff = ContextCompat.getColor(this, R.color.stoplightOff)

        lights.forEach { it.setColorFilter(colorOff) }
    }

    fun handleButtonClick() {
        when (currentState) {
            GameState.IDLE -> {
                currentState = GameState.WAIT
                binding.button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.button_inactive)
                binding.button.text = ContextCompat.getString(this, R.string.button_wait)
                startTrafficLightSequence()
            }

            GameState.WAIT -> {
                resetGame()
                binding.button.text = ContextCompat.getString(this, R.string.button_false_start)
            }

            GameState.STARTED -> {
                endTime = System.currentTimeMillis()
                val result = endTime - startTime
                binding.timer.text = "$result ms"

                currentState = GameState.IDLE
                binding.button.text = ContextCompat.getString(this, R.string.button_begin)
                binding.button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.button_active)
            }

            else -> {}
        }
    }

    fun startTrafficLightSequence() {
        resetLights()
        val randomDelay = (1000..3000).random().toLong()
        val colorOn = ContextCompat.getColor(this, R.color.stoplightOn)

        binding.firstLight.setColorFilter(colorOn)

        runnable1 = Runnable {
            binding.secondLight.setColorFilter(colorOn)
        }
        handler.postDelayed(runnable1!!, 500)

        runnable2 = Runnable {
            binding.thirdLight.setColorFilter(colorOn)
        }
        handler.postDelayed(runnable2!!, 1000)

        runnable3 = Runnable {
            resetLights()
            currentState = GameState.STARTED
            startTime = System.currentTimeMillis()
        }
        handler.postDelayed(runnable3!!, 1000 + randomDelay)
    }

    fun resetGame() {
        runnable1?.let { handler.removeCallbacks(it) }
        runnable2?.let { handler.removeCallbacks(it) }
        runnable3?.let { handler.removeCallbacks(it) }

        resetLights()
        currentState = GameState.IDLE

        binding.button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.button_active)
        binding.button.text = ContextCompat.getString(this, R.string.button_false_start)
    }

}