package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.GroupEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseRoundEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupWithRounds

@Dao
interface GroupDao {

    @Query("SELECT * FROM group_phase_group WHERE groupId = :groupId")
    suspend fun getAllRoundsForGroup(groupId: Long): List<GroupWithRounds>

    @Upsert
    suspend fun upsertGroup(group: GroupEntity)
}