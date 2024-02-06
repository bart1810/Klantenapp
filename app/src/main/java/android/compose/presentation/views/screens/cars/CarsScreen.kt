package android.compose.presentation.views.screens.cars

import android.compose.common.Screens
import android.compose.util.RetrofitInstance
import android.compose.data.repository.cars.CarsRepositoryImplementation
import android.compose.data.remote.response.CarItemResponse
import android.compose.presentation.viewmodels.cars.CarsViewModel
import android.compose.ui.components.BottomSheet
import android.compose.ui.components.ToastMessage
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CarsScreen(navController: NavController) {
    val viewModel: CarsViewModel = viewModel(factory = CarsViewModelFactory())

    val selectedBrandFilters = viewModel.selectedBrandFilters.collectAsState().value
    val selectedFuelFilters = viewModel.selectedFuelTypes.collectAsState().value
    val selectedBodyFilters = viewModel.selectedBodyTypes.collectAsState().value

    val noCarsFound = viewModel.noCarsFound.collectAsState().value
    val carsList = if (noCarsFound) {
        viewModel.allCars.collectAsState().value
    } else {
        viewModel.cars.collectAsState().value
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { toastMessage ->
            when (toastMessage) {
                ToastMessage.GenericError -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
                ToastMessage.NoCarsFound -> {
                    Toast.makeText(context, "No cars found with the selected filters", Toast.LENGTH_LONG).show()
                    viewModel.resetFilters()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        if (carsList.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            BottomSheet(
                selectedBrandFilters = selectedBrandFilters,
                selectedFuelFilters = selectedFuelFilters,
                selectedBodyFilters = selectedBodyFilters,
                onApplyFilter = { brands, fuels, bodies ->
                    viewModel.updateBrandFilters(brands)
                    viewModel.updateFuelFilters(fuels)
                    viewModel.updateBodyFilters(bodies)
                    viewModel.applyFilters()
                }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(16.dp)
            ) {
                items(carsList.size) { index ->
                    CarsItem(navController, carsList[index])
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun CarsItem(navController: NavController, carsItem: CarItemResponse) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 400.dp)
            .padding(5.dp)
    ) {
        CardDetails(navController, carsItem)
    }
}

@Composable
fun CardDetails(navController: NavController, carsItem: CarItemResponse) {
    Column {
        Box(
            modifier = Modifier
                .weight(0.75f)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = "https://www.autohurenrhodos.nl/wp-content/uploads/2022/10/auto-huren-rhodos-min.png",
                contentDescription = "Car image",
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .padding(start = 15.dp, bottom = 20.dp),
        ) {
            Text(
                text = "${carsItem.brand} ${carsItem.model} ${carsItem.body.lowercase()}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = "Vanaf € ${carsItem.price}/dag"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 15.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navController.navigate(Screens.CarScreen.withArgs(carsItem.id)) },
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFFFA500) // Orange color
                )
            ) {
                Text("Boek nu!")
            }
        }
    }
}

class CarsViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CarsViewModel(CarsRepositoryImplementation(RetrofitInstance.autoMaatApi)) as T
    }
}