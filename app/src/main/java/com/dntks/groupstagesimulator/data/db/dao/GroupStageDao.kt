package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseRoundEntity
import com.dntks.groupstagesimulator.data.db.entity.PlayerEntity

@Dao
interface RoundDao {

    @Upsert
    suspend fun upsertRound(round: GroupPhaseRoundEntity)
}
@Dao
interface PlayerDao {

    @Upsert
    suspend fun upsertPlayer(player: PlayerEntity)
    @Upsert
    suspend fun upsertPlayers(player: List<PlayerEntity>)

}