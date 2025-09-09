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

/**
 * Dao for retrieving and updating [GroupEntity] from DB
 */
@Dao
interface GroupDao {

    /**
     * Retrieves latest group with round and team data for a given group id
     */
    @Transaction
    @Query("SELECT * FROM group_phase_group WHERE groupId = :groupId")
    fun getAllRoundsForGroupFlow(groupId: Long): Flow<GroupWithRoundsAndTeams>

    /**
     * Retrieves all latest groups with round and team data
     */
    @Transaction
    @Query("SELECT * FROM group_phase_group")
    fun getGroups(): Flow<List<GroupWithRoundsAndTeams>>

    /**
     * Upserts a group
     */
    @Transaction
    @Upsert
    suspend fun upsertGroup(group: GroupEntity): Long

    /**
     * Upserts a list of [GroupTeamCrossRef]
     */
    @Transaction
    @Insert
    suspend fun upsertGroupTeamCrossRef(groupTeamCrossRefs: List<GroupTeamCrossRef>)

    /**
     * Retrieves a group with round and team data for a given group id
     */
    @Transaction
    @Query("SELECT * FROM group_phase_group WHERE groupId = :groupId")
    fun getGroup(groupId: Long): GroupWithRoundsAndTeams

}
