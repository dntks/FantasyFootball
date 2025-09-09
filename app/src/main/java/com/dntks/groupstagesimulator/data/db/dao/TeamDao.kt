package com.dntks.groupstagesimulator.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.db.entity.TeamWithPlayers
import kotlinx.coroutines.flow.Flow

/**
 * Dao for retrieving and updating [TeamEntity] from DB
 */
@Dao
interface TeamDao {

    /**
     * Retrieves all teams
     */
    @Transaction
    @Query("SELECT * FROM team")
    fun getAllTeams(): Flow<List<TeamEntity>>

    /**
     * Retrieves all teams with players
     */
    @Transaction
    @Query("SELECT * FROM team")
    fun getAllTeamWithPlayers(): Flow<List<TeamWithPlayers>>

    /**
     * Upserts a team
     */
    @Transaction
    @Upsert
    suspend fun upsertTeam(team: TeamEntity): Long

    /**
     * Retrieves a team by id
     */
    @Transaction
    @Query("SELECT * FROM team WHERE teamId = :id")
    suspend fun findTeamById(id: Long): TeamEntity

    /**
     * Upserts a list of teams
     */
    @Transaction
    @Upsert
    suspend fun upsertTeams(team: List<TeamEntity>)

    /**
     * Deletes all teams
     */
    @Transaction
    @Query("DELETE FROM team")
    suspend fun deleteAllTeams()
}