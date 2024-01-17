package android.compose.ui.components

import android.annotation.SuppressLint
import android.compose.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    selectedBrandFilters: List<String>,
    selectedFuelFilters: List<String>,
    selectedBodyFilters: List<String>,
    onApplyFilter: (List<String>, List<String>, List<String>) -> Unit
) {
    var openBottomSheet by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Button(onClick = { openBottomSheet = true }) {
        Text(text = "Sort and Filter")
    }

    if (openBottomSheet) {
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = { openBottomSheet = false },
            dragHandle = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(text = "Sort and Filters", style = MaterialTheme.typography.titleLarge)
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider()
                }
            }
        ) {
            BottomSheetContent(
                selectedBrandFilters = selectedBrandFilters,
                selectedFuelFilters = selectedFuelFilters,
                selectedBodyFilters = selectedBodyFilters,
                onHideButtonClick = {
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) openBottomSheet = false
                    }
                },
                onApplyFilter = { selectedBrands, selectedFuels, selectedBodies ->
                    onApplyFilter(selectedBrands, selectedFuels, selectedBodies)
                }
            )
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun BottomSheetContent(
    selectedBrandFilters: List<String>,
    selectedFuelFilters: List<String>,
    selectedBodyFilters: List<String>,
    onHideButtonClick: () -> Unit,
    onApplyFilter: (List<String>, List<String>, List<String>) -> Unit
) {
    var selectedBrands by remember { mutableStateOf(selectedBrandFilters) }
    var selectedFuels by remember { mutableStateOf(selectedFuelFilters) }
    var selectedBodies by remember { mutableStateOf(selectedBodyFilters) }

    fun <T> updateFilterList(currentList: List<T>, filter: T): List<T> {
        return if (currentList.contains(filter)) {
            currentList.filter { it != filter }
        } else {
            currentList + filter
        }
    }

    fun handleBrandSelection(brand: String) {
        selectedBrands = updateFilterList(selectedBrands, brand)
    }

    fun handleFuelTypeSelection(fuelType: String) {
        selectedFuels = updateFilterList(selectedFuels, fuelType)
    }

    fun handleBodyTypeSelection(bodyType: String) {
        selectedBodies = updateFilterList(selectedBodies, bodyType)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(15.dp))

        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Car Brand")
        }
        Row(
            modifier = Modifier
                .width(320.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterAssistChip("Audi", R.drawable.audi_logo_icon, selectedBrands.contains("Audi")) {
                handleBrandSelection("Audi")
            }
            FilterAssistChip("BMW", R.drawable.bmw_logo_icon, selectedBrands.contains("BMW")) {
                handleBrandSelection("BMW")
            }
            FilterAssistChip("Ford", R.drawable.ford_logo_icon, selectedBrands.contains("Ford")) {
                handleBrandSelection("Ford")
            }
        }
        Row(
            modifier = Modifier
                .width(320.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterAssistChip("Honda", R.drawable.honda_logo_icon, selectedBrands.contains("Honda")) {
                handleBrandSelection("Honda")
            }
            FilterAssistChip("Hyundai", R.drawable.hyundai_logo_icon, selectedBrands.contains("Hyundai")) {
                handleBrandSelection("Hyundai")
            }
            FilterAssistChip("Jeep", R.drawable.jeep_logo_icon, selectedBrands.contains("Jeep")) {
                handleBrandSelection("Jeep")
            }
        }
        Row(
            modifier = Modifier
                .width(320.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterAssistChip("Nissan", R.drawable.nissan_logo_icon, selectedBrands.contains("Nissan")) {
                handleBrandSelection("Nissan")
            }
            FilterAssistChip("Subaru", R.drawable.subaru_logo_icon, selectedBrands.contains("Subaru")) {
                handleBrandSelection("Subaru")
            }
            FilterAssistChip("Toyota", R.drawable.toyota_logo_icon, selectedBrands.contains("Toyota")) {
                handleBrandSelection("Toyota")
            }
        }
        Row(
            modifier = Modifier
                .width(320.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterAssistChip("Mercedes-Benz", R.drawable.mbenz_logo_icon, selectedBrands.contains("Mercedes-Benz")) {
                handleBrandSelection("Mercedes-Benz")
            }
            FilterAssistChip("Chevrolet", R.drawable.chevrolet_logo_icon, selectedBrands.contains("Chevrolet")) {
                handleBrandSelection("Chevrolet")
            }
        }

        Spacer(modifier = Modifier.size(15.dp))

        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Fuel type")
        }
        Row(
            modifier = Modifier
                .width(270.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            FilterAssistChip("Gasoline", R.drawable.gasoline_icon, selectedFuels.contains("GASOLINE")) {
                handleFuelTypeSelection("GASOLINE")
            }
            FilterAssistChip("Diesel", R.drawable.diesel_icon, selectedFuels.contains("DIESEL")) {
                handleFuelTypeSelection("DIESEL")
            }
        }
        Row(
            modifier = Modifier
                .width(270.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            FilterAssistChip("Electric", R.drawable.electric_icon, selectedFuels.contains("ELECTRIC")) {
                handleFuelTypeSelection("ELECTRIC")
            }
            FilterAssistChip("Hybrid", R.drawable.hybrid_icon, selectedFuels.contains("HYBRID")) {
                handleFuelTypeSelection("HYBRID")
            }
        }

        Spacer(modifier = Modifier.size(15.dp))

        Box (
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Body")
        }
        Row(
            modifier = Modifier
                .width(330.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            FilterAssistChip("SUV", R.drawable.suv_icon, selectedBodies.contains("SUV")) {
                handleBodyTypeSelection("SUV")
            }
            FilterAssistChip("Sedan", R.drawable.sedan_icon, selectedBodies.contains("SEDAN")) {
                handleBodyTypeSelection("SEDAN")
            }
            FilterAssistChip("PickupTruck", R.drawable.pickuptruck_icon, selectedBodies.contains("TRUCK")) {
                handleBodyTypeSelection("TRUCK")
            }
        }
        Row(
            modifier = Modifier
                .width(330.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            FilterAssistChip("Hatchback", R.drawable.hatchback_icon, selectedBodies.contains("HATCHBACK")) {
                handleBodyTypeSelection("HATCHBACK")
            }
            FilterAssistChip("Station wagon", R.drawable.stationwagon_icon, selectedBodies.contains("STATIONWAGON")) {
                handleBodyTypeSelection("STATIONWAGON")
            }
        }

        Spacer(modifier = Modifier.size(15.dp))

        Button(
            modifier = Modifier.width(250.dp),
            onClick = {
                onApplyFilter(selectedBrands, selectedFuels, selectedBodies)
                onHideButtonClick() }
        ) {
            Text(text = "Apply")
        }

        Spacer(modifier = Modifier.size(20.dp))
    }
}

@Composable
fun FilterAssistChip(
    filterName: String,
    iconResource: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    AssistChip(
        onClick = onClick,
        label = { Text(filterName, color = contentColor) },
        leadingIcon = {
            Icon(painterResource(id = iconResource), contentDescription = "$filterName icon", tint = contentColor)
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = backgroundColor
        )
    )
}