package com.woonish.winwin.ui.screens.leagues

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.woonish.winwin.data.local.entity.LeagueEntity
import com.woonish.winwin.ui.screens.leagues.LeaguesViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun LeaguesScreen(sport: String, viewModel: LeaguesViewModel = hiltViewModel(), onOpenLeague: (String) -> Unit = {}) {
    val (itemsState, setItems) = remember { mutableStateOf<List<LeagueEntity>>(emptyList()) }
    val (isLoading, setLoading) = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(sport) {
        setLoading(true)
        viewModel.refresh()
        setItems(viewModel.getLeagues(sport))
        setLoading(false)
    }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Лиги: $sport (${itemsState.size})")
        when {
            isLoading -> {
                CircularProgressIndicator()
            }
            itemsState.isEmpty() -> {
                Text("Нет лиг. Проверьте интернет и попробуйте снова.")
                Button(onClick = {
                    scope.launch {
                        setLoading(true)
                        viewModel.refresh()
                        setItems(viewModel.getLeagues(sport))
                        setLoading(false)
                    }
                }) { Text("Повторить") }
            }
            else -> {
                LazyColumn {
                    items(itemsState) { league ->
                        val id = league.idLeague
                        Text(
                            text = league.strLeague ?: "-",
                            modifier = Modifier.clickable(enabled = id.isNotEmpty()) {
                                onOpenLeague(id)
                            }
                        )
                    }
                }
            }
        }
    }
}


