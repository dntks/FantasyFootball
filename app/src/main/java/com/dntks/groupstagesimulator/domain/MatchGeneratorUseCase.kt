package com.dntks.groupstagesimulator.domain

import com.dntks.groupstagesimulator.data.db.entity.GroupPhaseMatchEntity
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random

class MatchGeneratorUseCase @Inject constructor(
    private val repository: GroupStageRepository
)  {

    suspend fun generateAllMatches(groupId: Long) {
        withContext(Dispatchers.IO) {
            val group = repository.getGroup(groupId)
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
        val roundId = repository.upsertRound(roundName = "Round $roundOrder", groupId = groupId)
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
        repository.upsertMatch(firstMatch)
        repository.upsertMatch(secondMatch)
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
}