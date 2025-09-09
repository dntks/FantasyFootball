package com.dntks.groupstagesimulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.dntks.groupstagesimulator.ui.groupstatistics.GroupView
import com.dntks.groupstagesimulator.ui.groupstatistics.MatchOutcome
import com.dntks.groupstagesimulator.ui.groupstatistics.Round
import com.dntks.groupstagesimulator.ui.groupstatistics.model.arsenal
import com.dntks.groupstagesimulator.ui.groupstatistics.model.barcelona
import com.dntks.groupstagesimulator.ui.groupstatistics.model.ftc
import com.dntks.groupstagesimulator.ui.groupstatistics.model.glasgow
import com.dntks.groupstagesimulator.ui.overview.GroupsOverview
import com.dntks.groupstagesimulator.ui.theme.GroupStageSimulatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroupStageSimulatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        GroupsOverview(modifier = Modifier.safeContentPadding())
                        GroupView(
                            modifier = Modifier.safeContentPadding(),
                            com.dntks.groupstagesimulator.ui.groupstatistics.Group(
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
                            ),
                            onSimulateClick = ::onSimulateClick,
                            onSimulateAllClick = ::onSimulateAllClick
                        )
                    }
                }
            }
        }
    }

    fun onSimulateClick(match: MatchOutcome) {
        println("Simulate Clicked")
    }

    fun onSimulateAllClick() {
        println("Simulate Clicked")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GroupStageSimulatorTheme {
        Greeting("Android")
    }
}