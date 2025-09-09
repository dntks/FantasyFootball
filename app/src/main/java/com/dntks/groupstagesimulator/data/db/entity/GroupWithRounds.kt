package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class GroupWithRoundsAndTeams(
    @Embedded val group: GroupEntity,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "roundId"
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