package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.PlayerEntity

@Dao
interface PlayerDao {

    @Transaction
    @Upsert
    suspend fun upsertPlayer(player: PlayerEntity)
    @Transaction
    @Upsert
    suspend fun upsertPlayers(player: List<PlayerEntity>)

}