package com.dntks.groupstagesimulator.ui.navigation

import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dntks.groupstagesimulator.ui.groupstatistics.view.GroupDetails
import com.dntks.groupstagesimulator.ui.overview.view.GroupsOverview
import kotlinx.serialization.Serializable

@Serializable
object Overview

@Serializable
data class Details(
    val groupId: Long
)

@Composable
fun GroupPhaseNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
//    groupViewModel: OverviewViewModel = hiltViewModel(),
//    statisticsViewModel: GroupStatisticsViewModel = hiltViewModel(),
) {

    Surface(
        modifier = Modifier.safeContentPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold { paddingValues ->
            paddingValues.toString()
            NavHost(
                navController = navController,
                startDestination = Overview,
                modifier = modifier
            ) {
                composable<Overview> {
                    GroupsOverview(
                        modifier = Modifier,
                    ){
                        navController.navigate(route = Details(it))
                    }
                }
                composable<Details> { backStackEntry ->
                    GroupDetails (
                        modifier = Modifier.safeContentPadding(),
                        onBack = { navController.popBackStack() },
                    )
                }
            }
        }
    }
}