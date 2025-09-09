package com.dntks.groupstagesimulator.ui.overview.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dntks.groupstagesimulator.R
import com.dntks.groupstagesimulator.ui.overview.viewmodel.OverviewViewModel

@Composable
fun GroupsOverview(
    viewModel: OverviewViewModel = hiltViewModel(),
    onGroupClick: (groupId: Long) -> Unit = {}
) {

    val groups by viewModel.groupsFlow.collectAsStateWithLifecycle()
    Column {

        Row(
            Modifier.Companion
                .fillMaxWidth()
                .padding(bottom = 15.dp),
            verticalAlignment = Alignment.Companion.CenterVertically
        ) {
            Text(
                stringResource(R.string.title),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Companion.SemiBold)
            )

        }
        GroupsList(groups, onGroupClick)
//        AddGroupView(modifier = modifier, teams) {
//            viewModel.addGroup(it)
//        }
//        Button(onClick = {
//            viewModel.addTeams()
//        }) {
//            Text("add teams")
//        }

    }
}

