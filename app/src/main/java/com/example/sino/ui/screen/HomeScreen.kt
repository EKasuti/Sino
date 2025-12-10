package com.example.sino.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.sino.ui.components.MetricCards
import com.example.sino.ui.components.SinoTopAppBar
import com.example.sino.ui.components.WellnessScoreCard

@OptIn(ExperimentalMaterial3Api::class)
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
        SinoTopAppBar()

        WellnessScoreCard(score = 7.5f)

        MetricCards()

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onVisualizationScreen()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Live Visualization",
                style = typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onBreathingExercisesScreen() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.secondary
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Spa,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Breathing Exercises",
                style = typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}