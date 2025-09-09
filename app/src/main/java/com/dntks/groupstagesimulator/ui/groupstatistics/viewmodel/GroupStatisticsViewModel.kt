package com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dntks.groupstagesimulator.data.db.DefaultTeamsInjector
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupStatisticsViewModel @Inject constructor(
    val teamsInjector: DefaultTeamsInjector,
    repository: GroupStageRepository
) : ViewModel() {

    val teamsFlow = repository.getTeamsWithPlayers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addTeams() {
        viewModelScope.launch {
            teamsInjector.addTeams()
        }
    }

    fun deleteTeams() {
        viewModelScope.launch {
            teamsInjector.deleteTeams()

        }
    }
}