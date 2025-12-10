package com.example.sino.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sino.ui.components.SimpleLineChart
import com.example.sino.ui.components.SinoTopAppBar

@Composable
fun VisualizationScreen() {
    val context = LocalContext.current
    val viewModel: EdaViewModel = viewModel(
        factory = EdaViewModel.provideFactory(context)
    )
    val edaData = viewModel.edaData.collectAsState(initial = emptyList()).value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Top Bar
        SinoTopAppBar(
            screenName = "Visualization Screen"
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        // Title
        Text(
            text = "EDA Signal",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Chart
        if (edaData.isEmpty()) {
            Text("Loading data...")
        } else {
            SimpleLineChart(
                data = edaData,
                lineColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}