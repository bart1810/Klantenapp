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
    onApplyFilter: (List<String>) -> Unit
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
                onHideButtonClick = {
                    scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                        if (!bottomSheetState.isVisible) openBottomSheet = false
                    }
                },
                onApplyFilter = onApplyFilter
            )
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun BottomSheetContent(
    onHideButtonClick: () -> Unit,
    onApplyFilter: (List<String>) -> Unit
) {
    var selectedFilters by remember { mutableStateOf(mutableListOf<String>()) }


    fun handleSelection(filter: String) {
        selectedFilters = if (selectedFilters.contains(filter)) {
            selectedFilters.filter { it != filter }.toMutableList()
        } else {
            (selectedFilters + filter).toMutableList()
        }
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
            FilterAssistChip("Audi", R.drawable.audi_logo_icon, selectedFilters.contains("Audi")) {
                handleSelection("Audi")
            }
            FilterAssistChip("BMW", R.drawable.bmw_logo_icon, selectedFilters.contains("BMW")) {
                handleSelection("BMW")
            }
            FilterAssistChip("Ford", R.drawable.ford_logo_icon, selectedFilters.contains("Ford")) {
                handleSelection("Ford")
            }
        }
        Row(
            modifier = Modifier
                .width(320.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterAssistChip("Honda", R.drawable.honda_logo_icon, selectedFilters.contains("Honda")) {
                handleSelection("Honda")
            }
            FilterAssistChip("Hyundai", R.drawable.hyundai_logo_icon, selectedFilters.contains("Hyundai")) {
                handleSelection("Hyundai")
            }
            FilterAssistChip("Jeep", R.drawable.jeep_logo_icon, selectedFilters.contains("Jeep")) {
                handleSelection("Jeep")
            }
        }
        Row(
            modifier = Modifier
                .width(320.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterAssistChip("Nissan", R.drawable.nissan_logo_icon, selectedFilters.contains("Nissan")) {
                handleSelection("Nissan")
            }
            FilterAssistChip("Subaru", R.drawable.subaru_logo_icon, selectedFilters.contains("Subaru")) {
                handleSelection("Subaru")
            }
            FilterAssistChip("Toyota", R.drawable.toyota_logo_icon, selectedFilters.contains("Toyota")) {
                handleSelection("Toyota")
            }
        }
        Row(
            modifier = Modifier
                .width(320.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterAssistChip("Mercedes-Benz", R.drawable.mbenz_logo_icon, selectedFilters.contains("Mercedes-Benz")) {
                handleSelection("Mercedes-Benz")
            }
            FilterAssistChip("Chevrolet", R.drawable.chevrolet_logo_icon, selectedFilters.contains("Chevrolet")) {
                handleSelection("Chevrolet")
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
            FilterAssistChip("Gasoline", R.drawable.gasoline_icon, selectedFilters.contains("Gasoline")) {
                handleSelection("Gasoline")
            }
            FilterAssistChip("Diesel", R.drawable.diesel_icon, selectedFilters.contains("Diesel")) {
                handleSelection("Diesel")
            }
        }
        Row(
            modifier = Modifier
                .width(270.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            FilterAssistChip("Electric", R.drawable.electric_icon, selectedFilters.contains("Electric")) {
                handleSelection("Electric")
            }
            FilterAssistChip("Hybrid", R.drawable.hybrid_icon, selectedFilters.contains("Hybrid")) {
                handleSelection("Hybrid")
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
            FilterAssistChip("SUV", R.drawable.suv_icon, selectedFilters.contains("SUV")) {
                handleSelection("SUV")
            }
            FilterAssistChip("Sedan", R.drawable.sedan_icon, selectedFilters.contains("Sedan")) {
                handleSelection("Sedan")
            }
            FilterAssistChip("PickupTruck", R.drawable.pickuptruck_icon, selectedFilters.contains("PickupTruck")) {
                handleSelection("PickupTruck")
            }
        }
        Row(
            modifier = Modifier
                .width(330.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            FilterAssistChip("Hatchback", R.drawable.hatchback_icon, selectedFilters.contains("Hatchback")) {
                handleSelection("Hatchback")
            }
            FilterAssistChip("Station wagon", R.drawable.stationwagon_icon, selectedFilters.contains("Station_wagon")) {
                handleSelection("Station_wagon")
            }
        }

        Spacer(modifier = Modifier.size(15.dp))

        Button(
            modifier = Modifier.width(250.dp),
            onClick = {
                onApplyFilter(selectedFilters)
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