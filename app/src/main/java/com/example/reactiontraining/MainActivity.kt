package com.example.reactiontraining

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reactiontraining.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lights: List<android.widget.ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lights = listOf(binding.firstLight, binding.secondLight, binding.thirdLight)

        binding.beginButton.setOnClickListener {
            startTest()
        }
    }

    fun startTest() {
        binding.beginButton.isEnabled = false

        resetLights()

        val randomDelay = (1000..3000).random().toLong()

        binding.root.postDelayed({
            turnOnLights()
            binding.beginButton.isEnabled = true
        }, randomDelay)
    }

    fun resetLights() {
        val colorOff = ContextCompat.getColor(this, R.color.stoplightOff)

        lights.forEach { it.setColorFilter(colorOff) }
    }

    fun turnOnLights() {
        val colorOn = ContextCompat.getColor(this, R.color.stoplightOn)

        lights.forEach { it.setColorFilter(colorOn) }
    }

}