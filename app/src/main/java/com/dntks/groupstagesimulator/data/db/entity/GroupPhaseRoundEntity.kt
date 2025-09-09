package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "group_phase_round"
)
data class GroupPhaseRoundEntity(
    @PrimaryKey(autoGenerate = true) val roundId: Int = 0,
    val roundName: String
)