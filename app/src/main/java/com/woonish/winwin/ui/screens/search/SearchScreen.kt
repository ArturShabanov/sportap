package com.woonish.winwin.ui.screens.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.woonish.winwin.data.local.entity.TeamEntity

@Composable
fun SearchScreen(onOpenTeam: (String) -> Unit, viewModel: SearchViewModel = hiltViewModel()) {
    var query by remember { mutableStateOf("") }
    val (itemsState, setItems) = remember { mutableStateOf<List<TeamEntity>>(emptyList()) }

    LaunchedEffect(query) {
        setItems(viewModel.search(query))
    }

    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Поиск команды") })
        LazyColumn {
            items(itemsState) { team ->
                AsyncImage(model = team.strTeamBadge, contentDescription = null)
                Text(team.strTeam ?: "-")
            }
        }
    }
}


