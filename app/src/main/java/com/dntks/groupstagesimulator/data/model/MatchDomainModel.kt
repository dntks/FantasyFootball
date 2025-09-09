package com.dntks.groupstagesimulator.data.model

/**
 * Domain model for a match
 */
data class MatchDomainModel(
    val homeTeam: TeamDomainModel,
    val awayTeam: TeamDomainModel,
    val homeGoals: Int,
    val awayGoals: Int,
    val time: String,
)
