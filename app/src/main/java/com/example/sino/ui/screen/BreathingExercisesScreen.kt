package com.example.sino.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun BreathingExercisesScreen(onNavigateBack: () -> Unit) {
    var instruction by remember { mutableStateOf("Inhale") }
    var sweepAngle by remember { mutableStateOf(0f) }

    val animatedSweepAngle by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(durationMillis = 4000)
    )

    val primaryColor = colorScheme.primary

    LaunchedEffect(Unit) {
        repeat(2) {
            instruction = "Inhale"
            sweepAngle = 360f
            delay(4000)

            instruction = "Hold"
            delay(4000)

            instruction = "Exhale"
            sweepAngle = 0f
            delay(4000)

            instruction = "Hold"
            delay(4000)
        }
        onNavigateBack()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawArc(
                color = primaryColor,
                startAngle = -90f,
                sweepAngle = animatedSweepAngle,
                useCenter = false,
                style = Stroke(width = 15.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(text = instruction, style = MaterialTheme.typography.headlineLarge)
    }
}