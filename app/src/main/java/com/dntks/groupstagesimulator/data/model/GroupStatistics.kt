package com.dntks.groupstagesimulator.data.model

data class GroupStatistics(
    val teamStatistics: Map<TeamDomainModel, TeamStatistics>,
    val roundStatistics: List<RoundDomainModel>
)