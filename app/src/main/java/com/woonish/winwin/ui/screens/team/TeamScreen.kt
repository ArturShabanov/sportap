package com.woonish.winwin.ui.screens.team

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.woonish.winwin.data.local.entity.TeamEntity

@Composable
fun TeamScreen(idTeam: String, viewModel: TeamViewModel = hiltViewModel()) {
    val (teamState, setTeam) = remember { mutableStateOf<TeamEntity?>(null) }

    LaunchedEffect(idTeam) {
        setTeam(viewModel.loadTeam(idTeam))
    }

    Column(Modifier.fillMaxSize()) {
        AsyncImage(model = teamState?.strTeamBadge, contentDescription = null)
        Text(text = teamState?.strTeam ?: "")
        Text(text = teamState?.strStadium ?: "")
    }
}


