package com.dntks.groupstagesimulator.data.model

data class MatchDomainModel(
    val homeTeam: TeamDomainModel,
    val awayTeam: TeamDomainModel,
    val homeGoals: Int,
    val awayGoals: Int,
    val date: String,
    val time: String
)
