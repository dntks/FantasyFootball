package com.dntks.groupstagesimulator.ui.groupstatistics.model

data class Match(
    val homeTeam: Team,
    val awayTeam: Team,
    val homeGoals: Int,
    val awayGoals: Int,
    val date: String,
    val time: String
)
