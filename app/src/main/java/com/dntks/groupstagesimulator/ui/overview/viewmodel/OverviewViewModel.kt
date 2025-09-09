package com.dntks.groupstagesimulator.ui.overview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dntks.groupstagesimulator.data.db.DefaultTeamsInjector
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    val repository: GroupStageRepository,
    val teamsInjector: DefaultTeamsInjector,
): ViewModel() {

    val teamsFlow = repository.getTeams().stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        emptyList()
    )
    val groupsFlow = repository.getGroupsWithTeams().stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        emptyList()
    )

    fun addGroup(group: GroupDomainModel){
        viewModelScope.launch {
            repository.addGroupWithTeams(group)
        }
    }

    fun addTeams(){
        viewModelScope.launch {
            teamsInjector.addTeams()
        }
    }

}