package com.dntks.groupstagesimulator.data.repository

import com.dntks.groupstagesimulator.data.db.dao.GroupDao
import com.dntks.groupstagesimulator.data.db.dao.GroupStageMatchDao
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import com.dntks.groupstagesimulator.data.db.dao.RoundDao
import com.dntks.groupstagesimulator.data.db.dao.TeamDao
import com.dntks.groupstagesimulator.data.db.entity.TeamWithPlayers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GroupStageRepository {
    fun getMatches(): Flow<List<GroupPhaseMatchEntity>>
    fun getTeams(): Flow<List<TeamWithPlayers>>
}

class GroupStageRepositoryImpl @Inject constructor(
    val groupStageDao: GroupDao,
    val groupStageMatchDao: GroupStageMatchDao,
    val groupStageRoundDao: RoundDao,
    val teamDao: TeamDao,
    val groupDao: GroupDao
): GroupStageRepository{
    override fun getMatches(): Flow<List<GroupPhaseMatchEntity>> {
        return groupStageMatchDao.getAllMatches()
    }
    override fun getTeams(): Flow<List<TeamWithPlayers>> {
        return teamDao.getAllTeamWithPlayers()
    }

}