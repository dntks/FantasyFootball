package com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel

import com.dntks.groupstagesimulator.data.model.GroupDomainModel

/**
 * State for the group statistics screen.
 */
sealed class GroupStatisticsUiState {
    data class Success(val group: GroupDomainModel) : GroupStatisticsUiState()
    object Error : GroupStatisticsUiState()
    object Loading : GroupStatisticsUiState()
}