package com.woonish.winwin.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.woonish.winwin.data.local.entity.EventEntity

@Composable
fun MatchCard(event: EventEntity, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(text = event.dateEvent ?: "", style = MaterialTheme.typography.labelMedium)
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamBadgeAndName(name = event.strHomeTeam, badgeUrl = event.strHomeTeamBadge)
                Text(text = "vs", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
                TeamBadgeAndName(name = event.strAwayTeam, badgeUrl = event.strAwayTeamBadge, alignEnd = true)
            }
            if (!event.strStatus.isNullOrBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(text = event.strStatus ?: "", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
private fun TeamBadgeAndName(name: String?, badgeUrl: String?, alignEnd: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (!badgeUrl.isNullOrBlank()) {
            AsyncImage(
                model = badgeUrl,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(Modifier.size(8.dp))
        Text(
            text = name ?: "",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = if (alignEnd) TextAlign.End else TextAlign.Start
        )
    }
}


