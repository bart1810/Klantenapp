package android.compose.presentation.views.screens.auth

import android.compose.R
import android.compose.common.Screens
import android.compose.common.UiEvents
import android.compose.common.components.forms.PasswordComponent
import android.compose.common.components.forms.TextFieldComponent
import android.compose.common.components.text.TextComponent
import android.compose.presentation.viewmodels.auth.RegisterViewModel
import android.compose.ui.theme.Primary
import android.compose.ui.theme.Secondary
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(navController: NavController,
                 registerViewModel: RegisterViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val usernameState = registerViewModel.usernameState.value
    val emailState = registerViewModel.emailState.value
    val passwordState = registerViewModel.passwordState.value
    val confirmPasswordState = registerViewModel.confirmPasswordState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        registerViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvents.NavigateEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                    navController.navigate(Screens.LoginScreen.route)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        Surface(
            color = Color.White,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.height(110.dp))
                TextComponent(
                    text = stringResource(id = R.string.create_account),
                    fontSize = 30,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    state = usernameState,
                    label = stringResource(id = R.string.username),
                    painterResource = painterResource(
                        id = R.drawable.account_circle
                    )
                ) { registerViewModel.setUsername(it) }

                TextFieldComponent(
                    state = emailState,
                    label = stringResource(id = R.string.email), painterResource = painterResource(
                        id = R.drawable.email
                    )
                ) { registerViewModel.setEmail(it) }

                PasswordComponent(state = passwordState,
                    label = stringResource(id = R.string.password),
                    painterResource = painterResource(
                        id = R.drawable.lock
                    ),
                    onTextChanged = { registerViewModel.setPassword(it) })
                Spacer(modifier = Modifier.height(30.dp))

                PasswordComponent(state = confirmPasswordState,
                    label = stringResource(id = R.string.confirm_password),
                    painterResource = painterResource(
                        id = R.drawable.lock
                    ),
                    onTextChanged = { registerViewModel.setConfirmPassword(it) })
                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        enabled = emailState.text.isNotEmpty() && passwordState.text.isNotEmpty() && usernameState.text.isNotEmpty() && confirmPasswordState.text.isNotEmpty(),
                        onClick = {
                            if (passwordState.text != confirmPasswordState.text) {
                                Toast.makeText(
                                    context,
                                    "Wachtwoorden komen niet overeen",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                registerViewModel.registerUser()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFFFA500) // Orange color
                        )
                    ) {
                        Text(stringResource(id = R.string.signUp))
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                TextButton(
                    onClick = { navController.navigate(Screens.LoginScreen.route) },
                ) {
                    Text(
                        text = stringResource(R.string.has_account),
                        modifier = Modifier
                            .padding(end = 5.dp),
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Secondary,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = stringResource(R.string.signInText),
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