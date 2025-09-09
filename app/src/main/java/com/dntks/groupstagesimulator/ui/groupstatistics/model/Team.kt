package com.dntks.groupstagesimulator.ui.groupstatistics.model

data class Team(
    val name: String,
    val position: Int,
    val played: Int,
    val win: Int,
    val loss: Int,
    val draw: Int,
    val points: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int
)

val barcelona = Team(
    name = "Barcelona",
    position = 1,
    played = 3,
    win = 2,
    loss = 0,
    draw = 1,
    points = 7,
    goalsFor = 7,
    goalsAgainst = 1,
    goalDifference = 6,
)
val ftc = Team(
    name = "FTC",
    position = 2,
    played = 3,
    win = 1,
    loss = 0,
    draw = 1,
    points = 4,
    goalsFor = 3,
    goalsAgainst = 2,
    goalDifference = 1,
)
val arsenal = Team(
    name = "Arsenal",
    position = 3,
    played = 3,
    win = 1,
    loss = 2,
    draw = 0,
    points = 3,
    goalsFor = 2,
    goalsAgainst = 1,
    goalDifference = 1,
)
val glasgow = Team(
    name = "Glasgow",
    position = 4,
    played = 3,
    win = 0,
    loss = 3,
    draw = 0,
    points = 0,
    goalsFor = 0,
    goalsAgainst = 7,
    goalDifference = -7,
)
