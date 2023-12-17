package com.example.pizzeria.models.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pizzeria.models.data.LocalInfo

class LocalViewModel: ViewModel() {
    var selectedLocal by mutableStateOf<LocalInfo?>(null)
    fun selectLocal(localInfo: LocalInfo) {
        selectedLocal = localInfo
    }
}