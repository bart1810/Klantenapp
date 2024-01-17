package android.compose.presentation.views.screens

import android.compose.common.Screens
import android.compose.data.local.AuthPreferences
import android.compose.data.repository.cars.CarsRepositoryImplementation
import android.compose.data.repository.customer.ICustomerRepository
import android.compose.presentation.viewmodels.cars.CarDetailViewModel
import android.compose.presentation.viewmodels.customer.CustomerViewModel
import android.compose.util.Resource
import android.compose.util.RetrofitInstance
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun BookingsScreen(navController: NavController) {
    val context = LocalContext.current
    val authPreferences = AuthPreferences(context)
    val customerViewModel: CustomerViewModel = viewModel(factory = CustomerViewModelFactory(authPreferences))

    LaunchedEffect(true) {
        customerViewModel.fetCustomer()
    }

    val customerState = customerViewModel.customerDetails.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        when (customerState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                customerState.data?.let { customerDetails ->

                }
            }
            is Resource.Error -> {
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
                        onClick = { navController.navigate(Screens.LoginScreen.route) },
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFFFA500) // Orange color
                        )
                    ) {
                        Text("Inloggen")
                    }
                }
            }
            null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

class CustomerViewModelFactory(
    private val authPreferences: AuthPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerViewModel::class.java)) {
            return CustomerViewModel(
                ICustomerRepository(RetrofitInstance.autoMaatApi),
                authPreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}