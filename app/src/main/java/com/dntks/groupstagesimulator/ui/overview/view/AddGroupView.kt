package com.dntks.groupstagesimulator.ui.overview.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.dntks.groupstagesimulator.data.db.entity.TeamEntity
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel

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