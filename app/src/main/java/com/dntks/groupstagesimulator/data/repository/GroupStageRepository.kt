package com.dntks.groupstagesimulator.data.repository

import com.dntks.groupstagesimulator.data.db.dao.GroupDao
import com.dntks.groupstagesimulator.data.db.dao.GroupStageMatchDao
import com.dntks.groupstagesimulator.data.db.dao.RoundDao
import com.dntks.groupstagesimulator.data.db.dao.TeamDao
import com.dntks.groupstagesimulator.data.db.entity.GroupEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseRoundEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupTeamCrossRef
import com.dntks.groupstagesimulator.data.db.entity.GroupWithRoundsAndTeams
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.db.entity.TeamWithPlayers
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.MatchDomainModel
import com.dntks.groupstagesimulator.data.model.RoundDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * Repository for the app.
 */
interface GroupStageRepository {
    fun getMatches(): Flow<List<GroupPhaseMatchEntity>>
    fun getTeams(): Flow<List<TeamEntity>>
    fun getTeamsWithPlayers(): Flow<List<TeamWithPlayers>>
    fun getGroupsWithTeams(): Flow<List<GroupDomainModel>>
    fun getGroupWithTeams(groupId: Long): Flow<GroupDomainModel>
    suspend fun addGroupWithTeams(group: GroupDomainModel)
//    suspend fun generateAllMatches(groupId: Long)
    suspend fun deleteRoundsForGroup(groupId: Long)
    fun getRoundsWithMatches(groupId: Long): Flow<List<RoundDomainModel>>
    suspend fun upsertRound(roundName: String, groupId: Long): Long
    suspend fun upsertMatch(match: GroupPhaseMatchEntity)
    fun getGroup(groupId: Long): GroupWithRoundsAndTeams
}

/**
 * Default GroupStageRepository implementation
 */
class GroupStageRepositoryImpl @Inject constructor(
    val groupStageMatchDao: GroupStageMatchDao,
    val teamDao: TeamDao,
    val groupDao: GroupDao,
    val roundDao: RoundDao,
) : GroupStageRepository {

    /**
     * Returns all matches in the database.
     */
    override fun getMatches(): Flow<List<GroupPhaseMatchEntity>> {
        return groupStageMatchDao.getAllMatches()
    }

    /**
     * Returns all teams in the database.
     */
    override fun getTeams(): Flow<List<TeamEntity>> {
        return teamDao.getAllTeams()
    }

    /**
     * Returns all teams and their players in the database.
     */
    override fun getTeamsWithPlayers(): Flow<List<TeamWithPlayers>> {
        return teamDao.getAllTeamWithPlayers()
    }

    /**
     * Returns all rounds for a group.
     */
    override fun getRoundsWithMatches(
        groupId: Long,
    ): Flow<List<RoundDomainModel>> {
        return roundDao.getRoundsWithMatches(groupId).combine(getTeams()) { rounds, teams ->
            rounds.map { roundWithMatches ->
                RoundDomainModel(
                    roundId = roundWithMatches.round.roundId,
                    roundName = roundWithMatches.round.roundName,
                    matches = roundWithMatches.matches.map {
                        MatchDomainModel(
                            homeTeam = TeamDomainModel(
                                teamId = it.homeTeamId,
                                name = teams.first { team -> team.teamId == it.homeTeamId }.name,
                                players = emptyList()
                            ),
                            awayTeam = TeamDomainModel(
                                teamId = it.awayTeamId,
                                name = teams.first { team -> team.teamId == it.awayTeamId }.name,
                                players = emptyList()
                            ),
                            homeGoals = it.homeGoals,
                            awayGoals = it.awayGoals,
                            time = "",
                        )
                    }
                )
            }
        }
    }

    override suspend fun upsertRound(roundName: String, groupId: Long): Long {
        val round = GroupPhaseRoundEntity(roundName = roundName, roundGroupId = groupId)
        return roundDao.upsertRound(round)
    }

    override suspend fun upsertMatch(match: GroupPhaseMatchEntity) {
        groupStageMatchDao.upsertMatch(match)
    }

    override fun getGroup(groupId: Long) : GroupWithRoundsAndTeams{
        return groupDao.getGroup(groupId)
    }

    enum class MatchOutcome {
        HOME_WIN, DRAW, AWAY_WIN
    }

    override suspend fun deleteRoundsForGroup(groupId: Long) {
        withContext(Dispatchers.IO) {
            roundDao.deleteRoundsFromGroup(groupId)
        }
    }

    override fun getGroupWithTeams(groupId: Long): Flow<GroupDomainModel> {
        val allRoundsForGroup = groupDao.getAllRoundsForGroupFlow(groupId)
        val domainModelFlow = allRoundsForGroup.map { groupWithStats ->
            GroupDomainModel(
                name = groupWithStats.group.groupName,
                id = groupWithStats.group.groupId,
                teams = groupWithStats.teams.map {
                    TeamDomainModel(
                        teamId = it.teamId,
                        name = it.name,
                        players = emptyList()
                    )
                },
                rounds = groupWithStats.rounds.map {
                    getRoundDomainModel(it)
                }
            )
        }
        return domainModelFlow
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
                    rounds = it.rounds.map { round ->
                        getRoundDomainModel(round)
                    }
                )
            }
        }
    }

    private suspend fun getRoundDomainModel(round: GroupPhaseRoundEntity): RoundDomainModel {
        val matches = groupStageMatchDao.getAllMatchesForRound(round.roundId)
        return RoundDomainModel(
            roundId = round.roundId,
            roundName = round.roundName,
            matches = matches.map { match ->
                val homeTeam = teamDao.findTeamById(match.homeTeamId)
                val awayTeam = teamDao.findTeamById(match.awayTeamId)
                MatchDomainModel(
                    homeTeam = TeamDomainModel(
                        teamId = homeTeam.teamId,
                        name = homeTeam.name,
                        players = emptyList()
                    ),
                    awayTeam = TeamDomainModel(
                        teamId = awayTeam.teamId,
                        name = awayTeam.name,
                        players = emptyList()
                    ),
                    homeGoals = match.homeGoals,
                    awayGoals = match.awayGoals,
                    time = if (match.isPlayed) LocalDateTime.now().toString() else "",
                )
            },
        )
    }

}