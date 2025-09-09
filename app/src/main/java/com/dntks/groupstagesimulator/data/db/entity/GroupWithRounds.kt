package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

/**
 * Helper class to handle one-to-many relation between Round and Team.
 */
data class GroupWithRoundsAndTeams(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "roundGroupId"
    )
    val rounds: List<GroupPhaseRoundEntity>,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "teamId",
        associateBy = Junction(
            value = GroupTeamCrossRef::class,
            parentColumn = "groupId",
            entityColumn = "teamId"
        )
    )
    val teams: List<TeamEntity>
)