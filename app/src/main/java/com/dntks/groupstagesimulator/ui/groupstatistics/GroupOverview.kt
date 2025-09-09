package com.dntks.groupstagesimulator.ui.groupstatistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dntks.groupstagesimulator.ui.groupstatistics.model.Team
import com.dntks.groupstagesimulator.ui.groupstatistics.model.arsenal
import com.dntks.groupstagesimulator.ui.groupstatistics.model.barcelona
import com.dntks.groupstagesimulator.ui.groupstatistics.model.ftc
import com.dntks.groupstagesimulator.ui.groupstatistics.model.glasgow
import com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel.GroupStatisticsViewModel

data class Group(
    val name: String,
    val teams: List<Team>,
    val rounds: List<Round>,
)

@Composable
fun GroupView(
    modifier: Modifier,
    group: Group,
    viewModel: GroupStatisticsViewModel = hiltViewModel(),
    onSimulateClick: (match: MatchOutcome) -> Unit,
    onSimulateAllClick: () -> Unit
) {
    val teams by viewModel.teamsFlow.collectAsStateWithLifecycle()
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        items(items = teams) { team ->
            Text(text = team.team.name)
            team.players.forEach { player ->
                Text(text = "${player.name} as ${player.position}")

            }
        }
        item {
            GroupStatistics(
                teams = group.teams
            )
        }
        items(items = group.rounds) { round ->
            RoundStatistics(round = round)
        }

        item {
            Button(onClick = {
                viewModel.deleteTeams()
            }) {
                Text("delete teams")
            }
        }
        item {
            Button(onClick = {
                viewModel.addTeams()
            }) {
                Text("add teams")
            }
        }
    }

}

@Composable
@Preview
fun GroupOverviewPreview() {
    GroupView(
        onSimulateClick = {},
        onSimulateAllClick = {},
        modifier = Modifier,
        group = Group(
            name = "Group A",
            teams = listOf(arsenal, ftc, barcelona, glasgow),
            rounds = listOf(
                Round(
                    roundName = "Round 1",
                    matchOutcomes = listOf(
                        MatchOutcome(
                            homeTeam = arsenal.name,
                            awayTeam = ftc.name,
                            homeGoals = 2,
                            awayGoals = 1
                        ),
                        MatchOutcome(
                            homeTeam = barcelona.name,
                            awayTeam = glasgow.name,
                            homeGoals = 3,
                            awayGoals = 0,
                            played = true
                        )
                    )
                ),
                Round(
                    roundName = "Round 2",
                    matchOutcomes = listOf(
                        MatchOutcome(
                            homeTeam = arsenal.name,
                            awayTeam = barcelona.name,
                            homeGoals = 2,
                            awayGoals = 1
                        ),
                        MatchOutcome(
                            homeTeam = ftc.name,
                            awayTeam = glasgow.name,
                            homeGoals = 3,
                            awayGoals = 0,
                            played = true
                        )
                    )
                ),
                Round(
                    roundName = "Round 3",
                    matchOutcomes = listOf(
                        MatchOutcome(
                            homeTeam = arsenal.name,
                            awayTeam = glasgow.name,
                            homeGoals = 2,
                            awayGoals = 1
                        ),
                        MatchOutcome(
                            homeTeam = ftc.name,
                            awayTeam = barcelona.name,
                            homeGoals = 3,
                            awayGoals = 0,
                            played = true
                        )
                    )
                )
            )
        )
    )

}
