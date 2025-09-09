package com.dntks.groupstagesimulator.ui.groupstatistics.model

/**
 * UI mode for team statistics
 */
data class Team(
    val name: String,
    val played: Int,
    val win: Int,
    val loss: Int,
    val draw: Int,
    val points: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int
)
