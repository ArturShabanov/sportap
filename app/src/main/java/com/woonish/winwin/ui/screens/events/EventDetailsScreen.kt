package com.woonish.winwin.ui.screens.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woonish.winwin.data.local.entity.EventEntity

@Composable
fun EventDetailsScreen(
    idEvent: String,
    viewModel: EventDetailsViewModel = hiltViewModel()
) {
    val (eventState, setEvent) = remember { mutableStateOf<EventEntity?>(null) }

    LaunchedEffect(idEvent) {
        setEvent(viewModel.loadEvent(idEvent))
    }

    Column(Modifier.fillMaxSize()) {
        Text(text = eventState?.strEvent ?: "Загрузка…")
        Spacer(Modifier.height(8.dp))
        Text(text = "Дата: ${eventState?.dateEvent ?: "-"}")
        Text(text = "Статус: ${eventState?.strStatus ?: "-"}")
        Text(text = "Стадион: ${eventState?.strVenue ?: "-"}")
    }
}


