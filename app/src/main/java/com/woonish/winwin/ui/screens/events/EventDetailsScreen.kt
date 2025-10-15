package com.woonish.winwin.ui.screens.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.woonish.winwin.data.local.entity.EventEntity

@Composable
fun EventDetailsScreen(
    idEvent: String,
    viewModel: EventDetailsViewModel = hiltViewModel(),
    onOpenTeam: (String) -> Unit = {}
) {
    val (eventState, setEvent) = remember { mutableStateOf<EventEntity?>(null) }

    LaunchedEffect(idEvent) {
        setEvent(viewModel.loadEvent(idEvent))
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = eventState?.strEvent ?: "Загрузка…", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(12.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            TeamBlock(
                name = eventState?.strHomeTeam,
                badge = eventState?.strHomeTeamBadge,
                onClick = { eventState?.idHomeTeam?.let(onOpenTeam) }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val hs = eventState?.intHomeScore
                val ascore = eventState?.intAwayScore
                val score = if (!hs.isNullOrBlank() && !ascore.isNullOrBlank()) "$hs : $ascore" else "vs"
                Text(text = score, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = eventState?.strStatus ?: "")
            }
            TeamBlock(
                name = eventState?.strAwayTeam,
                badge = eventState?.strAwayTeamBadge,
                onClick = { eventState?.idAwayTeam?.let(onOpenTeam) }
            )
        }
        Spacer(Modifier.height(16.dp))
        Text(text = "Дата: ${eventState?.dateEvent ?: "-"}")
        Text(text = "Лига: ${eventState?.strLeague ?: "-"}")
        Text(text = "Сезон: ${eventState?.strSeason ?: "-"}")
        Text(text = "Стадион: ${eventState?.strVenue ?: "-"}")
    }
}

@Composable
private fun TeamBlock(name: String?, badge: String?, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (!badge.isNullOrBlank()) {
            AsyncImage(model = badge, contentDescription = null, modifier = Modifier.size(48.dp))
        }
        Button(onClick = onClick, enabled = !name.isNullOrBlank()) {
            Text(text = name ?: "-")
        }
    }
}


