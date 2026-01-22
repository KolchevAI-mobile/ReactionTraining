package com.example.reactiontraining.ui.game

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.reactiontraining.R
import com.example.reactiontraining.data.models.GameState
import com.example.reactiontraining.databinding.FragmentGameBinding

class GameFragment : Fragment(R.layout.fragment_game) {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

    private lateinit var lights: List<android.widget.ImageView>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGameBinding.bind(view)

        lights = listOf(binding.firstLight, binding.secondLight, binding.thirdLight)

        binding.button.setOnClickListener {
            viewModel.handleButtonClick()
        }

        viewModel.gameState.observe(viewLifecycleOwner) { state ->
            updateUIByState(state)
        }

        viewModel.litLampIndex.observe(viewLifecycleOwner) { index ->
            updateLights(index)
        }

        viewModel.currentTime.observe(viewLifecycleOwner) { time ->
            binding.timer.text = "$time ms"
        }
    }

    private fun updateUIByState(state: GameState) {
        when (state) {
            GameState.IDLE -> {
                binding.button.text = getString(R.string.button_begin)
                binding.button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.button_active)
            }
            GameState.WAIT -> {
                binding.button.text = getString(R.string.button_wait)
                binding.button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.button_inactive)
            }
            GameState.STARTED -> {
                binding.button.text = getString(R.string.button_stop)
                binding.button.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.button_stop)
            }
        }
    }

    private fun updateLights(count: Int) {
        val colorOff = ContextCompat.getColor(requireContext(), R.color.stoplightOff)
        val colorOn = ContextCompat.getColor(requireContext(), R.color.stoplightOn)

        lights.forEachIndexed {index, imageView ->
            if (index < count) {
                imageView.setColorFilter(colorOn)
            } else {
                imageView.setColorFilter(colorOff)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}