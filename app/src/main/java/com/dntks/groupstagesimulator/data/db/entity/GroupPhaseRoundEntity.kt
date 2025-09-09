package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "group_phase_round",
    foreignKeys = [
        ForeignKey(
            entity = GroupEntity::class,
            parentColumns = ["groupId"],
            childColumns = ["roundGroupId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index(value = ["roundGroupId"])]
)
data class GroupPhaseRoundEntity(
    @PrimaryKey(autoGenerate = true) val roundId: Long = 0,
    val roundName: String,
    val roundGroupId: Long,
)
