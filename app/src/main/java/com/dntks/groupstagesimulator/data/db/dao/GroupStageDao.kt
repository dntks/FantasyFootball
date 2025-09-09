package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Transaction
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.PlayerEntity

/**
 * Dao for retrieving and updating [PlayerEntity] from DB
 */
@Dao
interface PlayerDao {

    /**
     * Upserts a player
     */
    @Transaction
    @Upsert
    suspend fun upsertPlayer(player: PlayerEntity)

    /**
     * Upserts a list of players
     */
    @Transaction
    @Upsert
    suspend fun upsertPlayers(player: List<PlayerEntity>)

}