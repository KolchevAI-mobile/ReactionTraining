package com.example.reactiontraining.shared.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Deep game backdrop
val ScreenBackground = Color(0xFF0B0D12)
val ScreenBackgroundMid = Color(0xFF12151F)
val ScreenBackgroundTop = Color(0xFF0E111A)

val CardSolid = Color(0xE6161B26)
val CardBorder = Color(0x3DFFFFFF)
val SubtleText = Color(0xFF8B92A6)
val BrightText = Color(0xFFF0F2F7)
val AccentAmber = Color(0xFFFFB020)
val GoGreen = Color(0xFF2EE6A0)
val GoGreenGlow = Color(0x4D2EE6A0)

val StoplightHousing = Color(0xFF1C212E)
val StoplightHousingBorder = Color(0xFF2A3142)

val StoplightOn = Color(0xFFFF3B3B)
val StoplightOff = Color(0xFF2A0D0D)
val StoplightGlow = Color(0xFFFF5C5C)

val ButtonStart = Color(0xFF3D7CFF)
val ButtonWait = Color(0xFF2A3142)
val ButtonStop = Color(0xFFE62E2E)
val ButtonText = Color(0xFFFFFFFF)
val ButtonTextDim = Color(0xFFADB4C8)

val TimerDigit = Color(0xFFECF0FF)
val GoPulse = Color(0x4D2EE6A0)

fun gameBackgroundBrush(): Brush = Brush.verticalGradient(
    colors = listOf(ScreenBackgroundTop, ScreenBackgroundMid, ScreenBackground),
)

fun accentPulseBrush(phase: Float): Brush = Brush.linearGradient(
    colors = listOf(
        GoGreen.copy(alpha = 0.12f + 0.08f * phase),
        Color.Transparent,
        AccentAmber.copy(alpha = 0.08f * phase),
    ),
    start = Offset(0f, 0f),
    end = Offset(1200f * phase, 900f * (1f - phase)),
)
