package com.dntks.groupstagesimulator.data.repository

import androidx.room.Transaction
import com.dntks.groupstagesimulator.data.db.dao.GroupDao
import com.dntks.groupstagesimulator.data.db.dao.GroupStageMatchDao
import com.dntks.groupstagesimulator.data.db.dao.RoundDao
import com.dntks.groupstagesimulator.data.db.dao.TeamDao
import com.dntks.groupstagesimulator.data.db.entity.GroupEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseRoundEntity
import com.dntks.groupstagesimulator.data.db.entity.GroupTeamCrossRef
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.db.entity.TeamWithPlayers
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.GroupStatistics
import com.dntks.groupstagesimulator.data.model.MatchDomainModel
import com.dntks.groupstagesimulator.data.model.RoundDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel
import com.dntks.groupstagesimulator.data.model.TeamStatistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Random
import javax.inject.Inject

interface GroupStageRepository {
    fun getMatches(): Flow<List<GroupPhaseMatchEntity>>
    fun getTeams(): Flow<List<TeamEntity>>
    fun getTeamsWithPlayers(): Flow<List<TeamWithPlayers>>
    fun getGroupsWithTeams(): Flow<List<GroupDomainModel>>
    fun getGroupWithTeams(groupId: Long): Flow<GroupDomainModel>
    suspend fun addGroupWithTeams(group: GroupDomainModel)
    suspend fun generateAllMatches(groupId: Long)
    suspend fun getGroupStatistics(groupId: Long, teams: List<TeamDomainModel>): Flow<GroupStatistics>
    suspend fun deleteRoundsForGroup(groupId: Long)
}

class GroupStageRepositoryImpl @Inject constructor(
    val groupStageMatchDao: GroupStageMatchDao,
    val teamDao: TeamDao,
    val groupDao: GroupDao,
    val roundDao: RoundDao,
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

    enum class MatchOutcome{
        HOME_WIN, DRAW, AWAY_WIN
    }

    @Transaction
    override suspend fun getGroupStatistics(groupId: Long, teams: List<TeamDomainModel>): Flow<GroupStatistics> {
        val teamStatisticsPerTeam = teams.associateBy(
            { it.teamId },
            { TeamStatistics() }
        ).toMutableMap()
        val roundStatistics = mutableListOf<RoundDomainModel>()
        return roundDao.getRoundsWithMatches(groupId).map { rounds ->
            rounds.forEach { round ->
                round.matches.forEach {
                    val homeTeamStatistics = teamStatisticsPerTeam.getOrDefault(it.homeTeamId, TeamStatistics())
                    val matchOutcome = if (it.homeGoals > it.awayGoals){
                        MatchOutcome.HOME_WIN
                    } else if (it.homeGoals == it.awayGoals){
                        MatchOutcome.DRAW
                    } else {
                        MatchOutcome.AWAY_WIN
                    }
                    val updatedHomeStats = homeTeamStatistics.copy(
                        played = homeTeamStatistics.played + 1,
                        goalsFor = homeTeamStatistics.goalsFor + it.homeGoals,
                        goalsAgainst = homeTeamStatistics.goalsAgainst + it.awayGoals,
                        goalDifference = homeTeamStatistics.goalDifference + (it.homeGoals - it.awayGoals),
                        win = homeTeamStatistics.win + if (matchOutcome == MatchOutcome.HOME_WIN) 1 else 0,
                        loss = homeTeamStatistics.loss + if (matchOutcome == MatchOutcome.AWAY_WIN) 1 else 0,
                        draw = homeTeamStatistics.draw + if (matchOutcome == MatchOutcome.DRAW) 1 else 0,
                        points = homeTeamStatistics.points + when(matchOutcome){
                            MatchOutcome.HOME_WIN -> 3
                            MatchOutcome.DRAW -> 1
                            MatchOutcome.AWAY_WIN -> 0
                        }
                    )
                    val awayTeamStatistics = teamStatisticsPerTeam.getOrDefault(it.awayTeamId, TeamStatistics())
                    val updatedAwayStats = awayTeamStatistics.copy(
                        played = awayTeamStatistics.played + 1,
                        goalsFor = awayTeamStatistics.goalsFor + it.awayGoals,
                        goalsAgainst = awayTeamStatistics.goalsAgainst + it.homeGoals,
                        goalDifference = awayTeamStatistics.goalDifference + (it.awayGoals - it.homeGoals),
                        win = awayTeamStatistics.win + if (matchOutcome == MatchOutcome.AWAY_WIN) 1 else 0,
                        loss = awayTeamStatistics.loss + if (matchOutcome == MatchOutcome.HOME_WIN) 1 else 0,
                        draw = awayTeamStatistics.draw + if (matchOutcome == MatchOutcome.DRAW) 1 else 0,
                        points = awayTeamStatistics.points + when(matchOutcome){
                            MatchOutcome.HOME_WIN -> 0
                            MatchOutcome.DRAW -> 1
                            MatchOutcome.AWAY_WIN -> 3
                        }
                    )
                    teamStatisticsPerTeam[it.homeTeamId] = updatedHomeStats
                    teamStatisticsPerTeam[it.awayTeamId] = updatedAwayStats
                }

                val roundDomainModel = RoundDomainModel(
                    roundId = round.round.roundId,
                    roundName = round.round.roundName,
                    matches = round.matches.map {  match ->
                        MatchDomainModel(
                            homeTeam = teams.first{it.teamId == match.homeTeamId},
                            awayTeam = teams.first{it.teamId == match.awayTeamId},
                            homeGoals = match.homeGoals,
                            awayGoals = match.awayGoals,
                            date = "",
                            time = ""
                        )
                    }
                )
                roundStatistics.add(roundDomainModel)
            }

            val teamStatistics = teams.associateBy(
                {
                    TeamDomainModel(
                        teamId = it.teamId,
                        name = it.name,
                        players = emptyList()
                    )
                },
                {
                    teamStatisticsPerTeam[it.teamId] ?: TeamStatistics()
                }
            )
            GroupStatistics(
                teamStatistics = teamStatistics,
                roundStatistics = roundStatistics
            )
        }
    }

    override suspend fun deleteRoundsForGroup(groupId: Long) {
        withContext(Dispatchers.IO){
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

    override suspend fun generateAllMatches(groupId: Long) {
        withContext(Dispatchers.IO) {
            val group = groupDao.getGroup(groupId)
            val numberOfRounds = group.teams.size - 1

            val team1 = group.teams[0]
            val team2 = group.teams[1]
            val team3 = group.teams[2]
            val team4 = group.teams[3]
            generateRound(groupId, roundOrder = 1, Pair(team1, team2), Pair(team3, team4))
            generateRound(groupId, roundOrder = 2, Pair(team1, team3), Pair(team2, team4))
            generateRound(groupId, roundOrder = 3, Pair(team4, team1), Pair(team2, team3))
        }
    }

    suspend fun generateRound(
        groupId: Long,
        roundOrder: Int,
        firstMatchPair: Pair<TeamEntity, TeamEntity>,
        secondMatchPair: Pair<TeamEntity, TeamEntity>
    ) {
        val round = GroupPhaseRoundEntity(roundName = "Round $roundOrder", roundGroupId = groupId)
        val roundId = roundDao.upsertRound(round)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val firstMatch = GroupPhaseMatchEntity(
            matchRoundId = roundId,
            homeTeamId = firstMatchPair.first.teamId,
            awayTeamId = firstMatchPair.second.teamId,
            homeGoals = Random().nextInt(5),
            awayGoals = Random().nextInt(5),
            date = LocalDateTime.now().format(formatter),
            time = null,
            isPlayed = true
        )
        val secondMatch = GroupPhaseMatchEntity(
            matchRoundId = roundId,
            homeTeamId = secondMatchPair.first.teamId,
            awayTeamId = secondMatchPair.second.teamId,
            homeGoals = Random().nextInt(5),
            awayGoals = Random().nextInt(5),
            date = LocalDateTime.now().format(formatter),
            time = null,
            isPlayed = true
        )
        groupStageMatchDao.upsertMatch(firstMatch)
        groupStageMatchDao.upsertMatch(secondMatch)
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
                    date = if(match.isPlayed) LocalDateTime.now().toString() else "",
                    time = "",
                )
            },
        )
    }

}