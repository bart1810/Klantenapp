package android.compose.presentation.views.screens

import android.compose.common.Screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun BookingsScreen(navController: NavController) {

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
        Button(
            onClick = {navController.navigate(Screens.LoginScreen.route) },
            colors = ButtonDefaults.buttonColors(
                Color(0xFFFFA500) // Orange color
            )
        ) {
            Text("Inloggen")
        }
    }
}