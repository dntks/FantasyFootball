package com.dntks.groupstagesimulator.data.model

data class TeamDomainModel(
    val teamId: Int,
    val name: String,
    val players: List<PlayerDomainModel>

)
