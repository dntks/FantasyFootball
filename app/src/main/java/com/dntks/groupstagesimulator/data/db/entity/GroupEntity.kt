package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "group_phase_group"
)
data class GroupEntity(
    @PrimaryKey(autoGenerate = true) val groupId: Int = 0,
    val groupName: String
)