package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GroupWithRounds(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "roundId"
    )
    val rounds: List<GroupPhaseRoundEntity>
)