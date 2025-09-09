package com.dntks.groupstagesimulator.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
object Overview

@Serializable
data class Details(
    val groupId: Long
)