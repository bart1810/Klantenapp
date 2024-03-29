package android.compose.presentation.views.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import android.compose.R
import android.compose.common.nav.Navigation
import android.compose.common.nav.BottomNavigationItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Navigation(navController = navController)
    val bottomBarTabs = BottomNavigationItem.entries.toTypedArray()
    val bottomBarRoutes = bottomBarTabs.map { it.route }

    val showComponent = navController.currentBackStackEntryAsState().value?.destination?.route in bottomBarRoutes

    Scaffold(
        topBar = {
                 CenterAlignedTopAppBar(
                     colors = TopAppBarDefaults.topAppBarColors(),
                     title = {
                         Text(
                             text = stringResource(id = R.string.app_name),
                             fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                             fontWeight = FontWeight.Bold
                         )
                     },
                     navigationIcon = {
                         if (!showComponent) {
                             IconButton(onClick = { navController.navigateUp() }) {
                                 Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                             }
                         }
                     },
                 )
        },
        bottomBar = {
            if (showComponent) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Navigation(navController = navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController ) {
    val screens = listOf(
        BottomNavigationItem.Cars,
        BottomNavigationItem.Bookings,
        BottomNavigationItem.Favorites,
        BottomNavigationItem.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Transparent
    ) {
        screens.forEach { screen ->
            AddItem(screen = screen, currentDestination = currentDestination, navController = navController)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.AddItem(
    screen: BottomNavigationItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.White
        ),
        icon = {
            BadgedBox(
                badge = {
                    if (screen.badgeCount != null) {
                        Badge {
                            Text(text = screen.badgeCount.toString())
                        }
                    } else if (screen.hasNews) {
                        Badge()
                    }

                }) {
                Icon(
                    imageVector = if (currentDestination?.hierarchy?.any {
                            it.route == screen.route
                        } == true) {
                        ImageVector.vectorResource(id = screen.selectedIcon)
                    } else ImageVector.vectorResource(id = screen.unselectedIcon),
                    contentDescription = screen.title)
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}