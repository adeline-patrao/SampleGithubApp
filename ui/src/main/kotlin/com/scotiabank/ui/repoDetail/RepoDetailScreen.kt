package com.scotiabank.ui.repoDetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.scotiabank.domain.model.Repo
import com.scotiabank.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailScreen(modifier: Modifier = Modifier, repo: Repo, starBadgeEnabled: Boolean) {
    Scaffold(topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.detail_page_title)) }) }) { paddingValues ->
        Surface(modifier = modifier.padding(paddingValues)) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                RepoInfoCard(repo = repo)
                RepoStatsCard(modifier = modifier, repo.forks ?: 0, starBadgeEnabled)
            }
        }
    }
}

@Composable
fun RepoInfoCard(modifier: Modifier = Modifier, repo: Repo) {
    Card(
        elevation = CardDefaults.cardElevation(),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Text(
                text = buildString { append(repo.name ?: "N/A") },
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 8.dp)
            )

            Text(
                text = buildString {
                    append("Description: ")
                    append(repo.description ?: "No description available")
                },
                modifier = modifier.padding(bottom = 16.dp)
            )

            Text(
                text = buildString {
                    append("Last updated: ")
                    append(repo.updatedAt ?: "N/A")
                },
                modifier = modifier.padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
fun RepoStatsCard(modifier: Modifier = Modifier, totalForks: Int, startBadgeEnabled: Boolean) {
    Card(elevation = CardDefaults.cardElevation(), modifier = Modifier.padding(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Forks: $totalForks",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.weight(1f)
                )
                AnimatedVisibility(visible = startBadgeEnabled) {
                    Badge(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ) {
                        Text(
                            text = "*",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
