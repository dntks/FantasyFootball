package com.dntks.groupstagesimulator.ui.groupstatistics.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dntks.groupstagesimulator.data.model.GroupDomainModel
import com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel.GroupStatisticsViewModel

@Composable
fun TopBar(
    group: GroupDomainModel,
    viewModel: GroupStatisticsViewModel,
    onBack: () -> Unit,
) {
    Row(
        Modifier.Companion
            .fillMaxWidth()
            .padding(bottom = 15.dp),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
        Text(
            group.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Companion.SemiBold)
        )
        Spacer(Modifier.Companion.weight(1f))

        group.id?.let {
            if (group.rounds.isNotEmpty()) {
                Button(onClick = {
                    viewModel.deleteRounds(it)
                }) {
                    Text("Reset")
                }
            } else {
                Button(onClick = {
                    viewModel.generateAllMatches(it)
                }) {
                    Text("Simulate matches")
                }
            }
        }
    }
}