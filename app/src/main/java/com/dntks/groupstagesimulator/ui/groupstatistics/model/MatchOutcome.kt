package com.dntks.groupstagesimulator.ui.groupstatistics.model

/**
 * UI model for match outcome
 */
data class MatchOutcome(
    val homeTeam: String,
    val awayTeam: String,
    val homeGoals: Int,
    val awayGoals: Int,
    val played: Boolean = false
)