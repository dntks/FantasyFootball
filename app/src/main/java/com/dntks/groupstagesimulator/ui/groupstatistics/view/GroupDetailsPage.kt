package com.dntks.groupstagesimulator.ui.groupstatistics.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel.GroupStatisticsUiState
import com.dntks.groupstagesimulator.ui.groupstatistics.viewmodel.GroupStatisticsViewModel

/**
 * Page containing group and round statistics
 */
@Composable
fun GroupDetailsPage(
    groupStatisticsViewModel: GroupStatisticsViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState by groupStatisticsViewModel.selectedGroup.collectAsStateWithLifecycle()
    val groupStatistics by groupStatisticsViewModel.groupStatistics.collectAsStateWithLifecycle()

    when (uiState) {
        GroupStatisticsUiState.Error -> Text("Error")
        GroupStatisticsUiState.Loading -> Text("Loading")
        is GroupStatisticsUiState.Success -> {
            GroupDetailsView(
                viewModel = groupStatisticsViewModel,
                modifier = Modifier,
                group = (uiState as GroupStatisticsUiState.Success).group,
                groupStatistics = groupStatistics,
                onBack = onBack
            )
        }
    }
}

@Composable
@Preview
fun GroupOverviewPreview() {
    GroupDetailsPage(
        onBack = {}
    )
}
