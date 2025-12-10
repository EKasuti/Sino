package com.example.sino.data

data class PhysiologicalData(
    val tRel: Float, // Time in seconds (increments of 0.25)
    val edaClean: Float, // EDA clean
    val scl: Float, // Skin Conductance Level
    val scr: Float, // Skin Conductance Response
    val bvpClean: Float, // Blood Volume Pulse clean
    val tempSmooth: Float, // Temperature smooth
    val hrBpm: Float, // Heart Rate in BPM
    val protocolLabel: String, // Protocol label
    val stressScore: Float // Stress score
)
