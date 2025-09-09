package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.GroupEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupTeamCrossRef
import com.dntks.groupstagesimulator.data.db.entity.GroupWithRoundsAndTeams
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("SELECT * FROM group_phase_group WHERE groupId = :groupId")
    suspend fun getAllRoundsForGroup(groupId: Long): GroupWithRoundsAndTeams
    @Transaction
    @Query("SELECT * FROM group_phase_group WHERE groupId = :groupId")
    fun getAllRoundsForGroupFlow(groupId: Long): Flow<GroupWithRoundsAndTeams>
    @Transaction
    @Query("SELECT * FROM group_phase_group")
    fun getGroups(): Flow<List<GroupWithRoundsAndTeams>>

    @Transaction
    @Upsert
    suspend fun upsertGroup(group: GroupEntity): Long
    @Transaction
    @Insert
    suspend fun upsertGroupTeamCrossRef(groupTeamCrossRefs: List<GroupTeamCrossRef>)

    @Transaction
    @Query("SELECT * FROM group_phase_group WHERE groupId = :groupId")
    fun getGroup(groupId: Long): GroupWithRoundsAndTeams

}
