package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Cross reference Entity for many-to-many relation between Group and Team.
 */
@Entity(
    tableName = "group_team_cross_ref",
    primaryKeys = ["groupId", "teamId"],          // composite PK prevents duplicates
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["groupId"],
            childColumns = ["groupId"],
            onDelete = ForeignKey.Companion.CASCADE             // delete links when student deleted
        ),
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["teamId"],
            onDelete = ForeignKey.Companion.CASCADE             // delete links when course deleted
        )
    ],
    indices = [Index("groupId"), Index("teamId")]
)
data class GroupTeamCrossRef(
    val groupId: Long,
    val teamId: Long
)