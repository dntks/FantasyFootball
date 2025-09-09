package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class RoundWithMatches(
    @Embedded val round: GroupPhaseRoundEntity,
    @Relation(
        parentColumn = "roundId",
        entityColumn = "matchRoundId"
    )
    val matches: List<GroupPhaseMatchEntity>
)