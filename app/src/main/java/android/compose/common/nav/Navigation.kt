package android.compose.common.nav

import CarDetailScreen
import android.compose.common.Screens
import android.compose.presentation.views.screens.BookingsScreen
import android.compose.presentation.views.screens.cars.CarsScreen
import android.compose.presentation.views.screens.NotificationsScreen
import android.compose.presentation.views.screens.account.DamageFormScreen
import android.compose.presentation.views.screens.account.FaqScreen
import android.compose.presentation.views.screens.account.ProfileScreen
import android.compose.presentation.views.screens.auth.ChangePasswordScreen
import android.compose.presentation.views.screens.auth.ForgotPasswordScreen
import android.compose.presentation.views.screens.auth.LoginScreen
import android.compose.presentation.views.screens.auth.SignUpScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screens.CarScreen.route, modifier = Modifier) {
        composable(route = Screens.CarScreen.route) {
            CarsScreen(navController)
        }
        composable(route = Screens.CarScreen.route + "/{carId}",
            arguments = listOf(
                navArgument("carId") {
                    nullable = false
                    type = NavType.StringType
                })
            ) { entry ->
            CarDetailScreen(navController, entry.arguments?.getString("carId"))
        }
        composable(route = Screens.BookingsScreen.route) {
            BookingsScreen(navController)
        }
        composable(route = Screens.NotificationsScreen.route) {
            NotificationsScreen(navController)
        }
        composable(route = Screens.Profile.route) {
            ProfileScreen(navController)
        }
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screens.RegisterScreen.route) {
            SignUpScreen(navController)
        }
        composable(route = Screens.DamageFormScreen.route) {
            DamageFormScreen(navController)
        }
        composable(route = Screens.FaqScreen.route) {
            FaqScreen(navController)
        }
        composable(route = Screens.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController)
        }
        composable(route = Screens.ChangePasswordScreen.route) {
            ChangePasswordScreen(navController)
        }
    }
}