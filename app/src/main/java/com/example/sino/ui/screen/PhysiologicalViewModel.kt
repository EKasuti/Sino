package com.example.sino.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sino.data.PhysiologicalData
import com.example.sino.data.PhysiologicalDataRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class PhysiologicalViewModel(private val repository: PhysiologicalDataRepository) : ViewModel() {

    private val _allData = MutableStateFlow<List<PhysiologicalData>>(emptyList())
    val allData = _allData.asStateFlow()

    private val _currentSecond = MutableStateFlow(0f)
    val currentSecond = _currentSecond.asStateFlow()

    private val _currentDataUpToSecond = MutableStateFlow<List<PhysiologicalData>>(emptyList())
    val currentDataUpToSecond = _currentDataUpToSecond.asStateFlow()

    private val _currentStressScore = MutableStateFlow(0f)
    val currentStressScore = _currentStressScore.asStateFlow()

    private var animationJob: Job? = null

    init {
        viewModelScope.launch {
            repository.loadPhysiologicalData().collect { data ->
                _allData.value = data
                // Auto-start animation once data is loaded
                if (data.isNotEmpty()) {
                    startAutoReplayAnimation()
                }
            }
        }
    }

    fun restartAnimation() {
        if (_allData.value.isNotEmpty()) {
            startAutoReplayAnimation()
        }
    }

    private fun startAutoReplayAnimation() {
        animationJob?.cancel()
        animationJob = viewModelScope.launch {
            while (isActive) { // Infinite loop for auto-replay
                val maxTime = _allData.value.maxOfOrNull { it.tRel } ?: 0f
                
                // Reset to beginning
                var currentTime = 0f
                
                while (currentTime <= maxTime && isActive) {
                    _currentSecond.value = currentTime
                    
                    // Get all data points up to current second
                    val dataUpToNow = _allData.value.filter { it.tRel <= currentTime }
                    _currentDataUpToSecond.value = dataUpToNow
                    
                    // Update current stress score (get the latest value at this time)
                    val latestData = dataUpToNow.lastOrNull()
                    if (latestData != null) {
                        _currentStressScore.value = latestData.stressScore
                    }
                    
                    // Increment by 1 second
                    currentTime += 1f
                    
                    // Wait 300ms before showing next second of data (faster animation)
                    delay(300)
                }
                
                // Brief pause before restarting
                delay(1500)
            }
        }
    }

    companion object {
        fun provideFactory(context: android.content.Context): ViewModelProvider.Factory = 
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PhysiologicalViewModel(PhysiologicalDataRepository(context)) as T
                }
            }
    }
}
