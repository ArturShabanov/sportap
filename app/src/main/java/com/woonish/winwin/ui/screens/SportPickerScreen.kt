package com.woonish.winwin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SportPickerScreen(onPick: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Выберите вид спорта")
        Button(onClick = { onPick("Soccer") }) { Text("Футбол") }
        Button(onClick = { onPick("Basketball") }) { Text("Баскетбол") }
        Button(onClick = { onPick("Ice Hockey") }) { Text("Хоккей") }
    }
}


