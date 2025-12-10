package com.example.sino.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

class PhysiologicalDataRepository(private val context: Context) {

    fun loadPhysiologicalData(): Flow<List<PhysiologicalData>> = flow {
        emit(readCsv())
    }

    private suspend fun readCsv(): List<PhysiologicalData> =
        withContext(Dispatchers.IO) {
            val result = mutableListOf<PhysiologicalData>()
            val inputStream = context.assets.open("final_df.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))

            // Skip header line
            reader.readLine()

            reader.forEachLine { line ->
                val lineTrimmed = line.trim()
                if (lineTrimmed.isNotEmpty()) {
                    try {
                        val parts = lineTrimmed.split(",")
                        if (parts.size >= 9) {
                            val data = PhysiologicalData(
                                tRel = parts[0].toFloatOrNull() ?: 0f,
                                edaClean = parts[1].toFloatOrNull() ?: 0f,
                                scl = parts[2].toFloatOrNull() ?: 0f,
                                scr = parts[3].toFloatOrNull() ?: 0f,
                                bvpClean = parts[4].toFloatOrNull() ?: 0f,
                                tempSmooth = parts[5].toFloatOrNull() ?: 0f,
                                hrBpm = parts[6].toFloatOrNull() ?: 0f,
                                protocolLabel = parts[7],
                                stressScore = parts[8].toFloatOrNull() ?: 0f
                            )
                            result.add(data)
                        }
                    } catch (e: Exception) {
                        // Skip malformed lines
                    }
                }
            }

            result
        }
}
