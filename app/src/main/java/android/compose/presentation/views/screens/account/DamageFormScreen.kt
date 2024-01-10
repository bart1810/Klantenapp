package android.compose.presentation.views.screens.account

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DamageFormScreen(navController: NavController) {
    var KmStand by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(start = 10.dp, end = 10.dp)) {
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
            Alignment.TopStart
        ) {
            Text(
                text = "Report Damage",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black)
        }
        Row {
            Text(
                text = "Kilometer stand",
                style = MaterialTheme.typography.bodyLarge
            )
            TextField(
                modifier = Modifier.width(30.dp),
                value = KmStand,
                onValueChange = { KmStand = it },
                placeholder = { Text(text = "10000 km") },
            )

            Text(
                text = "Kilometer stand",
                style = MaterialTheme.typography.bodyLarge
            )
            TextField(
                modifier = Modifier.width(30.dp),
                value = KmStand,
                onValueChange = { KmStand = it },
                placeholder = { Text(text = "10000 km") },
            )
        }


    }

}