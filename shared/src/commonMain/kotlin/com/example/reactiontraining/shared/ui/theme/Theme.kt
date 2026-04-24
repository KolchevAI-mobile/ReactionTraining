package com.example.reactiontraining.shared.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

private val colorScheme = lightColorScheme(
    primary = ButtonActive,
    onPrimary = OnPrimary,
    background = ScreenBackground,
    surface = Color.White,
    onBackground = OnSurface,
    onSurface = OnSurface,
)

@Composable
fun ReactionTrainingTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(colorScheme = colorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            content()
        }
    }
}
