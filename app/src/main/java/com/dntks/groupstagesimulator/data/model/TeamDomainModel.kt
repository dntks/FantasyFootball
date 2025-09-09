package com.dntks.groupstagesimulator.data.model

data class TeamDomainModel(
    val teamId: Long,
    val name: String,
    val players: List<PlayerDomainModel>

)
