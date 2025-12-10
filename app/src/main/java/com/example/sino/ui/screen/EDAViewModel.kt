package com.example.sino.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sino.data.EdaItem
import com.example.sino.data.EdaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class EdaViewModel(private val repository: EdaRepository) : ViewModel() {

    private val _edaData = MutableStateFlow<List<EdaItem>>(emptyList())
    val edaData = _edaData.asStateFlow()

    init {
        viewModelScope.launch {
            repository.loadEdaData().collect {
                _edaData.value = it
            }
        }
    }

    companion object {
        fun provideFactory(context: android.content.Context): androidx.lifecycle.ViewModelProvider.Factory = object : androidx.lifecycle.ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return EdaViewModel(EdaRepository(context)) as T
            }
        }
    }
}