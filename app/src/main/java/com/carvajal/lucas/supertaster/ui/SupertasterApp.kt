package com.carvajal.lucas.supertaster.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme

class SupertasterApp {

    private val items = listOf(
        Screen.Dashboard,
        Screen.Cookbooks,
        Screen.Add,
        Screen.WeeklySchedule,
        Screen.ShoppingList
    )

    @Composable
    fun SupertasterApp() {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        BottomNavigationItem(
                            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
//            NavHost(navController, startDestination = Screen.Dashboard.route, Modifier.padding(innerPadding)) {
//                composable(Screen.Dashboard.route) { Screen.Dashboard(navController) }
//                composable(Screen.Cookbooks.route) { Screen.Cookbooks(navController) }
//            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SupertasterTheme {
        SupertasterApp()
    }
}