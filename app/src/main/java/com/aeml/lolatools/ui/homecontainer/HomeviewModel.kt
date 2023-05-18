package com.aeml.lolatools.ui.homecontainer

import androidx.lifecycle.ViewModel
import com.aeml.lolatools.ui.chaptercontainer.ChapterUIState
import kotlinx.coroutines.flow.MutableStateFlow

class HomeviewModel: ViewModel() {
    private val _uiState = MutableStateFlow(HomeUIState())
}