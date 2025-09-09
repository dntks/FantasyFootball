package com.dntks.groupstagesimulator.data.repository

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
import com.dntks.groupstagesimulator.data.model.MatchDomainModel
import com.dntks.groupstagesimulator.data.model.RoundDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random

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
    suspend fun generateAllMatches(groupId: Long)
    suspend fun deleteRoundsForGroup(groupId: Long)
    fun getRoundsWithMatches(groupId: Long): Flow<List<RoundDomainModel>>
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

    override suspend fun generateAllMatches(groupId: Long) {
        withContext(Dispatchers.IO) {
            val group = groupDao.getGroup(groupId)
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
        val firstMatchStats = generateMatchStats(firstMatchPair.first, firstMatchPair.second)
        val firstMatch = GroupPhaseMatchEntity(
            matchRoundId = roundId,
            homeTeamId = firstMatchPair.first.teamId,
            awayTeamId = firstMatchPair.second.teamId,
            homeGoals = firstMatchStats.homeGoals,
            awayGoals = firstMatchStats.awayGoals,
            date = LocalDateTime.now().format(formatter),
            time = null,
            isPlayed = true
        )
        val secondMatchStats = generateMatchStats(secondMatchPair.first, secondMatchPair.second)

        val secondMatch = GroupPhaseMatchEntity(
            matchRoundId = roundId,
            homeTeamId = secondMatchPair.first.teamId,
            awayTeamId = secondMatchPair.second.teamId,
            homeGoals = secondMatchStats.homeGoals,
            awayGoals = secondMatchStats.awayGoals,
            date = LocalDateTime.now().format(formatter),
            time = null,
            isPlayed = true
        )
        groupStageMatchDao.upsertMatch(firstMatch)
        groupStageMatchDao.upsertMatch(secondMatch)
    }

    data class GeneratedMatchStats(
        val homeGoals: Int,
        val awayGoals: Int
    )

    fun generateMatchStats(
        homeTeam: TeamEntity,
        awayTeam: TeamEntity
    ): GeneratedMatchStats {
        val homeAttack =
            Random.nextFloat() * homeTeam.attack * Random.nextFloat() * (1 - awayTeam.defense)
        val awayAttack =
            Random.nextFloat() * awayTeam.attack * Random.nextFloat() * (1 - homeTeam.defense)
        return GeneratedMatchStats(
            scoredGoalsBasedOnAttack(homeAttack),
            scoredGoalsBasedOnAttack(awayAttack)
        )
    }

    fun scoredGoalsBasedOnAttack(attack: Float): Int {
        return (attack * Random.nextInt(10, 30)).toInt()
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