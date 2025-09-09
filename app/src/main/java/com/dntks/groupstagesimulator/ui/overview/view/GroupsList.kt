package com.dntks.groupstagesimulator.ui.overview.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dntks.groupstagesimulator.R
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.data.model.TeamDomainModel
import com.dntks.groupstagesimulator.ui.theme.CardBeige
import com.dntks.groupstagesimulator.ui.theme.DeepGreen90

@Composable
fun GroupsList(
    groups: List<GroupDomainModel>,
    onGroupClick: (groupId: Long) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(groups) { group ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(22.dp))
                    .background(CardBeige)
                    .padding(16.dp)
                    .clickable {
                        group.id?.let {
                            onGroupClick(it)
                        }
                    }
            ) {
                Text(
                    text = group.name,
                    modifier = Modifier.Companion.padding(vertical = 6.dp),
                    fontSize = 20.sp
                )
                Text(
                    text = stringResource(R.string.teams),
                    modifier = Modifier.Companion.padding(vertical = 6.dp),
                    fontSize = 16.sp
                )
                group.teams.forEach {
                    Text(
                        text = it.name,
                        color = DeepGreen90,
                        fontSize = 22.sp,
                        modifier = Modifier.Companion
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GroupsListPreview() {
    GroupsList(
        groups = listOf(
            GroupDomainModel(
                name = "Group A",
                teams = listOf(
                    TeamDomainModel(
                        teamId = 1,
                        name = "Arsenal",
                        players = emptyList()
                    ),
                    TeamDomainModel(
                        teamId = 2,
                        name = "Juventus",
                        players = emptyList()
                    ),
                    TeamDomainModel(
                        teamId = 2,
                        name = "Barcelona",
                        players = emptyList()
                    ),
                    TeamDomainModel(
                        teamId = 2,
                        name = "Debrecen",
                        players = emptyList()
                    )
                ),
                rounds = emptyList()
            )
        )
    ) {}
}