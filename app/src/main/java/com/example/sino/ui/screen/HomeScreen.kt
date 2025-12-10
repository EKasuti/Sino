package com.example.sino.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sino.R
import com.example.sino.ui.components.MetricCards
import com.example.sino.ui.components.SinoTopAppBar
import com.example.sino.ui.components.WellnessScoreCard

@Composable
fun HomeScreen(
    onVisualizationScreen: () -> Unit,
    onBreathingExercisesScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        SinoTopAppBar(
            screenName = stringResource(R.string.app_name)
        )

        WellnessScoreCard(score = 7.5f)

        MetricCards()

        Spacer(modifier = Modifier.height(32.dp))

        NavigationButton(
            text = "Live Visualization",
            icon = Icons.Outlined.Person,
            onClick = onVisualizationScreen
        )

        Spacer(modifier = Modifier.height(16.dp))

        NavigationButton(
            text = "Breathing Exercises",
            icon = Icons.Outlined.Spa,
            onClick = onBreathingExercisesScreen
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun NavigationButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}