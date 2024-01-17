package android.compose.presentation.views.screens.account

import android.compose.R
import android.compose.common.Screens
import android.compose.ui.theme.Secondary
import android.compose.ui.theme.TextColor
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 5.dp, end = 5.dp),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = "Settings",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        Text(
            text = "Log in of registreer bij jouw AutoMaat!",
            style = TextStyle(
                fontSize = 15.sp,
                color = TextColor
            ),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp, end = 5.dp, top = 15.dp)) {
            Button(
                modifier = Modifier
                    .width(185.dp),
                contentPadding = PaddingValues(15.dp),
                shape = RoundedCornerShape(25),
                onClick = { navController.navigate(Screens.LoginScreen.route) },
                border = BorderStroke(2.dp, Color(0xFFFFA500)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Orange color
                    contentColor = Color(0xFFFFA500)
                )
            ) {
                Text(stringResource(id = R.string.login).uppercase())
            }
            Button(
                modifier = Modifier
                    .width(185.dp),
                contentPadding = PaddingValues(15.dp),
                shape = RoundedCornerShape(25),
                onClick = { navController.navigate(Screens.RegisterScreen.route) },
                border = BorderStroke(2.dp, Color(0xFFFFA500)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Orange color
                    contentColor = Color(0xFFFFA500)
                )
            ) {
                Text(stringResource(id = R.string.signUp).uppercase())
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                onClick = {
                    navController.navigate(Screens.DamageFormScreen.route)
                }),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Report damages")
                Icon(
                    painter = painterResource(R.drawable.chevron_right_icon),
                    contentDescription = "pointer"
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .clickable(
                onClick = {
                    navController.navigate(Screens.FaqScreen.route)
                }),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 5.dp, end = 5.dp)
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Frequently asked questions")
                Icon(
                    painter = painterResource(R.drawable.chevron_right_icon),
                    contentDescription = "pointer"
                )
            }
        }
    }

}