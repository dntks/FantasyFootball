package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupStageMatchDao {

    @Query("SELECT * FROM group_phase_match ORDER BY date")
    fun getAllMatches(): Flow<List<GroupPhaseMatchEntity>>

    @Upsert
    suspend fun upsertMatch(match: GroupPhaseMatchEntity)
}