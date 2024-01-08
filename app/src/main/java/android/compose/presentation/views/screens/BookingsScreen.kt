package android.compose.presentation.views.screens

import android.compose.R
import android.compose.common.Screens
import android.compose.data.local.AuthPreferences
import android.compose.presentation.viewmodels.auth.LoginViewModel
import android.compose.util.Resource
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun BookingsScreen(navController: NavController, loginViewModel: LoginViewModel = hiltViewModel()) {

    LaunchedEffect(true) {
        loginViewModel.isUserAuthenticated()
    }

    val isAuthenticated = loginViewModel.authenticated.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        when (isAuthenticated) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                Text("Ingelogd")
            }
            is Resource.Error -> {
                Text("Niet ingelogd")
                Button(
                    onClick = { navController.navigate(Screens.LoginScreen.route) },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFFFA500) // Orange color
                    )
                ) {
                    Text("Boek nu!")
                }
            }
            null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}