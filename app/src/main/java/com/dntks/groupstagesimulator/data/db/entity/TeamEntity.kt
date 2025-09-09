package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for storing teams of a tournament.
 */
@Entity(
    tableName = "team"
)
data class TeamEntity(
    @PrimaryKey(autoGenerate = true) val teamId: Long = 0,
    val name: String,
    val attack: Float,
    val defense: Float,
)