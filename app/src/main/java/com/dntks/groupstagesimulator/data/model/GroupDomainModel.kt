package com.dntks.groupstagesimulator.data.model

/**
 * Domain model for groups.
 */
data class GroupDomainModel(
    val name: String,
    val id: Long? = null,
    val teams: List<TeamDomainModel>,
    val rounds: List<RoundDomainModel>,
)