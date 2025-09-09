package com.dntks.groupstagesimulator.data.model

data class RoundDomainModel(
    val roundId: Long,
    val roundName: String,
    val matches: List<MatchDomainModel>
)
