package com.example.sino.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sino.R
import com.example.sino.ui.components.SinoTopAppBar
import com.example.sino.ui.components.WellnessScoreCard
import com.example.sino.ui.theme.*

@Composable
fun HomeScreen(
    onVisualizationScreen: () -> Unit,
    onBreathingExercisesScreen: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: PhysiologicalViewModel = viewModel(
        factory = PhysiologicalViewModel.provideFactory(context)
    )

    val currentData = viewModel.currentDataUpToSecond.collectAsState().value
    val stressScore = viewModel.currentStressScore.collectAsState().value

    // Get the latest values from current data
    val latestData = currentData.lastOrNull()
    val heartRate = latestData?.hrBpm ?: 0f
    val temperature = latestData?.tempSmooth ?: 0f
    val eda = latestData?.edaClean ?: 0f
    val bvp = latestData?.bvpClean ?: 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        SinoTopAppBar(
            screenName = stringResource(R.string.app_name),
            onBreathingExercisesClick = onBreathingExercisesScreen
        )

        WellnessScoreCard(score = stressScore)

        // Live Metrics Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.live_metrics),
                style = typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            FilledTonalButton(
                onClick = { onVisualizationScreen() },
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.MonitorHeart,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.visualize),
                    style = typography.titleMedium
                )
            }
        }

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LiveMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Heart Rate",
                    value = String.format("%.0f", heartRate),
                    unit = "bpm",
                    valueColor = MetricRed
                )
                LiveMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Temperature",
                    value = String.format("%.1f", temperature),
                    unit = "°C",
                    valueColor = MetricCyan
                )
            }

            // Row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LiveMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "EDA",
                    value = String.format("%.2f", eda),
                    unit = "μS",
                    valueColor = MetricBlue
                )
                LiveMetricCard(
                    modifier = Modifier.weight(1f),
                    title = "BVP",
                    value = String.format("%.1f", bvp),
                    unit = "au",
                    valueColor = MetricGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
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
            containerColor = colorScheme.secondary
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
            style = typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun LiveMetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    unit: String,
    valueColor: Color
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = typography.bodyMedium,
                color = Color.Gray,
                maxLines = 2
            )

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = value,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = valueColor,
                    lineHeight = 36.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = unit,
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}