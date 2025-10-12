package com.woonish.winwin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.woonish.winwin.ui.screens.SportPickerScreen
import com.woonish.winwin.ui.screens.leagues.LeaguesScreen

object Routes {
    const val SPORT_PICKER = "sport_picker"
    const val LEAGUES = "leagues/{sport}"
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.SPORT_PICKER) {
        composable(Routes.SPORT_PICKER) {
            SportPickerScreen { sport ->
                navController.navigate("leagues/$sport")
            }
        }
        composable(Routes.LEAGUES) { backStackEntry ->
            val sport = backStackEntry.arguments?.getString("sport") ?: "Soccer"
            LeaguesScreen(sport = sport)
        }
    }
}


