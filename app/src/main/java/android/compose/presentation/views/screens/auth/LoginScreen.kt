package android.compose.presentation.views.screens.auth

import android.compose.R
import android.compose.common.Screens
import android.compose.common.UiEvents
import android.compose.common.components.forms.PasswordComponent
import android.compose.common.components.forms.TextFieldComponent
import android.compose.common.components.text.TextComponent
import android.compose.ui.theme.Primary
import android.compose.ui.theme.Secondary
import android.compose.ui.theme.TextColor
import android.compose.presentation.viewmodels.auth.LoginViewModel
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(navController: NavController,
                loginViewModel: LoginViewModel = hiltViewModel()
) {
    val usernameState = loginViewModel.usernameState.value
    val passwordState = loginViewModel.passwordState.value
    val rememberMeState = loginViewModel.rememberMeState.value
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        loginViewModel.eventFlow.collectLatest { event ->
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
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(28.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                TextComponent(text = stringResource(id = R.string.login), fontSize = 30, fontWeight = FontWeight.Bold)
                TextComponent(text = stringResource(id = R.string.loginSubText), fontSize = 15, fontWeight = FontWeight.Normal, textColor = Secondary)

                Spacer(modifier = Modifier.height(20.dp))

                TextFieldComponent(
                    state = usernameState,
                    label = stringResource(id = R.string.username), painterResource = painterResource(
                        id = R.drawable.account_circle
                    )
                ) { loginViewModel.setUsername(it) }

                PasswordComponent(state = passwordState, label = stringResource(id = R.string.password), painterResource = painterResource(
                    id = R.drawable.lock), onTextChanged = { loginViewModel.setPassword(it) })

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = rememberMeState.checked,
                            onCheckedChange = {
                            loginViewModel.setRememberMe(!rememberMeState.checked)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Primary,
                            )
                            )
                        Text(
                            text = stringResource(R.string.remember_me),
                            style = TextStyle(
                                fontSize = 15.sp,
                                color = TextColor
                            ),
                            color = TextColor,
                            textAlign = TextAlign.Start
                        )
                    }

                    TextButton(
                        onClick = { navController.navigate(Screens.ForgotPasswordScreen.route) },
                    ) {
                        Text(
                            text = stringResource(R.string.forgot_password_question_mark),
                            style = TextStyle(
                                fontSize = 15.sp,
                                color = Primary,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        enabled = usernameState.text.isNotEmpty() && passwordState.text.isNotEmpty(),
                        onClick = { loginViewModel.loginUser() },
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFFFA500)
                        )
                    ) {
                        Text(stringResource(id = R.string.login))
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
                    onClick = { navController.navigate(Screens.RegisterScreen.route) },
                ) {
                    Text(
                        text = stringResource(R.string.has_no_account),
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
                        text = stringResource(R.string.signUpText),
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