package com.scotiabank.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.scotiabank.domain.model.Repo
import com.scotiabank.ui.repoDetail.RepoDetailScreen
import com.scotiabank.ui.userRepoList.HomeScreen

@Composable
fun AppNavigator(
    startDestination: String = NavigationItem.Home.route,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(navController = navController)
            // Use this to scroll to make the Home screen scrollable along with the searchbar
            // HomeScreenScrollable(navController = navController)
        }
        composable(
            route = NavigationItem.Detail.route + "/{starBadgeEnabled}",
            arguments = listOf(
                navArgument(name = "starBadgeEnabled") {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) { entry ->
            val repo = navController.previousBackStackEntry?.savedStateHandle?.get<Repo>("repo")
            val starBadgeEnabled = entry.arguments?.getBoolean("starBadgeEnabled") ?: false
            repo?.let { RepoDetailScreen(repo = it, starBadgeEnabled = starBadgeEnabled) }
        }
    }
}

enum class Screen {
    HOME,
    DETAIL
}

sealed class NavigationItem(val route: String) {
    data object Home : NavigationItem(route = Screen.HOME.name)
    data object Detail : NavigationItem(route = Screen.DETAIL.name)

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}

