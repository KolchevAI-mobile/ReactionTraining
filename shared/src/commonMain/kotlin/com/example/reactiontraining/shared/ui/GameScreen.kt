package com.example.reactiontraining.shared.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.reactiontraining.shared.domain.model.GameScreenState
import com.example.reactiontraining.shared.domain.model.GameState
import com.example.reactiontraining.shared.ui.theme.BrightText
import com.example.reactiontraining.shared.ui.theme.ButtonStart
import com.example.reactiontraining.shared.ui.theme.ButtonStop
import com.example.reactiontraining.shared.ui.theme.ButtonText
import com.example.reactiontraining.shared.ui.theme.ButtonTextDim
import com.example.reactiontraining.shared.ui.theme.ButtonWait
import com.example.reactiontraining.shared.ui.theme.CardBorder
import com.example.reactiontraining.shared.ui.theme.CardSolid
import com.example.reactiontraining.shared.ui.theme.GoGreen
import com.example.reactiontraining.shared.ui.theme.GoPulse
import com.example.reactiontraining.shared.ui.theme.StoplightHousing
import com.example.reactiontraining.shared.ui.theme.StoplightHousingBorder
import com.example.reactiontraining.shared.ui.theme.StoplightOff
import com.example.reactiontraining.shared.ui.theme.StoplightOn
import com.example.reactiontraining.shared.ui.theme.SubtleText
import com.example.reactiontraining.shared.ui.theme.TimerDigit
import com.example.reactiontraining.shared.ui.theme.accentPulseBrush
import com.example.reactiontraining.shared.ui.theme.gameBackgroundBrush

@Composable
fun GameScreen(
    state: GameScreenState,
    onPrimaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val phase = state.phase
    val litCount = when {
        phase == GameState.WAIT && state.litLampIndex in 1..3 -> state.litLampIndex
        else -> 0
    }

    val inf = rememberInfiniteTransition(label = "fx")
    val breath by inf.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = CubicBezierEasing(0.4f, 0f, 0.2f, 1f)),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "breath",
    )
    val sweep by inf.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(7000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "sweep",
    )

    val started = phase == GameState.STARTED
    val bgSweepColors = if (started) {
        listOf(
            Color(0x10FFFFFF),
            Color(0x00FFFFFF),
            Color(0x2A2EE6A0),
        )
    } else {
        listOf(
            Color(0x12FFFFFF),
            Color(0x00FFFFFF),
            Color(0x00FFFFFF),
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gameBackgroundBrush())
            .drawBehind {
                val w = size.width
                val h = size.height
                drawRect(
                    brush = Brush.linearGradient(
                        colors = bgSweepColors,
                        start = Offset(lerp(0f, w, sweep), 0f),
                        end = Offset(lerp(w, 0f, sweep), h),
                    ),
                )
            }
            .then(
                if (started) {
                    Modifier.drawBehind {
                        val r = size.maxDimension * 0.5f
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0x202EE6A0),
                                    Color.Transparent,
                                ),
                                center = this.center,
                                radius = r * breath,
                            ),
                        )
                    }
                } else {
                    Modifier
                },
            ),
    ) {
        Box(Modifier.fillMaxSize().background(accentPulseBrush(sweep * breath)))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(Modifier.height(4.dp))
                AppHeader()
                Spacer(Modifier.height(20.dp))
                StatusCluster(phase, state.litLampIndex)
                Spacer(Modifier.height(20.dp))
                TimerShowcase(phase, state.elapsedMs)
                Spacer(Modifier.height(20.dp))
                StoplightHousing(litCount)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PrimaryAction(phase, onPrimaryClick)
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun AppHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "ARCADE",
            style = MaterialTheme.typography.labelLarge,
            color = SubtleText,
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = GameStrings.appTitle,
            style = MaterialTheme.typography.headlineLarge,
            color = BrightText,
        )
    }
}

@Composable
private fun StatusCluster(phase: GameState, litLampIndex: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AnimatedContent(
            targetState = GameStrings.statusTitle(phase, litLampIndex),
            transitionSpec = {
                (fadeIn(tween(220)) + slideInVertically { it / 6 }) togetherWith
                    (fadeOut(tween(180)) + slideOutVertically { -it / 8 })
            },
            label = "status",
        ) { title ->
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = if (phase == GameState.STARTED) GoGreen else BrightText,
                textAlign = TextAlign.Center,
            )
        }
        AnimatedVisibility(
            visible = phase == GameState.STARTED,
            enter = expandVertically() + fadeIn(spring(dampingRatio = 0.78f, stiffness = 300f)),
            exit = shrinkVertically() + fadeOut(tween(120)),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(GoPulse)
                    .border(1.dp, GoGreen.copy(alpha = 0.4f), RoundedCornerShape(999.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            ) {
                Text(
                    text = "SIGNAL",
                    style = MaterialTheme.typography.labelLarge,
                    color = GoGreen,
                )
            }
        }
    }
}

@Composable
private fun TimerShowcase(phase: GameState, elapsedMs: Long) {
    val timeColor = if (phase == GameState.STARTED) GoGreen else TimerDigit
    val inf = rememberInfiniteTransition(label = "b")
    val borderPulse by inf.animateFloat(
        0.4f, 1f,
        infiniteRepeatable(tween(900, easing = LinearEasing), RepeatMode.Reverse),
        label = "border",
    )
    val borderColor = if (phase == GameState.STARTED) {
        GoGreen.copy(alpha = 0.3f * borderPulse + 0.15f)
    } else {
        CardBorder
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(18.dp, RoundedCornerShape(28.dp), spotColor = Color(0x40000000))
            .clip(RoundedCornerShape(28.dp))
            .background(CardSolid)
            .border(1.5.dp, borderColor, RoundedCornerShape(28.dp))
            .defaultMinSize(minHeight = 120.dp)
            .padding(vertical = 20.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = GameStrings.formatTimerMs(elapsedMs),
                style = MaterialTheme.typography.displayLarge,
                color = timeColor,
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = GameStrings.timerUnit,
                style = MaterialTheme.typography.titleLarge,
                color = timeColor.copy(alpha = 0.75f),
                modifier = Modifier.padding(bottom = 6.dp),
            )
        }
    }
}

@Composable
private fun StoplightHousing(litCount: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "СВЕТОФОР",
            style = MaterialTheme.typography.labelLarge,
            color = SubtleText,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(StoplightHousing)
                .border(1.dp, StoplightHousingBorder, RoundedCornerShape(24.dp))
                .padding(18.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                repeat(3) { index ->
                    StoplightLamp(isOn = index < litCount)
                }
            }
        }
    }
}

@Composable
private fun StoplightLamp(
    isOn: Boolean,
    size: Dp = 64.dp,
) {
    val scale by animateFloatAsState(
        targetValue = if (isOn) 1.1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "lamp",
    )
    val core = if (isOn) StoplightOn else StoplightOff
    Box(
        modifier = Modifier
            .size((size + 20.dp) * 1.05f)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(size * 1.2f)
                .drawBehind {
                    if (isOn) {
                        val r = this.size.maxDimension * 0.5f
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Color(0x50FF3B3B),
                                    Color.Transparent,
                                ),
                                center = center,
                                radius = r,
                            ),
                        )
                    }
                },
        )
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(Brush.linearGradient(listOf(core, core)))
                .then(
                    if (isOn) {
                        Modifier.drawBehind {
                            val s = this.size
                            val gloss = Brush.linearGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.4f),
                                    Color.Transparent,
                                ),
                                start = Offset(0f, 0f),
                                end = Offset(s.width * 0.45f, s.height * 0.5f),
                            )
                            drawCircle(brush = gloss)
                        }
                    } else {
                        Modifier
                    },
                )
                .border(1.dp, Color.White.copy(alpha = if (isOn) 0.3f else 0.1f), CircleShape),
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun PrimaryAction(
    phase: GameState,
    onClick: () -> Unit,
) {
    val container = when (phase) {
        GameState.IDLE -> ButtonStart
        GameState.WAIT -> ButtonWait
        GameState.STARTED -> ButtonStop
    }
    val textColor = if (phase == GameState.WAIT) ButtonTextDim else ButtonText
    val interaction = MutableInteractionSource()
    val pressed by interaction.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 500f),
        label = "press",
    )
    val waitWave = rememberInfiniteTransition(label = "w")
    val flashAlpha by waitWave.animateFloat(
        0.25f,
        0.5f,
        infiniteRepeatable(tween(600, easing = LinearEasing), RepeatMode.Reverse),
        label = "wA",
    )
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .scale(scale)
            .then(
                if (phase == GameState.WAIT) {
                    Modifier.drawBehind {
                        val g = Brush.linearGradient(
                            colors = listOf(
                                Color(0x00FFFFFF),
                                Color.White.copy(alpha = 0.1f * flashAlpha),
                                Color(0x00FFFFFF),
                            ),
                            start = Offset(0f, size.height * 0.5f),
                            end = Offset(size.width, size.height * 0.5f),
                        )
                        drawRect(brush = g)
                    }
                } else {
                    Modifier
                },
            ),
        shape = RoundedCornerShape(18.dp),
        interactionSource = interaction,
        colors = ButtonDefaults.buttonColors(
            containerColor = container,
            contentColor = textColor,
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
        ),
    ) {
        Text(
            text = GameStrings.buttonLabel(phase),
            style = MaterialTheme.typography.titleLarge,
        )
    }
}
