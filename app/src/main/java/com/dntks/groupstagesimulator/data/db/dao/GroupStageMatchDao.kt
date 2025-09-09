package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupStageMatchDao {

    @Transaction
    @Query("SELECT * FROM group_phase_match")
    fun getAllMatches(): Flow<List<GroupPhaseMatchEntity>>

    @Transaction
    @Query("SELECT * FROM group_phase_match WHERE matchRoundId = :roundId")
    suspend fun getAllMatchesForRound(roundId: Long): List<GroupPhaseMatchEntity>

    @Transaction
    @Upsert
    suspend fun upsertMatch(match: GroupPhaseMatchEntity)
}