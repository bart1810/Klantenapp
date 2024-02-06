package android.compose.presentation.views.screens.account

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FaqScreen(navController: NavController) {

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(
            containerColor = Color.White
        ), modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            CardContent(
                title = "Hoeveel auto's kan ik als favoriet markeren?",
                description = "Het is mogelijk om maximaal 3 auto's als favoriet te markeren. Bij AutoMaat+ is het mogelijk om 8 auto's als favoriet te markeren.")
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ), modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            CardContent(
                title = "Kan ik een auto huren zonder rijbewijs?",
                description = "Ja, je kan een auto huren zonder rijbewijs, maar dat betekent dat je wel een bestuurder nodig bent die voor jou kan rijden.")
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ), modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            CardContent(
                title = "Wat is de prijs van AutoMaat+?",
                description = "AutoMaat+ is beschikbaar voor 8,95 per maand of 99,95 per jaar")
        }
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ), modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)) {
            CardContent(
                title = "Waarom is het niet mogelijk om een auto te huren in de app?",
                description = "Op het moment zit de app nog in de testfase, hierdoor kun je nog geen auto's huren, alleen nog maar bekijken")
        }
    }
}

@Composable
private fun CardContent(title: String, description: String) {
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {

            Text(
                text = title,
                fontSize = 18.sp,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            if (expanded) {
                Text(text = description)
            }
        }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription =
                if (expanded) {
                    "show less"
                } else {
                    "show more"
                }
            )
        }
    }
}