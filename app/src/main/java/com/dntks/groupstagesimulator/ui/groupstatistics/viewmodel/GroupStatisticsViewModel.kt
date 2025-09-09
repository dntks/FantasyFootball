package com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dntks.groupstagesimulator.domain.GroupStatistics
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import com.dntks.groupstagesimulator.domain.GetGroupStatisticsUseCase
import com.dntks.groupstagesimulator.domain.MatchGeneratorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for the group statistics screen.
 */
@HiltViewModel
class GroupStatisticsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val repository: GroupStageRepository,
    getGroupStatsUseCase: GetGroupStatisticsUseCase,
    val matchGeneratorUseCase: MatchGeneratorUseCase
) : ViewModel() {

    /**
     * the id of the group to display the statistics for
     */
    private val groupId: Long = checkNotNull(savedStateHandle["groupId"])

    /**
     * the group to display the statistics for
     */
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

    /**
     * Flow for the group statistics
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val groupStatistics = getGroupStatsUseCase
        .getGroupStatistics(groupId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GroupStatistics(mapOf(), listOf())
        )

    /**
     * Simulating the matches for the group
     */
    fun generateAllMatches(groupId: Long) {
        viewModelScope.launch {
            matchGeneratorUseCase.generateAllMatches(groupId)
        }
    }

    /**
     * Delete all rounds for the group
     */
    fun deleteRounds(groupId: Long) {
        viewModelScope.launch {
            repository.deleteRoundsForGroup(groupId)

        }
    }

}