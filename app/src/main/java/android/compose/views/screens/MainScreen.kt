package android.compose.views.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import android.compose.R
import android.compose.bottombar.BottomNavGraph
import android.compose.bottombar.BottomNavigationItem
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
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
                     }
                 )
        },
        bottomBar = { BottomBar(navController = navController)}
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BottomNavGraph(navController = navController)
        }
    }

}

@Composable
fun BottomBar(navController: NavHostController ) {
    val screens = listOf(
        BottomNavigationItem.Cars,
        BottomNavigationItem.Bookings,
        BottomNavigationItem.Notifications,
        BottomNavigationItem.Settings,
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
                    } == true)
                        {ImageVector.vectorResource(id = screen.selectedIcon)}
                        else ImageVector.vectorResource(id = screen.unselectedIcon),
                    contentDescription = screen.title)
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navController.navigate(screen.route)
        }
    )
}