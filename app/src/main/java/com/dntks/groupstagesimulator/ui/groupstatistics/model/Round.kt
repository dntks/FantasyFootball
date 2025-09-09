package com.dntks.groupstagesimulator.ui.groupstatistics.model

/**
 * UI model for Round statistics
 */
data class Round(
    val roundName: String,
    val matchOutcomes: List<MatchOutcome>
)