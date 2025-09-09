package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Helper class to handle one-to-many relation between Team and Player.
 */
data class TeamWithPlayers(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "playerTeamId"
    )
    val players: List<PlayerEntity>
)