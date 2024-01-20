package android.compose.presentation.views.screens.account

import android.compose.R
import android.compose.common.Screens
import android.compose.data.local.AuthPreferences
import android.compose.data.repository.account.IAccountRepository
import android.compose.presentation.viewmodels.account.AccountViewModel
import android.compose.ui.theme.Gray
import android.compose.ui.theme.LightGray
import android.compose.ui.theme.Primary
import android.compose.ui.theme.TextColor
import android.compose.util.Resource
import android.compose.util.RetrofitInstance
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val authPreferences = AuthPreferences(context)
    val accountViewModel: AccountViewModel = viewModel(factory = AccountViewModelFactory(authPreferences))

    LaunchedEffect(true) {
       accountViewModel.fetAccount()
    }

    val accountState = accountViewModel.accountResponse.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxWidth(),
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
                text = "Profiel",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
        when (accountState) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is Resource.Success -> {
                accountState.data?.let { accountDetails ->
                    Box(
                        modifier = Modifier
                            .background(LightGray)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.default_profile),
                                contentDescription = "A call icon for calling"
                            )
                            Text(modifier = Modifier
                                .padding(15.dp),
                                color = TextColor,
                                fontWeight = FontWeight.Medium,
                                text = accountDetails.firstName + " " + accountDetails.lastName
                            )
//                            TODO: Scherm maken om je profiel te bewerken
                            TextButton(
                                onClick = { TODO() },
                            ) {
                                Text(
                                    text = stringResource(R.string.editProfile).uppercase(),
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        color = Primary,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                    }
                }
            }
            is Resource.Error -> {
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
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFFFFA500)
                        )
                    ) {
                        Text(stringResource(id = R.string.signUp).uppercase())
                    }
                }
            }
            null -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
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
                Text(text = "Schade melden")
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
                Text(text = "FAQ")
                Icon(
                    painter = painterResource(R.drawable.chevron_right_icon),
                    contentDescription = "pointer"
                )
            }
        }
        if (accountState?.data?.id != null) {
            val showLogoutMessage = remember { mutableStateOf(false) }
            if (showLogoutMessage.value) {
                AlertDialog(
                    onDismissRequest = { showLogoutMessage.value = false },
                    confirmButton = { Button(
                        onClick = {
                            showLogoutMessage.value = false
                        }) {
                        Text(text = stringResource(id = R.string.logout).uppercase())
                    }},
                    dismissButton = { Button(
                        onClick = { showLogoutMessage.value = false }) {
                        Text(text = stringResource(id = R.string.cancel).uppercase())
                    }},
                    title = { Text(text = stringResource(id = R.string.logout))},
                    text = { Text(text = "Weet je zeker dat je wilt uitloggen?")})
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clickable(
                    onClick = {
                        showLogoutMessage.value = true
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
                    Text(text = stringResource(id = R.string.logout))
                    Icon(
                        painter = painterResource(R.drawable.chevron_right_icon),
                        contentDescription = "pointer"
                    )
                }
            }
        }
    }
}

class AccountViewModelFactory(
    private val authPreferences: AuthPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(
                IAccountRepository(RetrofitInstance.autoMaatApi),
                authPreferences
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}