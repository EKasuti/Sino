package com.example.sino.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sino.R
import com.example.sino.ui.theme.MetricBlue
import com.example.sino.ui.theme.MetricCyan
import com.example.sino.ui.theme.MetricGreen
import com.example.sino.ui.theme.MetricRed


@Composable
fun MetricCards() {
    Text(
        text = stringResource(R.string.current_metrics),
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
                title = stringResource(R.string.heart_rate),
                value = "73",
                unit = stringResource(R.string.heart_rate_unit),
                valueColor = MetricRed // Red
            )
            MetricCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.heart_rate_variability),
                value = "44",
                unit = stringResource(R.string.heart_rate_variability_unit),
                valueColor = MetricGreen // Green
            )
        }

        // Row 2
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.electrodermal_activity),
                value = "2.0",
                unit = stringResource(R.string.electrodermal_activity_unit),
                valueColor = MetricBlue // Blue
            )
            MetricCard(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.temperature),
                value = "36.8",
                unit = stringResource(R.string.temperature_unit),
                valueColor = MetricCyan // Cyan color
            )
        }
    }
}

@Composable
private fun MetricCard(
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