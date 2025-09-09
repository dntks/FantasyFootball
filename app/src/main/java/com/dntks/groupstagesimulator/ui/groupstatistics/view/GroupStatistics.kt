package com.dntks.groupstagesimulator.ui.groupstatistics.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dntks.groupstagesimulator.ui.groupstatistics.model.Team
import com.dntks.groupstagesimulator.ui.theme.DeepGreen
import com.dntks.groupstagesimulator.ui.theme.DeepGreen90
import com.dntks.groupstagesimulator.ui.theme.LightGreen

/**
 * View containing group statistics
 */
@Composable
fun GroupStatistics(teams: List<Team>) {
    Row(
        modifier = Modifier.Companion
            .horizontalScroll(rememberScrollState())
            .wrapContentWidth()

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(DeepGreen)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(LightGreen)
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GroupStatisticsTitleItem("#", 20.dp)
                GroupStatisticsTitleItem("team", 110.dp)
                GroupStatisticsTitleItem("Pld")
                GroupStatisticsTitleItem("W")
                GroupStatisticsTitleItem("D")
                GroupStatisticsTitleItem("L")
                GroupStatisticsTitleItem("GF")
                GroupStatisticsTitleItem("GA")
                GroupStatisticsTitleItem("GD")
                GroupStatisticsTitleItem("Pts")
            }

            teams.sortedByDescending { it.points }.forEachIndexed { position, team ->
                val background = if(position %2 ==0) Color.Transparent else DeepGreen90
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(background)
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                ) {
                    GroupStatisticsTeamItem((position + 1).toString(), 20.dp)
                    GroupStatisticsTeamItem(team.name, 110.dp)
                    GroupStatisticsTeamItem(team.played.toString())
                    GroupStatisticsTeamItem(team.win.toString())
                    GroupStatisticsTeamItem(team.draw.toString())
                    GroupStatisticsTeamItem(team.loss.toString())
                    GroupStatisticsTeamItem(team.goalsFor.toString())
                    GroupStatisticsTeamItem(team.goalsAgainst.toString())
                    GroupStatisticsTeamItem(team.goalDifference.toString())
                    GroupStatisticsTeamItem(team.points.toString())
                }
            }
        }
    }
}

/**
 * View for showing an item in the statistics table
 */
@Composable
private fun GroupStatisticsTeamItem(itemText: String, width: Dp = 25.dp) {
    Column(
        modifier = Modifier
            .width(width)
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = itemText,
            color = Color.White
        )
    }
}

/**
 * View for showing a title in the statistics table
 */
@Composable
private fun GroupStatisticsTitleItem(title: String, width: Dp = 25.dp) {
    Column(
        modifier = Modifier.width(width),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title)
    }
}

@Composable
@Preview
fun GroupStatisticsPreview() {
    GroupStatistics(
        listOf(
            Team(
                name = "Barcelona",
                played = 3,
                win = 2,
                loss = 0,
                draw = 1,
                points = 7,
                goalsFor = 7,
                goalsAgainst = 1,
                goalDifference = 6,
            ),
            Team(
                name = "FTC",
                played = 3,
                win = 1,
                loss = 0,
                draw = 1,
                points = 4,
                goalsFor = 3,
                goalsAgainst = 2,
                goalDifference = 1,
            ),
            Team(
                name = "Glasgow",
                played = 3,
                win = 0,
                loss = 3,
                draw = 0,
                points = 0,
                goalsFor = 0,
                goalsAgainst = 7,
                goalDifference = -7,
            ),
            Team(
                name = "Arsenal",
                played = 3,
                win = 1,
                loss = 2,
                draw = 0,
                points = 3,
                goalsFor = 2,
                goalsAgainst = 1,
                goalDifference = 1,
            )
        )
    )
}