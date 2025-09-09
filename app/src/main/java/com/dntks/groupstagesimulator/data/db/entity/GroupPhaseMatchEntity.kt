package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entity for storing a match of a tournament.
 * Referencing teams and round.
 */
@Entity(
    tableName = "group_phase_match",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["awayTeamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["homeTeamId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GroupPhaseRoundEntity::class,
            parentColumns = ["roundId"],
            childColumns = ["matchRoundId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["awayTeamId"]), Index(value = ["homeTeamId"]), Index(
        value = ["matchRoundId"]
    )]
)
data class GroupPhaseMatchEntity(
    @PrimaryKey(autoGenerate = true) val matchId: Int = 0,
    val homeTeamId: Long,
    val awayTeamId: Long,
    val matchRoundId: Long,
    val homeGoals: Int,
    val awayGoals: Int,
    val date: String?,
    val time: String?,
    val isPlayed: Boolean
)

