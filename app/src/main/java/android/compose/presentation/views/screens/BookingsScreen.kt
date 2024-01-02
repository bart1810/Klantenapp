package android.compose.presentation.views.screens

import android.compose.common.Screens
import android.compose.data.local.AuthPreferences
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import kotlinx.coroutines.flow.first

@Composable
fun BookingsScreen(navController: NavController) {
    val context = LocalContext.current
    val token = AuthPreferences(context).getToken()
    Log.d("TOKEN", token)

    if (token.isBlank()) {
        navController.navigate(Screens.LoginScreen.route)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
     Text(
         text = "Bookings",
         fontSize = MaterialTheme.typography.headlineMedium.fontSize,
         fontWeight = FontWeight.Bold,
         color = Color.Black
         )
    }
}