package android.compose.presentation.views.screens.auth

import android.compose.R
import android.compose.common.UiEvents
import android.compose.common.components.forms.PasswordComponent
import android.compose.common.components.forms.TextFieldComponent
import android.compose.common.components.text.TextComponent
import android.compose.ui.theme.Secondary
import android.compose.presentation.viewmodels.auth.LoginViewModel
import android.compose.presentation.viewmodels.auth.PasswordViewModel
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val keyState = passwordViewModel.keyState.value
    val newPasswordState = passwordViewModel.passwordState.value
    val scaffoldState = rememberScaffoldState()

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
                    state = keyState,
                    label = stringResource(id = R.string.key), painterResource = painterResource(
                        id = R.drawable.baseline_key_24
                    )
                ) { passwordViewModel.setKey(it) }

                PasswordComponent(state = newPasswordState, label = stringResource(id = R.string.password), painterResource = painterResource(
                    id = R.drawable.lock), onTextChanged = { passwordViewModel.setPassword(it) })

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        enabled = keyState.text.isNotEmpty() && newPasswordState.text.isNotEmpty(),
                        onClick = { passwordViewModel.loginUser() },
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFFFA500)
                        )
                    ) {
                        Text(stringResource(id = R.string.change_password))
                    }
                }
            }
        }
    }
}