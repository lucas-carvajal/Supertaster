package com.carvajal.lucas.supertaster.composables

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.carvajal.lucas.supertaster.auth.Profile
import com.carvajal.lucas.supertaster.data.AppRepository
import com.carvajal.lucas.supertaster.ui.*
import com.carvajal.lucas.supertaster.ui.theme.SupertasterTheme
import com.carvajal.lucas.supertaster.viewmodels.AddViewModel
import com.carvajal.lucas.supertaster.viewmodels.AddViewModelFactory
import com.carvajal.lucas.supertaster.viewmodels.CookbookViewModel
import com.carvajal.lucas.supertaster.viewmodels.DashboardViewModel

class SupertasterApp {

    private val items = listOf(
        BottomBarScreen.Dashboard,
        BottomBarScreen.Cookbooks,
        BottomBarScreen.Add,
        BottomBarScreen.WeeklySchedule,
        BottomBarScreen.ShoppingList
    )

    @Composable
    fun SupertasterAppScreen(
        dashboardViewModel: DashboardViewModel,
        cookbookViewModel: CookbookViewModel,
        addViewModel: AddViewModel
    ) {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomNavigation {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    items.forEach { screen ->
                        AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
                    }
                }
            }
        ) { innerPadding ->
            NavHost(navController, startDestination = BottomBarScreen.Dashboard.route, Modifier.padding(innerPadding)) {
                composable(BottomBarScreen.Dashboard.route) { DashboardScreen(dashboardViewModel) }
                composable(BottomBarScreen.Cookbooks.route) { CookbooksScreen(cookbookViewModel) }
                composable(BottomBarScreen.Add.route) { AddScreen(addViewModel) }
                composable(BottomBarScreen.WeeklySchedule.route) { WeeklyScheduleScreen() }
                composable(BottomBarScreen.ShoppingList.route) { ShoppingListScreen() }
                composable("profile") { Profile() }
            }
        }
    }

}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
    BottomNavigationItem(
        icon = { Icon(screen.icon, contentDescription = stringResource(screen.resourceId)) },
        selected = isSelected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
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


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SupertasterTheme {
        SupertasterApp()
    }
}
