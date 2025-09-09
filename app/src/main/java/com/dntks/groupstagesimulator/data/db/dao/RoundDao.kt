package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseRoundEntity
import com.dntks.groupstagesimulator.data.db.entity.RoundWithMatches
import kotlinx.coroutines.flow.Flow

/**
 * Dao for retrieving and updating [GroupPhaseRoundEntity] from DB
 */
@Dao
interface RoundDao {

    /**
     * Retrieves all rounds with matches for a given group
     */
    @Transaction
    @Query("SELECT * FROM group_phase_round WHERE roundGroupId = :groupId")
    fun getRoundsWithMatches(groupId: Long): Flow<List<RoundWithMatches>>

    /**
     * Upserts a round
     */
    @Transaction
    @Upsert
    suspend fun upsertRound(round: GroupPhaseRoundEntity): Long

    /**
     * Deletes all rounds for a given group
     */
    @Transaction
    @Query("DELETE FROM group_phase_round WHERE roundGroupId = :groupId")
    suspend fun deleteRoundsFromGroup(groupId: Long)
}