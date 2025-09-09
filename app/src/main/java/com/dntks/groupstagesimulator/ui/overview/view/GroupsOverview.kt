package com.dntks.groupstagesimulator.ui.overview.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dntks.groupstagesimulator.ui.overview.viewmodel.OverviewViewModel

@Composable
fun GroupsOverview(
    modifier: Modifier,
    viewModel: OverviewViewModel = hiltViewModel(),
    onGroupClick: (groupId: Long) -> Unit = {}
) {

    val teams by viewModel.teamsFlow.collectAsStateWithLifecycle()
    val groups by viewModel.groupsFlow.collectAsStateWithLifecycle()
    Column {

        GroupsList(groups, onGroupClick)
        AddGroupView(modifier = modifier, teams) {
            viewModel.addGroup(it)
        }
        Button(onClick = {
            viewModel.addTeams()
        }) {
            Text("add teams")
        }

    }
}

