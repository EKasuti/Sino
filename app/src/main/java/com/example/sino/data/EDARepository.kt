package com.example.sino.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class EdaRepository(private val context: Context) {

    // Public Flow for UI and ViewModel
    fun loadEdaData(): Flow<List<EdaItem>> = flow {
        emit(readCsv())
    }

    private suspend fun readCsv(): List<EdaItem> =
        withContext(Dispatchers.IO) {

            val result = mutableListOf<EdaItem>()
            val inputStream = context.assets.open("EDA.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // Line 1: Start Time (yyyy-MM-dd HH:mm:ss)
            val startTimeString = reader.readLine()
            // Line 2: Sample Rate (Hz)
            val sampleRateString = reader.readLine()
            // Line 3: Offset/Metadata (?) - usually 0.0, skip
            reader.readLine()

            // Parse Start Time
            // Example: 2013-06-12 17:26:25
            // If parsing fails, default to current time or 0
            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.US)
            var currentTimestamp = try {
                dateFormat.parse(startTimeString)?.time ?: 0L
            } catch (e: Exception) {
                0L
            }

            // Parse Sample Rate
            val sampleRate = sampleRateString?.trim()?.toFloatOrNull() ?: 4.0f
            val sampleIntervalMs = if (sampleRate > 0) (1000 / sampleRate).toLong() else 250L

            reader.forEachLine { line ->
                val lineTrimmed = line.trim()
                if (lineTrimmed.isNotEmpty()) {
                    val value = lineTrimmed.toFloatOrNull() ?: 0f
                    result.add(EdaItem(value, currentTimestamp))
                    currentTimestamp += sampleIntervalMs
                }
            }

            result
        }
}
