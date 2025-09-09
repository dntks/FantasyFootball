package com.dntks.groupstagesimulator.domain

import com.dntks.groupstagesimulator.data.model.RoundDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel

/**
 * Storing derived statistics for a group.
 */
data class GroupStatistics(
    val teamStatistics: Map<TeamDomainModel, TeamStatistics>,
    val roundStatistics: List<RoundDomainModel>
)