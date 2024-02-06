package android.compose.presentation.views.screens

import android.compose.R
import android.compose.common.Screens
import android.compose.data.local.AuthPreferences
import android.compose.data.remote.objects.RentalState
import android.compose.data.remote.response.RentalResponse
import android.compose.data.repository.customer.ICustomerRepository
import android.compose.presentation.viewmodels.customer.CustomerViewModel
import android.compose.ui.theme.Primary
import android.compose.ui.theme.Secondary
import android.compose.ui.theme.TextColor
import android.compose.ui.theme.White
import android.compose.util.Resource
import android.compose.util.RetrofitInstance
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
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

                    if (customerDetails.rentals.isNullOrEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(top = 140.dp)
                        ) {
                            Image(
                                painterResource(id = R.drawable.documents_mad),
                                contentDescription = "documents",
                                modifier = Modifier
                                    .width(250.dp)
                            )
                            Text(
                                text = "Je hebt nog geen auto's gehuurd",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = TextColor,
                                    fontWeight = FontWeight.Medium
                                ),
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 10.dp)
                                    ,
                                text = "Hier worden al jouw eerdere boekingen getoond nadat je je eerste auto hebt gehuurd",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Secondary,
                                    textAlign = TextAlign.Center
                                ),
                            )
                        }
                    } else {
                        val rentalList = customerDetails.rentals.toList()
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(rentalList.size) { index ->
                                RentalItem(rentalList[index])
                            }
                        }
                    }
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

fun rentalStateToReadable(state: RentalState): String {
    return when (state) {
        RentalState.ACTIVE -> "Actief"
        RentalState.PICKUP -> "Kan opgepikt worden"
        RentalState.RESERVED -> "Gereserveerd"
        RentalState.RETURNED -> "Teruggebracht"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun rentalDateToReadableDate(fromDate: String, toDate: String): String {
    return fromDate.format(DateTimeFormatter.ofPattern("d MMM uuuu HH:mm")) + " tot " + toDate.format(DateTimeFormatter.ofPattern("d MMM uuuu HH:mm"))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RentalItem(rental: RentalResponse) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row {
                AsyncImage(
                    modifier = Modifier
                        .width(80.dp)
                        .padding(end = 10.dp),
                    model = "https://www.autohurenrhodos.nl/wp-content/uploads/2022/10/auto-huren-rhodos-min.png",
                    contentDescription = "Car image",
                )
                Column(
                ) {
                    Text(
                        text = rentalStateToReadable(state = rental.state),
                        fontSize = 15.sp,
                        color = Secondary,
                    )
                    Text(
                        text = "${rental.car?.brand} ${rental.car?.model} ${rental.car?.body?.lowercase()}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = rentalDateToReadableDate(rental.fromDate, rental.toDate),
                        fontSize = 15.sp,
                        color = Secondary,
                    )
                }
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
                authPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}