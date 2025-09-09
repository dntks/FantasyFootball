package com.dntks.groupstagesimulator.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "player",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["teamId"],
            childColumns = ["playerTeamId"],
            onDelete = ForeignKey.Companion.SET_NULL
        )
    ],
    indices = [Index(value = ["playerTeamId"])]
)
data class PlayerEntity(
    @PrimaryKey(autoGenerate = true) val playerId: Int = 0,
    val name: String,
    val position: Position,
    val playerTeamId: Long? = null
)