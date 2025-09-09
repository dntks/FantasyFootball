package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "team"
)
data class TeamEntity(
    @PrimaryKey(autoGenerate = true) val teamId: Long = 0,
    val name: String
)