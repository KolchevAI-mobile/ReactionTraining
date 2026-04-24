package com.example.reactiontraining.shared.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = darkColorScheme(
    primary = ButtonStart,
    onPrimary = ButtonText,
    primaryContainer = ButtonWait,
    onPrimaryContainer = ButtonText,
    background = ScreenBackground,
    onBackground = BrightText,
    surface = CardSolid,
    onSurface = BrightText,
    surfaceVariant = StoplightHousing,
    onSurfaceVariant = SubtleText,
    outline = CardBorder,
    error = ButtonStop,
    onError = ButtonText,
    tertiary = AccentAmber,
    onTertiary = Color(0xFF1A0F00),
)

@Composable
fun ReactionTrainingTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = GameTypography,
        content = content,
    )
}
