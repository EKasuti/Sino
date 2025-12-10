package com.example.sino.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sino.data.PhysiologicalData

@Composable
fun MultiMetricChart(
    data: List<PhysiologicalData>,
    metric: String,
    lineColor: Color,
    modifier: Modifier = Modifier
) {
    val primaryColor = lineColor
    
    Column(modifier = modifier) {
        Text(
            text = metric,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        if (data.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Text("No data", style = MaterialTheme.typography.bodySmall)
            }
        } else {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                val width = size.width
                val height = size.height
                val padding = 40f

                // Extract values based on metric
                val values = data.map { item ->
                    when (metric) {
                        "EDA Clean" -> item.edaClean
                        "SCL" -> item.scl
                        "SCR" -> item.scr
                        "BVP Clean" -> item.bvpClean
                        "Temperature" -> item.tempSmooth
                        "Heart Rate" -> item.hrBpm
                        else -> 0f
                    }
                }

                if (values.isEmpty()) return@Canvas

                val minValue = values.minOrNull() ?: 0f
                val maxValue = values.maxOrNull() ?: 1f
                val valueRange = maxValue - minValue
                
                // Avoid division by zero
                val range = if (valueRange == 0f) 1f else valueRange

                // Draw the line
                val path = Path()
                val stepX = (width - 2 * padding) / (data.size - 1).coerceAtLeast(1)

                data.forEachIndexed { index, _ ->
                    val x = padding + index * stepX
                    val normalizedValue = (values[index] - minValue) / range
                    val y = height - padding - (normalizedValue * (height - 2 * padding))

                    if (index == 0) {
                        path.moveTo(x, y)
                    } else {
                        path.lineTo(x, y)
                    }
                }

                drawPath(
                    path = path,
                    color = primaryColor,
                    style = Stroke(width = 3f)
                )

                // Draw min/max labels using native canvas
                val textPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 28f
                    isAntiAlias = true
                }
                
                drawContext.canvas.nativeCanvas.apply {
                    // Max value (top)
                    drawText(
                        String.format("%.2f", maxValue),
                        5f,
                        padding - 5f,
                        textPaint
                    )
                    
                    // Min value (bottom)
                    drawText(
                        String.format("%.2f", minValue),
                        5f,
                        height - 5f,
                        textPaint
                    )
                }
            }
        }
    }
}
