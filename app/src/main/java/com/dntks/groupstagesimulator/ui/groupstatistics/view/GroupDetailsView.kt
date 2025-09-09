package com.dntks.groupstagesimulator.ui.groupstatistics.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.domain.GroupStatistics
import com.dntks.groupstagesimulator.ui.groupstatistics.model.MatchOutcome
import com.dntks.groupstagesimulator.ui.groupstatistics.model.Round
import com.dntks.groupstagesimulator.ui.groupstatistics.model.Team
import com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel.GroupStatisticsViewModel

/**
 * View containing group and round statistics
 */
@Composable
fun GroupDetailsView(
    modifier: Modifier = Modifier,
    group: GroupDomainModel,
    viewModel: GroupStatisticsViewModel,
    groupStatistics: GroupStatistics,
    onBack: () -> Unit
) {
    Column {
        TopBar(group, viewModel, onBack)
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                GroupStatistics(
                    teams = groupStatistics.teamStatistics.map { entry ->
                        val team = entry.key
                        val stats = entry.value
                        Team(
                            name = team.name,
                            played = stats.played,
                            win = stats.win,
                            loss = stats.loss,
                            draw = stats.draw,
                            points = stats.points,
                            goalsFor = stats.goalsFor,
                            goalsAgainst = stats.goalsAgainst,
                            goalDifference = stats.goalDifference
                        )
                    }
                )
            }
            items(items = groupStatistics.roundStatistics) { round ->
                RoundStatistics(
                    round = Round(
                        roundName = round.roundName,
                        matchOutcomes = round.matches.map { match ->
                            MatchOutcome(
                                homeTeam = match.homeTeam.name,
                                awayTeam = match.awayTeam.name,
                                homeGoals = match.homeGoals,
                                awayGoals = match.awayGoals,
                                played = true
                            )
                        }
                    ))
            }
        }
    }
}