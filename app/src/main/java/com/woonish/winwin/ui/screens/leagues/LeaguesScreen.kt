package com.woonish.winwin.ui.screens.leagues

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
import com.woonish.winwin.data.local.entity.LeagueEntity
import com.woonish.winwin.ui.screens.leagues.LeaguesViewModel

@Composable
fun LeaguesScreen(sport: String, viewModel: LeaguesViewModel = hiltViewModel()) {
    val (itemsState, setItems) = remember { mutableStateOf<List<LeagueEntity>>(emptyList()) }

    LaunchedEffect(sport) {
        viewModel.refresh()
        setItems(viewModel.getLeagues(sport))
    }

    Column(Modifier.fillMaxSize()) {
        Text("Лиги: $sport")
        LazyColumn {
            items(itemsState) { league ->
                Text(text = league.strLeague ?: "-")
            }
        }
    }
}


