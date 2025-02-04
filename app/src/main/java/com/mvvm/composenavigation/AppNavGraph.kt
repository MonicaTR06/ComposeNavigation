package com.mvvm.composenavigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.demo.login.presentation.LoginScreen
import com.mvvm.composenavigation.feature.task.list.TaskListScreen
import com.demo.navigation.LoginRoute

import com.demo.navigation.TaskListRoute
import com.demo.navigation.Route
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    startDestination: Route = LoginRoute,
) {
    val navController: NavHostController = rememberNavController()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute =
        currentNavBackStackEntry?.destination?.route ?: startDestination::class.qualifiedName

    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<TaskListRoute> {
            ScreenWithDrawer(drawerState, currentRoute, navController::navigate) {
                TaskListScreen(
                    openDrawer = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    onAddTask = {
                        //navController.navigate(CreateListRoute)
                    }
                )
            }
        }
        composable<LoginRoute> {
            ScreenWithDrawer(drawerState, currentRoute, navController::navigate) {
                LoginScreen(
                    onSuccessLogin = {
                        navController.navigate(TaskListRoute) {
                            popUpTo<LoginRoute> { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}