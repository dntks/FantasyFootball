package com.dntks.groupstagesimulator.data.model

/**
 * Domain model for a round
 */
data class RoundDomainModel(
    val roundId: Long,
    val roundName: String,
    val matches: List<MatchDomainModel>
)
