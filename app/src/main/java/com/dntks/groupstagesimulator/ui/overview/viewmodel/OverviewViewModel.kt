package com.dntks.groupstagesimulator.ui.overview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the Overview screen.
 */
@HiltViewModel
class OverviewViewModel @Inject constructor(
    val repository: GroupStageRepository
): ViewModel() {
    val groupsFlow = repository.getGroupsWithTeams().stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        emptyList()
    )

}