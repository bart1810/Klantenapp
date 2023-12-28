package android.compose.common.bottombar

import android.compose.presentation.views.screens.BookingsScreen
import android.compose.presentation.views.screens.cars.CarsScreen
import android.compose.presentation.views.screens.NotificationsScreen
import android.compose.presentation.views.screens.SettingsScreen
import android.compose.presentation.views.screens.auth.LoginScreen
import android.compose.presentation.views.screens.auth.SignUpScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController) {
    val token = null

    NavHost(navController = navController, startDestination = BottomNavigationItem.Cars.route, modifier = Modifier) {
        composable(route = BottomNavigationItem.Cars.route) {
            CarsScreen()
        }
        composable(route = BottomNavigationItem.Bookings.route) {
            if (!token.isNullOrBlank()) {
                BookingsScreen()
            } else LoginScreen()
        }
        composable(route = BottomNavigationItem.Notifications.route) {
            if (!token.isNullOrBlank()) {
                NotificationsScreen()
            } else SignUpScreen()
        }
        composable(route = BottomNavigationItem.Settings.route) {
            SettingsScreen()
        }
    }
}