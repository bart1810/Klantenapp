import android.compose.data.local.AuthPreferences
import android.compose.data.remote.response.CarItemResponse
import android.compose.data.repository.cars.CarsRepositoryImplementation
import android.compose.presentation.viewmodels.cars.CarDetailViewModel
import android.compose.util.Resource
import android.compose.util.RetrofitInstance
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun CarDetailScreen(navController: NavController, carId: String?) {
    val context = LocalContext.current
    val authPreferences = AuthPreferences(context)
    val carDetailViewModel: CarDetailViewModel = viewModel(factory = CarDetailViewModelFactory(authPreferences))

    LaunchedEffect(carId) {
        carId?.let {
            carDetailViewModel.fetchCarDetails(it)
        }
    }

    val carDetailsState = carDetailViewModel.carDetails.collectAsState().value

    Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }

        when (carDetailsState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                carDetailsState.data?.let { carDetails ->
                    AsyncImage(
                        model = "https://parkers-images.bauersecure.com/wp-images/18290/930x620/90-vauxhall-corsa-electric-best-small-cars.jpg",
                        contentDescription = "Car image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    TableLayout(carDetails = carDetails)

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp, bottom = 10.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = { },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFFFA500) // Orange color
                            )
                        ) {
                            Text("Boek nu!")
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text("Error fetching car details", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun TableLayout(carDetails: CarItemResponse) {
    Column {
        Row(modifier = Modifier.padding(8.dp)){
            Text(
                text = "${carDetails.brand} ${carDetails.model} ${carDetails.body.lowercase()}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("Engine Size: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.engineSize}")
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("Benzine type: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.fuel}")
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("Model jaar: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.modelYear}")
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("Aantal zitplaatsen: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.nrOfSeats}")
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text("Prijs: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.price}")
        }
    }
}

class CarDetailViewModelFactory(
    private val authPreferences: AuthPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarDetailViewModel::class.java)) {
            return CarDetailViewModel(
                CarsRepositoryImplementation(RetrofitInstance.autoMaatApi),
                authPreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
