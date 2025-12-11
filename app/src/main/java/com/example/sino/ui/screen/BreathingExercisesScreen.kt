package com.example.sino.ui.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.sino.ui.components.SinoTopAppBar
import kotlinx.coroutines.delay

private const val EXERCISE_INTERVAL_S = 4
private const val EXERCISE_INTERVAL_MS = (EXERCISE_INTERVAL_S * 1000).toLong()
private const val COUNTDOWN_INTERVAL_MS = 1000L
private const val GET_READY_DELAY_MS = 2000L
private const val FEEDBACK_DELAY_MS = 2000L

private const val CIRCLE_DIAMETER = 300
private const val CIRCLE_STROKE_WIDTH = 15f
private const val START_ANGLE = -90f
private const val MAX_SWEEP_ANGLE = 360f
private const val MIN_SWEEP_ANGLE = 0f

@Composable
fun BreathingExercisesScreen(
    onNavigateBack: () -> Unit,
    repetitions: Int = 2
) {
    var instruction by remember { mutableStateOf("Get ready...") }
    var countdown by remember { mutableStateOf(0) }
    var sweepAngle by remember { mutableStateOf(MIN_SWEEP_ANGLE) }

    val animatedSweepAngle by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(durationMillis = EXERCISE_INTERVAL_MS.toInt()),
        label = "Breathing Animation"
    )

    val primaryColor = colorScheme.primary

    LaunchedEffect(Unit) {
        delay(GET_READY_DELAY_MS)

        repeat(repetitions) { repetition ->
            instruction = "Breathe in..."
            sweepAngle = MAX_SWEEP_ANGLE
            for (i in EXERCISE_INTERVAL_S downTo 1) {
                countdown = i
                delay(COUNTDOWN_INTERVAL_MS)
            }

            instruction = "Hold"
            for (i in EXERCISE_INTERVAL_S downTo 1) {
                countdown = i
                delay(COUNTDOWN_INTERVAL_MS)
            }

            instruction = "Breathe out..."
            sweepAngle = MIN_SWEEP_ANGLE
            for (i in EXERCISE_INTERVAL_S downTo 1) {
                countdown = i
                delay(COUNTDOWN_INTERVAL_MS)
            }

            if (repetition < repetitions - 1) {
                instruction = "Hold"
                for (i in EXERCISE_INTERVAL_S downTo 1) {
                    countdown = i
                    delay(COUNTDOWN_INTERVAL_MS)
                }
            }
        }

        instruction = "Good job!"
        countdown = 0
        delay(FEEDBACK_DELAY_MS)
        onNavigateBack()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SinoTopAppBar(
            screenName = "Breathing Exercises",
            showBackButton = true,
            onBack = onNavigateBack,
            showBreathingIcon = false
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(CIRCLE_DIAMETER.dp)) {
                drawArc(
                    color = primaryColor,
                    startAngle = START_ANGLE,
                    sweepAngle = animatedSweepAngle,
                    useCenter = false,
                    style = Stroke(
                        width = CIRCLE_STROKE_WIDTH.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite }
            ) {
                Text(
                    text = instruction,
                    style = MaterialTheme.typography.headlineLarge
                )
                if (countdown > 0) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = countdown.toString(),
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
    }
}