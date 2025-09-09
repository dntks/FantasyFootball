package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Helper class to handle one-to-many relation between Round and Match.
 */
data class RoundWithMatches(
    @Embedded val round: GroupPhaseRoundEntity,
    @Relation(
        parentColumn = "roundId",
        entityColumn = "matchRoundId"
    )
    val matches: List<GroupPhaseMatchEntity>
)