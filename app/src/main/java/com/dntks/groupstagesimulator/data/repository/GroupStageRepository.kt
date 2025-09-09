package com.dntks.groupstagesimulator.data.repository

import com.dntks.groupstagesimulator.data.db.dao.GroupDao
import com.dntks.groupstagesimulator.data.db.dao.GroupStageMatchDao
import com.dntks.groupstagesimulator.data.db.dao.TeamDao
import com.dntks.groupstagesimulator.data.db.entity.GroupEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupTeamCrossRef
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.db.entity.TeamWithPlayers
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GroupStageRepository {
    fun getMatches(): Flow<List<GroupPhaseMatchEntity>>
    fun getTeams(): Flow<List<TeamEntity>>
    fun getTeamsWithPlayers(): Flow<List<TeamWithPlayers>>
    fun getGroupsWithTeams(): Flow<List<GroupDomainModel>>
    suspend fun addGroupWithTeams(group: GroupDomainModel)
}

class GroupStageRepositoryImpl @Inject constructor(
    val groupStageMatchDao: GroupStageMatchDao,
    val teamDao: TeamDao,
    val groupDao: GroupDao
) : GroupStageRepository {
    override fun getMatches(): Flow<List<GroupPhaseMatchEntity>> {
        return groupStageMatchDao.getAllMatches()
    }

    override fun getTeams(): Flow<List<TeamEntity>> {
        return teamDao.getAllTeams()
    }

    override fun getTeamsWithPlayers(): Flow<List<TeamWithPlayers>> {
        return teamDao.getAllTeamWithPlayers()
    }

    override suspend fun addGroupWithTeams(group: GroupDomainModel) {
        withContext(Dispatchers.IO) {
            val groupId = groupDao.upsertGroup(
                GroupEntity(
                    groupName = group.name
                )
            )
            val crossRefs = group.teams.map { team ->
                GroupTeamCrossRef(
                    groupId = groupId,
                    teamId = team.teamId
                )
            }
            groupDao.upsertGroupTeamCrossRef(
                crossRefs
            )
        }
    }

    override fun getGroupsWithTeams(): Flow<List<GroupDomainModel>> {
        return groupDao.getGroups().map { groups ->
            groups.map {
                GroupDomainModel(
                    name = it.group.groupName,
                    id = it.group.groupId,
                    teams = it.teams.map { team ->
                        TeamDomainModel(
                            teamId = team.teamId,
                            name = team.name,
                            players = emptyList()
                        )
                    },
                    rounds = emptyList()
                )
            }
        }
    }

}