package com.dntks.groupstagesimulator.data.model

data class RoundDomainModel(
    val roundId: Int,
    val roundName: String,
    val matches: List<MatchDomainModel>
)
