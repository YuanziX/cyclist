package dev.yuanzix.cyclist.core.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.DirectionsBike
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.yuanzix.cyclist.core.presentation.ObserveAsEvents
import dev.yuanzix.cyclist.core.presentation.toString
import dev.yuanzix.cyclist.dash.presentation.home.HomeEvent
import dev.yuanzix.cyclist.dash.presentation.home.HomeScreen
import dev.yuanzix.cyclist.dash.presentation.home.HomeViewModel
import dev.yuanzix.cyclist.dash.presentation.mycycle.MyCycleEvent
import dev.yuanzix.cyclist.dash.presentation.mycycle.MyCycleScreen
import dev.yuanzix.cyclist.dash.presentation.mycycle.MyCycleViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardNavigation(
    modifier: Modifier = Modifier, navController: NavController
) {
    val dashboardNavController = rememberNavController()

    Scaffold(bottomBar = { DashboardBottomNav(navController = dashboardNavController) }) { paddingValues ->
        NavHost(
            navController = dashboardNavController,
            startDestination = Destination.Dash.Home,
            modifier = modifier.padding(paddingValues)
        ) {
            composable<Destination.Dash.Home> {
                val viewModel: HomeViewModel = koinViewModel()
                val ctx = LocalContext.current

                ObserveAsEvents(
                    events = viewModel.events,
                ) {
                    when (it) {
                        is HomeEvent.NavigateToCycleWithID -> {
                            navController.navigate(Destination.Dash.BicycleDetails(it.id))
                        }

                        is HomeEvent.Error -> {
                            Toast.makeText(
                                ctx, it.error.toString(ctx), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                HomeScreen(
                    state = viewModel.state.collectAsStateWithLifecycle().value,
                    onAction = { action -> viewModel.onAction(action) },
                    modifier = modifier
                )
            }

            composable<Destination.Dash.MyCycle> {
                val viewModel: MyCycleViewModel = koinViewModel()
                val ctx = LocalContext.current

                ObserveAsEvents(
                    events = viewModel.events
                ) {
                    when (it) {
                        is MyCycleEvent.Error -> {
                            Toast.makeText(
                                ctx, it.error.toString(ctx), Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                MyCycleScreen(
                    state = viewModel.state.collectAsStateWithLifecycle().value,
                    onAction = { viewModel.onAction(it) }
                )
            }
        }
    }
}

@Composable
fun DashboardBottomNav(navController: NavHostController) {
    val navItems = listOf(
        NavItem(
            name = "Home", icon = Icons.Filled.Home, route = Destination.Dash.Home
        ),
        NavItem(
            name = "My Cycle",
            icon = Icons.AutoMirrored.Filled.DirectionsBike,
            route = Destination.Dash.MyCycle,
        ),
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navItems.forEach { navItem ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == navItem.route::class.qualifiedName } == true
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.name
                    )
                },
                label = {
                    Text(
                        text = navItem.name,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                },
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground
                ),
            )
        }
    }
}