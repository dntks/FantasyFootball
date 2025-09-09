package com.dntks.groupstagesimulator.data.model

data class TeamStatistics(
    val played: Int = 0,
    val win: Int = 0,
    val loss: Int = 0,
    val draw: Int = 0,
    val points: Int = 0,
    val goalsFor: Int = 0,
    val goalsAgainst: Int = 0,
    val goalDifference: Int = 0,
)
data class GroupDomainModel(
    val name: String,
    val id: Long? = null,
    val teams: List<TeamDomainModel>,
    val rounds: List<RoundDomainModel>,
)