package android.compose.presentation.views.screens

import android.compose.R
import android.compose.ui.theme.Secondary
import android.compose.ui.theme.TextColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FavoritesScreen(navController: NavController) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 140.dp)
            .fillMaxSize()
            .padding(5.dp),
    ) {
        Image(
            painterResource(id = R.drawable.star),
            contentDescription = "Star",
            modifier = Modifier
                .width(130.dp)
        )
        Text(
            text = "Je hebt nog geen auto's als favoriet gemarkeerd",
            style = TextStyle(
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = TextColor,
                fontWeight = FontWeight.Medium
            ),
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp),
            text = "Als je een auto als favoriet markeert, wordt deze hier getoond",
            style = TextStyle(
                fontSize = 16.sp,
                color = Secondary,
                textAlign = TextAlign.Center
            ),
        )
    }
}