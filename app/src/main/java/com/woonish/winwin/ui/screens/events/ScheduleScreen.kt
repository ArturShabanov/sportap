package com.woonish.winwin.ui.screens.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woonish.winwin.data.local.entity.EventEntity
import com.woonish.winwin.ui.components.MatchCard

@Composable
fun ScheduleScreen(
    mode: ScheduleMode,
    id: String? = null,
    date: String? = null,
    sport: String? = null,
    leagueName: String? = null,
    viewModel: ScheduleViewModel = hiltViewModel(),
    onOpenEvent: (String) -> Unit
) {
    val (itemsState, setItems) = remember { mutableStateOf<List<EventEntity>>(emptyList()) }

    LaunchedEffect(mode, id, date, sport, leagueName) {
        var data = when (mode) {
            ScheduleMode.LeagueNext -> viewModel.leagueNext(id!!)
            ScheduleMode.LeaguePast -> viewModel.leaguePast(id!!)
            ScheduleMode.TeamNext -> viewModel.teamNext(id!!)
            ScheduleMode.TeamLast -> viewModel.teamLast(id!!)
            ScheduleMode.ByDay -> viewModel.byDay(date!!, sport, leagueName)
        }
        // Fallback: if no upcoming events, show past ones
        if (data.isEmpty()) {
            data = when (mode) {
                ScheduleMode.LeagueNext -> viewModel.leaguePast(id!!)
                ScheduleMode.TeamNext -> viewModel.teamLast(id!!)
                else -> data
            }
        }
        setItems(data)
    }

    Column(Modifier.fillMaxSize().padding(12.dp)) {
        if (mode == ScheduleMode.ByDay && date != null) {
            Row(Modifier.fillMaxWidth()) {
                Button(onClick = {
                    val prev = java.time.LocalDate.parse(date).minusDays(1).toString()
                    viewModel.scopeLoadByDay(prev, sport, leagueName) { setItems(it) }
                }) { Text("Вчера") }
                Button(onClick = {
                    val next = java.time.LocalDate.parse(date).plusDays(1).toString()
                    viewModel.scopeLoadByDay(next, sport, leagueName) { setItems(it) }
                }, modifier = Modifier.padding(start = 8.dp)) { Text("Завтра") }
            }
            Text(text = "Дата: $date")
        }
        if (itemsState.isEmpty()) {
            Text(text = "Нет событий")
        } else {
            LazyColumn {
                items(itemsState) { ev ->
                    val id = ev.idEvent
                    MatchCard(event = ev) {
                        if (!id.isNullOrEmpty()) onOpenEvent(id)
                    }
                }
            }
        }
    }
}

enum class ScheduleMode { LeagueNext, LeaguePast, TeamNext, TeamLast, ByDay }


