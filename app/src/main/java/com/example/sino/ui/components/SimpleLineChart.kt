package com.example.sino.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sino.data.EdaItem
import com.example.sino.ui.theme.Excellent

@Composable
fun SimpleLineChart(
    data: List<EdaItem>,
    lineColor: Color = Excellent,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) return

    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
        fontSize = 10.sp,
        color = Color.Gray
    )

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            val width = size.width
            val height = size.height
            
            // Reserve space for axes
            val leftPadding = 40f
            val bottomPadding = 30f
            val chartWidth = width - leftPadding - 10f
            val chartHeight = height - bottomPadding - 10f
            
            val minValue = data.minOfOrNull { it.value } ?: 0f
            val maxValue = data.maxOfOrNull { it.value } ?: 1f
            val range = maxValue - minValue
            
            // Sample data if too large
            val sampledData = if (data.size > 500) {
                data.filterIndexed { index, _ -> index % (data.size / 500) == 0 }
            } else {
                data
            }
            
            val xStep = chartWidth / (sampledData.size - 1).coerceAtLeast(1)
            
            // Draw Y-axis
            drawLine(
                color = Color.Gray,
                start = Offset(leftPadding, 0f),
                end = Offset(leftPadding, chartHeight),
                strokeWidth = 2f
            )
            
            // Draw X-axis
            drawLine(
                color = Color.Gray,
                start = Offset(leftPadding, chartHeight),
                end = Offset(leftPadding + chartWidth, chartHeight),
                strokeWidth = 2f
            )
            
            // Draw Y-axis labels and grid lines
            val ySteps = 4
            for (i in 0..ySteps) {
                val y = chartHeight - (i.toFloat() / ySteps * chartHeight)
                val value = minValue + (range * i / ySteps)
                
                // Grid line
                drawLine(
                    color = Color.Gray.copy(alpha = 0.2f),
                    start = Offset(leftPadding, y),
                    end = Offset(leftPadding + chartWidth, y),
                    strokeWidth = 1f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
                )
                
                // Y-axis label
                val text = String.format("%.1f", value)
                val textLayoutResult = textMeasurer.measure(text, textStyle)
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        leftPadding - textLayoutResult.size.width - 8f,
                        y - textLayoutResult.size.height / 2
                    )
                )
            }
            
            // Draw X-axis labels
            val xLabels = listOf("0s", "15s", "30s")
            xLabels.forEachIndexed { index, label ->
                val x = leftPadding + (chartWidth * index / (xLabels.size - 1))
                val textLayoutResult = textMeasurer.measure(label, textStyle)
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x - textLayoutResult.size.width / 2,
                        chartHeight + 8f
                    )
                )
            }
            
            // Draw the data line
            val path = Path()
            sampledData.forEachIndexed { index, item ->
                val x = leftPadding + (index * xStep)
                val y = chartHeight - ((item.value - minValue) / range * chartHeight)
                
                if (index == 0) path.moveTo(x, y)
                else path.lineTo(x, y)
            }
            
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}
