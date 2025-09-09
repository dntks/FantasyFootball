package com.dntks.groupstagesimulator.domain

import com.dntks.groupstagesimulator.data.model.RoundDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel
import com.dntks.groupstagesimulator.data.repository.GroupStageRepository
import com.dntks.groupstagesimulator.data.repository.GroupStageRepositoryImpl.MatchOutcome
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetGroupStatisticsUseCase @Inject constructor(
    private val repository: GroupStageRepository
) {

    fun getGroupStatistics(
        groupId: Long,
    ): Flow<GroupStatistics> {
        val roundStatistics = mutableListOf<RoundDomainModel>()
        return repository.getRoundsWithMatches(groupId)
            .combine(repository.getGroupWithTeams(groupId)) { rounds, group ->
                val teams = group.teams
                val teamStatisticsPerTeam = teams.associateBy(
                    { it.teamId },
                    { TeamStatistics() }
                ).toMutableMap()
                rounds.forEach { round ->
                    round.matches.forEach { match ->
                        val homeTeamStatistics =
                            teamStatisticsPerTeam.getOrDefault(match.homeTeam.teamId, TeamStatistics())
                        val matchOutcome = if (match.homeGoals > match.awayGoals) {
                            MatchOutcome.HOME_WIN
                        } else if (match.homeGoals == match.awayGoals) {
                            MatchOutcome.DRAW
                        } else {
                            MatchOutcome.AWAY_WIN
                        }
                        val updatedHomeStats = homeTeamStatistics.copy(
                            played = homeTeamStatistics.played + 1,
                            goalsFor = homeTeamStatistics.goalsFor + match.homeGoals,
                            goalsAgainst = homeTeamStatistics.goalsAgainst + match.awayGoals,
                            goalDifference = homeTeamStatistics.goalDifference + (match.homeGoals - match.awayGoals),
                            win = homeTeamStatistics.win + if (matchOutcome == MatchOutcome.HOME_WIN) 1 else 0,
                            loss = homeTeamStatistics.loss + if (matchOutcome == MatchOutcome.AWAY_WIN) 1 else 0,
                            draw = homeTeamStatistics.draw + if (matchOutcome == MatchOutcome.DRAW) 1 else 0,
                            points = homeTeamStatistics.points + when (matchOutcome) {
                                MatchOutcome.HOME_WIN -> 3
                                MatchOutcome.DRAW -> 1
                                MatchOutcome.AWAY_WIN -> 0
                            }
                        )

                        val awayTeamStatistics =
                            teamStatisticsPerTeam.getOrDefault(match.awayTeam.teamId, TeamStatistics())
                        val updatedAwayStats = awayTeamStatistics.copy(
                            played = awayTeamStatistics.played + 1,
                            goalsFor = awayTeamStatistics.goalsFor + match.awayGoals,
                            goalsAgainst = awayTeamStatistics.goalsAgainst + match.homeGoals,
                            goalDifference = awayTeamStatistics.goalDifference + (match.awayGoals - match.homeGoals),
                            win = awayTeamStatistics.win + if (matchOutcome == MatchOutcome.AWAY_WIN) 1 else 0,
                            loss = awayTeamStatistics.loss + if (matchOutcome == MatchOutcome.HOME_WIN) 1 else 0,
                            draw = awayTeamStatistics.draw + if (matchOutcome == MatchOutcome.DRAW) 1 else 0,
                            points = awayTeamStatistics.points + when (matchOutcome) {
                                MatchOutcome.HOME_WIN -> 0
                                MatchOutcome.DRAW -> 1
                                MatchOutcome.AWAY_WIN -> 3
                            }
                        )
                        teamStatisticsPerTeam[match.homeTeam.teamId] = updatedHomeStats
                        teamStatisticsPerTeam[match.awayTeam.teamId] = updatedAwayStats
                    }
                    roundStatistics.add(round)
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
                    roundStatistics = rounds
                )
            }
    }
}