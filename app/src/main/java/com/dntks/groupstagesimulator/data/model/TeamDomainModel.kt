package com.dntks.groupstagesimulator.data.model

/**
 * Domain model for a team
 */
data class TeamDomainModel(
    val teamId: Long,
    val name: String,
    val players: List<PlayerDomainModel>
)
