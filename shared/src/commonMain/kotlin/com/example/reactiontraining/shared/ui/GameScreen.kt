package com.example.reactiontraining.shared.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.reactiontraining.shared.domain.model.GameScreenState
import com.example.reactiontraining.shared.domain.model.GameState
import com.example.reactiontraining.shared.ui.theme.ButtonActive
import com.example.reactiontraining.shared.ui.theme.ButtonInactive
import com.example.reactiontraining.shared.ui.theme.ButtonStop
import com.example.reactiontraining.shared.ui.theme.StoplightOff
import com.example.reactiontraining.shared.ui.theme.StoplightOn

@Composable
fun GameScreen(
    state: GameScreenState,
    onPrimaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val phase = state.phase
    val buttonText = GameStrings.buttonLabel(phase)
    val buttonColor = when (phase) {
        GameState.IDLE -> ButtonActive
        GameState.WAIT -> ButtonInactive
        GameState.STARTED -> ButtonStop
    }

    val litCount = when {
        phase == GameState.WAIT && state.litLampIndex in 1..3 -> state.litLampIndex
        else -> 0
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(100.dp))
            Text(
                text = GameStrings.appTitle,
                style = MaterialTheme.typography.headlineLarge,
            )
            Spacer(Modifier.height(32.dp))
            Text(
                text = GameStrings.formatTimerMs(state.elapsedMs),
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(3) { index ->
                    if (index > 0) Spacer(Modifier.width(32.dp))
                    StoplightLamp(
                        isOn = index < litCount,
                    )
                }
            }
        }
        Button(
            onClick = onPrimaryClick,
            modifier = Modifier
                .padding(bottom = 40.dp)
                .width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = Color.White,
            ),
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Composable
private fun StoplightLamp(
    isOn: Boolean,
    modifier: Modifier = Modifier,
) {
    val color = if (isOn) StoplightOn else StoplightOff
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(color),
    )
}
