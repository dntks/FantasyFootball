package com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.GroupStatistics
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import com.dntks.groupstagesimulator.domain.GetGroupStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class GroupStatisticsUiState {
    data class Success(val group: GroupDomainModel) : GroupStatisticsUiState()
    object Error : GroupStatisticsUiState()
    object Loading : GroupStatisticsUiState()
}

@HiltViewModel
class GroupStatisticsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val repository: GroupStageRepository,
    getGroupStatsUseCase: GetGroupStatisticsUseCase
) : ViewModel() {

    private val groupId: Long = checkNotNull(savedStateHandle["groupId"])

    val selectedGroup = repository
        .getGroupWithTeams(groupId)
        .map {
            GroupStatisticsUiState.Success(it)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GroupStatisticsUiState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val groupStatistics = getGroupStatsUseCase
        .getGroupStatistics(groupId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GroupStatistics(mapOf(), listOf())
        )

    fun generateAllMatches(groupId: Long) {
        viewModelScope.launch {
            repository.generateAllMatches(groupId)
        }
    }

    fun deleteRounds(groupId: Long) {
        viewModelScope.launch {
            repository.deleteRoundsForGroup(groupId)

        }
    }

}