package com.mvvm.composenavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mvvm.composenavigation.feature.task.list.TaskListScreen
import com.mvvm.composenavigation.feature.task.presentation.AddTaskScreen
import com.mvvm.composenavigation.navigation.AddTaskRoute
import com.mvvm.composenavigation.navigation.Route
import com.mvvm.composenavigation.navigation.TaskListRoute
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    startDestination: Route = TaskListRoute,
) {
    val navController: NavHostController = rememberNavController()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Compose App", modifier = Modifier.padding(16.dp))
                HorizontalDivider()

                NavigationDrawerItem(
                    label = {
                        Text(text = stringResource(R.string.task_title) )
                    },
                    selected = currentRoute == TaskListRoute::class.qualifiedName,
                    onClick = {
                        //navigate
                        navController.navigate(TaskListRoute)
                        //Close drawer
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(text = "Notes")
                    },
                    selected = false,
                    onClick = {
                        //Close drawer
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = {
                        Text(text = "Reminder")
                    },
                    selected = false,
                    onClick = {
                        //Close drawer
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        },
        gesturesEnabled = true
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier
        ) {
            composable<TaskListRoute> {
                TaskListScreen(
                    openDrawer = {
                        coroutineScope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    onAddTask = {
                        navController.navigate(AddTaskRoute)
                    }
                )
            }
            composable<AddTaskRoute> {
                AddTaskScreen()
            }
        }
    }

}