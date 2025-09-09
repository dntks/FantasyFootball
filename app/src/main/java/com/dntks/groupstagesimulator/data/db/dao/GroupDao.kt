package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.GroupEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupTeamCrossRef
import com.dntks.groupstagesimulator.data.db.entity.GroupWithRoundsAndTeams
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupDao {

    @Query("SELECT * FROM group_phase_group WHERE groupId = :groupId")
    fun getAllRoundsForGroup(groupId: Long): Flow<List<GroupWithRoundsAndTeams>>
    @Query("SELECT * FROM group_phase_group")
    fun getGroups(): Flow<List<GroupWithRoundsAndTeams>>

    @Upsert
    suspend fun upsertGroup(group: GroupEntity): Long
    @Insert
    suspend fun upsertGroupTeamCrossRef(groupTeamCrossRefs: List<GroupTeamCrossRef>)

}
