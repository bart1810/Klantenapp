package android.compose.presentation.views.screens.auth

import android.compose.R
import android.compose.common.UiEvents
import android.compose.common.components.forms.PasswordComponent
import android.compose.common.components.forms.TextFieldComponent
import android.compose.common.components.text.TextComponent
import android.compose.ui.theme.Secondary
import android.compose.presentation.viewmodels.auth.PasswordViewModel
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ForgotPasswordScreen(navController: NavController,
                passwordViewModel: PasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val emailState = passwordViewModel.emailState.value
    val keyState = passwordViewModel.keyState.value
    val newPasswordState = passwordViewModel.newPasswordState.value
    val confirmPasswordState = passwordViewModel.confirmPasswordState.value
    val scaffoldState = rememberScaffoldState()
    var emailSent by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        passwordViewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                is UiEvents.NavigateEvent -> {
                    navController.navigateUp()
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )

                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                TextComponent(
                    text = stringResource(id = R.string.forgot_password),
                    fontSize = 30,
                    fontWeight = FontWeight.Bold
                )
                if (emailSent) {
                    TextComponent(
                        text = stringResource(id = R.string.forgot_password_subtext_2),
                        fontSize = 15,
                        fontWeight = FontWeight.Normal,
                        textColor = Secondary
                    )
                } else {
                    TextComponent(
                        text = stringResource(id = R.string.forgot_password_subtext_1),
                        fontSize = 15,
                        fontWeight = FontWeight.Normal,
                        textColor = Secondary
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (emailSent) {
                    TextFieldComponent(
                        state = keyState,
                        label = stringResource(id = R.string.key), painterResource = painterResource(
                            id = R.drawable.baseline_key_24
                        )
                    ) { passwordViewModel.setKey(it) }

                    PasswordComponent(state = newPasswordState,
                        label = stringResource(id = R.string.password),
                        painterResource = painterResource(
                            id = R.drawable.lock
                        ),
                        onTextChanged = { passwordViewModel.setPassword(it) })
                    Spacer(modifier = Modifier.height(30.dp))

                    PasswordComponent(state = confirmPasswordState,
                        label = stringResource(id = R.string.confirm_password),
                        painterResource = painterResource(
                            id = R.drawable.lock
                        ),
                        onTextChanged = { passwordViewModel.setConfirmPassword(it) })
                    Spacer(modifier = Modifier.height(30.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            enabled = keyState.text.isNotEmpty() && newPasswordState.text.isNotEmpty() &&
                                    confirmPasswordState.text.isNotEmpty(),
                            onClick = {
                                if (newPasswordState.text != confirmPasswordState.text) {
                                    Toast.makeText(
                                        context,
                                        "Wachtwoorden komen niet overeen",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    passwordViewModel.changePasswordFinish()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFFFA500)
                            )
                        ) {
                            Text(stringResource(id = R.string.change_password))
                        }
                    }
                } else {
                    TextFieldComponent(
                        state = emailState,
                        label = stringResource(id = R.string.email), painterResource = painterResource(
                            id = R.drawable.email
                        )
                    ) { passwordViewModel.setEmail(it) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            enabled = emailState.text.isNotEmpty(),
                            onClick = {
                                passwordViewModel.changeForgottenPasswordInit()
                                emailSent = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                Color(0xFFFFA500)
                            )
                        ) {
                            Text(stringResource(id = R.string.send_email))
                        }
                    }
                }
            }
        }
    }
}