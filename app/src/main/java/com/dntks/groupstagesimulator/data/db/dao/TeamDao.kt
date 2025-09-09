package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.db.entity.TeamWithPlayers
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {

    @Transaction
    @Query("SELECT * FROM team")
    fun getAllTeams(): Flow<List<TeamEntity>>

    @Transaction
    @Query("SELECT * FROM team")
    fun getAllTeamWithPlayers(): Flow<List<TeamWithPlayers>>

    @Upsert
    suspend fun upsertTeam(team: TeamEntity): Long

    @Query("SELECT * FROM team WHERE teamId = :id")
    suspend fun findTeamById(id: Long): TeamEntity

    @Upsert
    suspend fun upsertTeams(team: List<TeamEntity>)

    @Query("DELETE FROM team")
    suspend fun deleteAllTeams()
}