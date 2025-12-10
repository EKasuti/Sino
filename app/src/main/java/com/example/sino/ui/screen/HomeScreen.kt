package com.example.sino.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
//import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sino.R
import com.example.sino.ui.components.WellnessScoreCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onVisualizationScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Top Bar
        TopAppBar(
            modifier = Modifier.height(64.dp),
            windowInsets = WindowInsets(0.dp),
            title = { Text(text = stringResource(R.string.app_name)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = colorScheme.background
            ),
            actions = {
                IconButton(onClick = {  }) {
                    Icon(Icons.Outlined.AccountCircle,  contentDescription = stringResource(R.string.profile))
                }
            }
        )

        Spacer(Modifier.height(4.dp))

        // Wellness Score Card
        WellnessScoreCard(
            score = 7.5f,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Current Metrics Header
        Text(
            text = "Current Metrics",
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Metrics Grid
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Heart Rate",
                    value = "73",
                    unit = "bpm",
                    valueColor = Color(0xFFD9534F) // Red
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Heart Rate\nVariability",
                    value = "44",
                    unit = "ms",
                    valueColor = Color(0xFF5CB85C) // Green
                )
            }

            // Row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Electrodermal\nActivity",
                    value = "2.0",
                    unit = "µS",
                    valueColor = Color(0xFF0277BD) // Blue color
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = "Temperature",
                    value = "36.8",
                    unit = "°C",
                    valueColor = Color(0xFF00ACC1) // Cyan color
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Live Visualization Button
        Button(
            onClick = { onVisualizationScreen()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2) 
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

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun MetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    unit: String,
    valueColor: Color
) {
    Card(
        modifier = modifier.aspectRatio(1f),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = typography.bodyMedium,
                color = Color.Gray
            )

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = value,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = valueColor
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = unit,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}
