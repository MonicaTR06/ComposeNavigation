package com.mvvm.composenavigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.demo.navigation.Route
import com.mvvm.composenavigation.navigation.NoteListRoute
import com.mvvm.composenavigation.navigation.ReminderListRoute
import com.mvvm.composenavigation.navigation.TaskListRoute
import kotlinx.coroutines.launch

@Composable
fun ScreenWithDrawer(
    drawerState: DrawerState,
    currentRoute: String?,
    navigateTo: (Route) -> Unit,
    screenContent: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                ModalDrawerHeader()

                NavigationDrawerItem(
                    label = { Text(text = stringResource(R.string.task_title)) },
                    selected = currentRoute == TaskListRoute::class.qualifiedName,
                    onClick = {
                        navigateTo(TaskListRoute)
                        //Close drawer
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Notes") },
                    selected = currentRoute == NoteListRoute::class.qualifiedName,
                    onClick = {
                        navigateTo(NoteListRoute)
                        //Close drawer
                        coroutineScope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = "Reminder") },
                    selected = currentRoute == ReminderListRoute::class.qualifiedName,
                    onClick = {
                        navigateTo(ReminderListRoute)
                        //Close drawer
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        },
        gesturesEnabled = true
    ) {
        screenContent()
    }
}

@Composable
internal fun ModalDrawerHeader() {
    Text("Compose App", modifier = Modifier.padding(16.dp))

    HorizontalDivider()
}