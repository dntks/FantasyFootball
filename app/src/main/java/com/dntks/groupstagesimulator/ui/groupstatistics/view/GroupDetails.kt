package com.dntks.groupstagesimulator.ui.groupstatistics.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.GroupStatistics
import com.dntks.groupstagesimulator.ui.groupstatistics.model.Team
import com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel.GroupStatisticsUiState
import com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel.GroupStatisticsViewModel

@Composable
fun GroupDetails(
    modifier: Modifier,
    groupStatisticsViewModel: GroupStatisticsViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState by groupStatisticsViewModel.selectedGroup.collectAsStateWithLifecycle()
    val groupStatistics by groupStatisticsViewModel.groupStatistics.collectAsStateWithLifecycle()

    when (uiState) {
        GroupStatisticsUiState.Error -> Text("Error")
        GroupStatisticsUiState.Loading -> Text("Loading")
        is GroupStatisticsUiState.Success -> {
            GroupDetailsView(
                viewModel = groupStatisticsViewModel,
                modifier = Modifier,
                group = (uiState as GroupStatisticsUiState.Success).group,
                groupStatistics = groupStatistics,
                onBack = onBack
            )
        }
    }
}

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

@Composable
@Preview
fun GroupOverviewPreview() {
    GroupDetails(
        modifier = Modifier,
        onBack = {}
    )

}
