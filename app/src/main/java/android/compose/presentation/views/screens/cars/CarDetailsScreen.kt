import android.compose.data.local.AuthPreferences
import android.compose.data.remote.response.CarItemResponse
import android.compose.data.repository.cars.CarsRepositoryImplementation
import android.compose.presentation.viewmodels.cars.CarDetailViewModel
import android.compose.presentation.viewmodels.cars.CarsViewModel
import android.compose.util.Resource
import android.compose.util.RetrofitInstance
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarDetailScreen(navController: NavController, carId: String?, carDetailViewModel: CarDetailViewModel = hiltViewModel()) {

    LaunchedEffect(carId) {
        carId?.let {
            carDetailViewModel.fetchCarDetails(it)
        }
    }

    var openBookingBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true)
    val carDetailsState = carDetailViewModel.carDetails.collectAsState().value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)) {
        when (carDetailsState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                carDetailsState.data?.let { carDetails ->
                    AsyncImage(
                        model = "https://www.autohurenrhodos.nl/wp-content/uploads/2022/10/auto-huren-rhodos-min.png",
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
                            onClick = { openBookingBottomSheet = true },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFFFA500) // Orange color
                            )
                        ) {
                            Text("Boeken")
                        }
                    }
                    if (openBookingBottomSheet) {
                        ModalBottomSheet(
                            sheetState = bottomSheetState,
                            onDismissRequest = { openBookingBottomSheet = false },
                            dragHandle = {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    BottomSheetDefaults.DragHandle()
                                    Text(text = "Opties", style = MaterialTheme.typography.titleLarge)
                                    Spacer(modifier = Modifier.height(10.dp))
                                }
                            }
                        ) {
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(40.dp)
                                .clickable(
                                    onClick = {
                                    }),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 5.dp, end = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Vanaf")


                                }
                            }
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(10.dp)
                                .clickable(
                                    onClick = {

                                    }),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 5.dp, end = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Tot")
                                 
                                }
                            }
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(10.dp)
                                .clickable(
                                    onClick = {

                                    }),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 5.dp, end = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "Kilometerstand")

                                }
                            }
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
        Row(modifier = Modifier.padding(5.dp)) {
            Text("Engine Size: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.engineSize}")
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Text("Benzine type: ", fontWeight = FontWeight.Bold)
            Text(carDetails.fuel)
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Text("Model jaar: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.modelYear}")
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Text("Aantal zitplaatsen: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.nrOfSeats}")
        }
        Row(modifier = Modifier.padding(5.dp)) {
            Text("Prijs: ", fontWeight = FontWeight.Bold)
            Text("${carDetails.price}")
        }
    }
}
