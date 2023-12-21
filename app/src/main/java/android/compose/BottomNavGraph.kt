package android.compose

import android.compose.screens.BookingsScreen
import android.compose.screens.CarsScreen
import android.compose.screens.NotificationsScreen
import android.compose.screens.SettingsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavigationItem.Cars.route, modifier = Modifier) {
        composable(route = BottomNavigationItem.Cars.route) {
            CarsScreen()
        }
        composable(route = BottomNavigationItem.Bookings.route) {
            BookingsScreen()
        }
        composable(route = BottomNavigationItem.Notifications.route) {
            NotificationsScreen()
        }
        composable(route = BottomNavigationItem.Settings.route) {
            SettingsScreen()
        }
    }
}