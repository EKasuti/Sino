package com.example.sino.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sino.R
import com.example.sino.ui.theme.Balanced
import com.example.sino.ui.theme.Excellent
import com.example.sino.ui.theme.Relaxed
import com.example.sino.ui.theme.Stressed
import com.example.sino.ui.theme.Tense

@Composable
fun WellnessScoreCard(score: Float) {
    val progress = (score / 10f).coerceIn(0f, 1f)

    // Determine state and color based on score ranges
    val (state, color) = when {
        score < 3f -> stringResource(R.string.stressed) to Stressed
        score < 5f -> stringResource(R.string.tense) to Tense
        score < 7f -> stringResource(R.string.balanced) to Balanced
        score < 9f -> stringResource(R.string.relaxed) to Relaxed
        else -> stringResource(R.string.excellent) to Excellent
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.1f)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Background circle (track)
        CircularProgressIndicator(
            progress = { 1f },
            strokeWidth = 24.dp,
            modifier = Modifier.fillMaxSize(0.8f),
            color = colorScheme.surfaceVariant,
            trackColor = Color.Transparent
        )

        // Foreground circle (actual progress)
        CircularProgressIndicator(
            progress = { progress },
            strokeWidth = 24.dp,
            modifier = Modifier.fillMaxSize(0.8f),
            color = color,
            trackColor = Color.Transparent
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = score.toString(),
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = state,
                style = MaterialTheme.typography.titleMedium,
                color = color
            )
        }
    }
}