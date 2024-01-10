package android.compose.presentation.views.screens

import android.compose.common.Screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun BookingsScreen(navController: NavController) {

    navController.navigate(Screens.LoginScreen.route)

    Column(modifier = Modifier.fillMaxSize()) {
    }
}