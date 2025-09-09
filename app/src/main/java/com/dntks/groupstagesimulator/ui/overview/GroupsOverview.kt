package com.dntks.groupstagesimulator.ui.overview

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel

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

@Composable
fun GroupsList(
    groups: List<GroupDomainModel>,
    onGroupClick: (groupId: Long) -> Unit
) {
    LazyColumn {
        items(groups) { group ->
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.Companion.Black,
                        shape = RoundedCornerShape(3.dp)
                    )
                    .background(Color.Companion.LightGray)
                    .padding(vertical = 16.dp)
                    .clickable {
                        group.id?.let {
                            onGroupClick(it)
                        }
                    }
            ) {
                Text(
                    text = group.name,
                    modifier = Modifier.padding(vertical = 6.dp),
                    fontSize = 20.sp
                )
                group.teams.forEach {
                    Text(text = it.name)
                }
            }
        }
    }
}

@Composable
fun AddGroupView(
    modifier: Modifier,
    teams: List<TeamEntity>,
    onAddGroup: (group: GroupDomainModel) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var teamBySlot by remember { mutableStateOf(mapOf<String, TeamEntity>()) }

    Column(modifier = modifier) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter group name") }
        )
        if (teams.isNotEmpty()) {
            LongBasicDropdownMenu(teams, teamBySlot["Slot1"] ?: teams[0]) {
                teamBySlot = teamBySlot.toMutableMap().apply { put("Slot1", it) }
            }
            LongBasicDropdownMenu(teams, teamBySlot["Slot2"] ?: teams[0]) {
                teamBySlot = teamBySlot.toMutableMap().apply { put("Slot2", it) }
            }
            LongBasicDropdownMenu(teams, teamBySlot["Slot3"] ?: teams[0]) {
                teamBySlot = teamBySlot.toMutableMap().apply { put("Slot3", it) }
            }
            LongBasicDropdownMenu(teams, teamBySlot["Slot4"] ?: teams[0]) {
                teamBySlot = teamBySlot.toMutableMap().apply { put("Slot4", it) }
            }
        }
        Button(onClick = {
            onAddGroup(
                GroupDomainModel(
                    name = text,
                    teams = listOfNotNull(
                        teamBySlot["Slot1"]?.let { actualTeam ->
                            TeamDomainModel(
                                teamId = actualTeam.teamId,
                                name = actualTeam.name,
                                players = emptyList()
                            )
                        },
                        teamBySlot["Slot2"]?.let { actualTeam ->
                            TeamDomainModel(
                                teamId = actualTeam.teamId,
                                name = actualTeam.name,
                                players = emptyList()
                            )
                        },
                        teamBySlot["Slot3"]?.let { actualTeam ->
                            TeamDomainModel(
                                teamId = actualTeam.teamId,
                                name = actualTeam.name,
                                players = emptyList()
                            )
                        },
                        teamBySlot["Slot4"]?.let { actualTeam ->
                            TeamDomainModel(
                                teamId = actualTeam.teamId,
                                name = actualTeam.name,
                                players = emptyList()
                            )
                        },
                    ),
                    rounds = emptyList()
                )

            )
        }) {
            Text("Add group")
        }
    }
}

@Composable
fun LongBasicDropdownMenu(
    values: List<TeamEntity>,
    chosenTeam: TeamEntity,
    onValueChange: (TeamEntity) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options")
        }
        Text(text = chosenTeam.name)
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            values.forEach { team ->
                DropdownMenuItem(
                    text = { Text(team.name) },
                    onClick = {
                        onValueChange(team)
                    }
                )
            }
        }
    }
}