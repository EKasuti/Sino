package com.example.sino.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sino.ui.components.MultiMetricChart
import com.example.sino.ui.components.SinoTopAppBar
import com.example.sino.ui.theme.*

@Composable
fun VisualizationScreen() {
    val context = LocalContext.current
    val viewModel: PhysiologicalViewModel = viewModel(
        factory = PhysiologicalViewModel.provideFactory(context)
    )
    
    val currentData = viewModel.currentDataUpToSecond.collectAsState().value
    val stressScore = viewModel.currentStressScore.collectAsState().value
    val allData = viewModel.allData.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Top Bar
        SinoTopAppBar(
            screenName = "Visualization"
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        // Stress Score Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Current Stress Score",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = String.format("%.1f", stressScore),
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = getStressColor(stressScore)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Data Status
        if (currentData.isEmpty()) {
            Text(
                text = if (allData.isEmpty()) "Loading data..." else "Starting visualization...",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Text(
                text = "Bio signals",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Charts for each metric using app theme colors
        val metrics = listOf(
            "EDA Clean" to Excellent,
            "SCL" to Relaxed,
            "SCR" to Balanced,
            "BVP Clean" to MetricRed,
            "Temperature" to Tense,
            "Heart Rate" to MetricCyan
        )

        metrics.forEach { (metricName, color) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    MultiMetricChart(
                        data = currentData,
                        metric = metricName,
                        lineColor = color,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun getStressColor(score: Float): Color {
    return when {
        score <= 2.0f -> Relaxed
        score <= 4.0f -> Balanced
        else -> Stressed
    }
}