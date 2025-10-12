package com.woonish.winwin.ui.screens.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.woonish.winwin.data.local.entity.EventEntity

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
        val data = when (mode) {
            ScheduleMode.LeagueNext -> viewModel.leagueNext(id!!)
            ScheduleMode.LeaguePast -> viewModel.leaguePast(id!!)
            ScheduleMode.TeamNext -> viewModel.teamNext(id!!)
            ScheduleMode.TeamLast -> viewModel.teamLast(id!!)
            ScheduleMode.ByDay -> viewModel.byDay(date!!, sport, leagueName)
        }
        setItems(data)
    }

    Column(Modifier.fillMaxSize()) {
        LazyColumn {
            items(itemsState) { ev ->
                Text(text = ev.strEvent ?: "-", modifier = Modifier)
            }
        }
    }
}

enum class ScheduleMode { LeagueNext, LeaguePast, TeamNext, TeamLast, ByDay }


