package com.dntks.groupstagesimulator.ui.groupstatistics.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dntks.groupstagesimulator.ui.groupstatistics.model.MatchOutcome
import com.dntks.groupstagesimulator.ui.groupstatistics.model.Round
import com.dntks.groupstagesimulator.ui.theme.CardBeige

/**
 * Round statistics view
 */
@Composable
fun RoundStatistics(round: Round) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(CardBeige)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(10.dp)
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
        Row {
            Text(round.roundName, fontSize = 25.sp, modifier = Modifier.padding(vertical = 10.dp))
        }
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
        ) {
            Row(modifier = Modifier.wrapContentWidth()) {
                RoundStatisticsItem("Home")
                RoundStatisticsItem("Score", 70.dp)
                RoundStatisticsItem("Away")
            }

            round.matchOutcomes.forEach {
                Row(modifier = Modifier.wrapContentWidth()) {
                    val score = if (it.played) "${it.homeGoals} - ${it.awayGoals}" else "-"
                    RoundStatisticsItem(it.homeTeam)
                    RoundStatisticsItem(score, 70.dp)
                    RoundStatisticsItem(it.awayTeam)
                }
            }
        }
    }
}

/**
 * Round statistics text item
 */
@Composable
private fun RoundStatisticsItem(itemText: String, width: Dp = 120.dp) {
    Column(modifier = Modifier.width(width)
        .padding(bottom = 8.dp)
    ) {
        Text(text = itemText, color = Color.Black)
    }
}

@Preview
@Composable
fun RoundStatisticsPreview() {
    RoundStatistics(
        Round(
            "Round 1",
            listOf(
                MatchOutcome(
                    "Team A",
                    "Team B",
                    2,
                    1
                ),
                MatchOutcome(
                    "Team C",
                    "Team D",
                    3,
                    0,
                    true
                )
            )
        )
    )
}