package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for storing groups of a tournament
 */
@Entity(
    tableName = "group_phase_group"
)
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) val groupId: Long = 0,
    val groupName: String
)

