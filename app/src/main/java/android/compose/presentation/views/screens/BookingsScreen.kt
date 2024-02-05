package android.compose.presentation.views.screens

import android.compose.R
import android.compose.common.Screens
import android.compose.data.local.AuthPreferences
import android.compose.data.repository.customer.ICustomerRepository
import android.compose.presentation.viewmodels.customer.CustomerViewModel
import android.compose.ui.theme.Primary
import android.compose.ui.theme.TextColor
import android.compose.ui.theme.White
import android.compose.util.Resource
import android.compose.util.RetrofitInstance
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun BookingsScreen(navController: NavController) {
    val context = LocalContext.current
    val authPreferences = AuthPreferences(context)
    val customerViewModel: CustomerViewModel = viewModel(factory = CustomerViewModelFactory(authPreferences))

    LaunchedEffect(true) {
        customerViewModel.fetchCustomer()
    }

    val customerState = customerViewModel.customerDetails.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
    ) {
        when (customerState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                customerState.data?.let { customerDetails ->
                    Log.d("customerDetails", customerDetails.toString())
                }
            }
            is Resource.Error -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 140.dp)
                ) {
                    Text(
                        text = "Het lijkt erop dat je niet bent ingelogd",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = TextColor,
                            fontWeight = FontWeight.Medium
                        ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = "Log in om jouw reserveringen te bekijken",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = TextColor,
                        ),
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp, top = 15.dp)) {
                        Button(
                            modifier = Modifier
                                .width(290.dp),
                            contentPadding = PaddingValues(15.dp),
                            shape = RoundedCornerShape(25),
                            onClick = { navController.navigate(Screens.LoginScreen.route) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary,
                                contentColor = White
                            )
                        ) {
                            Text(stringResource(id = R.string.login).uppercase())
                        }
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