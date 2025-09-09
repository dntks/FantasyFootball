package com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.GroupStatistics
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
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
    private val savedStateHandle: SavedStateHandle,
    val repository: GroupStageRepository
) : ViewModel() {

    val teamsFlow = repository.getTeamsWithPlayers().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val groupId: Long = checkNotNull(savedStateHandle["groupId"])

    //    private val _selectedGroup = MutableStateFlow(GroupStatisticsUiState.Loading)
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
    val groupStatistics = repository
        .getGroupWithTeams(groupId)
        .flatMapLatest { group ->
            repository.getGroupStatistics(group.id?:0, group.teams)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            GroupStatistics(mapOf(), listOf())
        )


    fun addTeams() {
        viewModelScope.launch {
//            teamsInjector.addTeams()
        }
    }

    fun generateAllMatches(groupId: Long) {
        viewModelScope.launch {
            repository.generateAllMatches(groupId)
        }
    }

    fun deleteTeams(groupId: Long) {
        viewModelScope.launch {
            repository.deleteRoundsForGroup(groupId)

        }
    }

    fun getGroupById(groupId: Long) {
//        viewModelScope.launch {
//            _selectedGroup.update { repository.getGroupWithTeams(groupId) }
//
//        }
    }
}