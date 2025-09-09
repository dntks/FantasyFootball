package com.dntks.groupstagesimulator.ui.overview.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dntks.groupstagesimulator.data.model.GroupDomainModel

@Composable
fun GroupsList(
    groups: List<GroupDomainModel>,
    onGroupClick: (groupId: Long) -> Unit
) {
    LazyColumn {
        items(groups) { group ->
            Column(
                modifier = Modifier.Companion
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
                    modifier = Modifier.Companion.padding(vertical = 6.dp),
                    fontSize = 20.sp
                )
                group.teams.forEach {
                    Text(text = it.name)
                }
            }
        }
    }
}