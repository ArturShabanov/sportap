package com.woonish.winwin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.woonish.winwin.ui.screens.SportPickerScreen
import com.woonish.winwin.ui.screens.leagues.LeaguesScreen
import com.woonish.winwin.ui.screens.events.EventDetailsScreen
import com.woonish.winwin.ui.screens.events.ScheduleMode
import com.woonish.winwin.ui.screens.events.ScheduleScreen

object Routes {
    const val SPORT_PICKER = "sport_picker"
    const val LEAGUES = "leagues/{sport}"
    const val SCHEDULE_LEAGUE_NEXT = "schedule/league/next/{idLeague}"
    const val SCHEDULE_LEAGUE_PAST = "schedule/league/past/{idLeague}"
    const val SCHEDULE_TEAM_NEXT = "schedule/team/next/{idTeam}"
    const val SCHEDULE_TEAM_LAST = "schedule/team/last/{idTeam}"
    const val SCHEDULE_BY_DAY = "schedule/day/{date}?sport={sport}&league={league}"
    const val EVENT_DETAILS = "event/{idEvent}"
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
        composable(Routes.SCHEDULE_LEAGUE_NEXT) { backStackEntry ->
            val idLeague = backStackEntry.arguments?.getString("idLeague") ?: return@composable
            ScheduleScreen(mode = ScheduleMode.LeagueNext, id = idLeague) { idEvent ->
                navController.navigate("event/$idEvent")
            }
        }
        composable(Routes.SCHEDULE_LEAGUE_PAST) { backStackEntry ->
            val idLeague = backStackEntry.arguments?.getString("idLeague") ?: return@composable
            ScheduleScreen(mode = ScheduleMode.LeaguePast, id = idLeague) { idEvent ->
                navController.navigate("event/$idEvent")
            }
        }
        composable(Routes.SCHEDULE_TEAM_NEXT) { backStackEntry ->
            val idTeam = backStackEntry.arguments?.getString("idTeam") ?: return@composable
            ScheduleScreen(mode = ScheduleMode.TeamNext, id = idTeam) { idEvent ->
                navController.navigate("event/$idEvent")
            }
        }
        composable(Routes.SCHEDULE_TEAM_LAST) { backStackEntry ->
            val idTeam = backStackEntry.arguments?.getString("idTeam") ?: return@composable
            ScheduleScreen(mode = ScheduleMode.TeamLast, id = idTeam) { idEvent ->
                navController.navigate("event/$idEvent")
            }
        }
        composable(Routes.SCHEDULE_BY_DAY) { backStackEntry ->
            val date = backStackEntry.arguments?.getString("date") ?: return@composable
            val sport = backStackEntry.arguments?.getString("sport")
            val league = backStackEntry.arguments?.getString("league")
            ScheduleScreen(mode = ScheduleMode.ByDay, date = date, sport = sport, leagueName = league) { idEvent ->
                navController.navigate("event/$idEvent")
            }
        }
        composable(Routes.EVENT_DETAILS) { backStackEntry ->
            val idEvent = backStackEntry.arguments?.getString("idEvent") ?: return@composable
            EventDetailsScreen(idEvent = idEvent)
        }
    }
}


