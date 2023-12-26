package android.compose.views.screens.auth

import android.compose.R
import android.compose.RetrofitInstance
import android.compose.components.forms.PasswordComponent
import android.compose.components.forms.TextFieldComponent
import android.compose.components.text.TextComponent
import android.compose.data.auth.IUserRepository
import android.compose.ui.theme.Primary
import android.compose.ui.theme.Secondary
import android.compose.ui.theme.TextColor
import android.compose.viewmodels.auth.LoginViewModel
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen() {
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModalFactory())

    val loginResult = viewModel.loginResult.value
    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.showErrorToastChannel) {
        viewModel.showErrorToastChannel.collectLatest { show ->
            if (show) {
                Toast.makeText(
                    context, "Error", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val username = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val rememberMe = remember {
        mutableStateOf(false)
    }
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
            Spacer(modifier = Modifier.height(150.dp))
            TextComponent(value = stringResource(id = R.string.login), fontSize = 30, fontWeight = FontWeight.Bold)
            TextComponent(value = stringResource(id = R.string.loginSubText), fontSize = 15, fontWeight = FontWeight.Normal, textColor = Secondary)

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldComponent(
                label = stringResource(id = R.string.username), painterResource = painterResource(
                id = R.drawable.account_circle
            ), onTextChanged = { username.value = it })

            PasswordComponent(label = stringResource(id = R.string.password), painterResource = painterResource(
                id = R.drawable.lock), onTextChanged = { password.value = it })

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(checked = rememberMe.value, onCheckedChange = {
                    rememberMe.value = !rememberMe.value
                })
                Text(
                    modifier = Modifier.padding(end = 25.dp),
                    text = stringResource(R.string.remember_me),
                    style = TextStyle(
                        fontSize = 15.sp,
                        color = TextColor
                    ),
                    color = TextColor,
                    textAlign = TextAlign.Start
                )
                TextButton(
                    onClick = { /*TODO*/ },
                ) {
                    Text(
                        text = stringResource(R.string.forgot_password),
                        style = TextStyle(
                            fontSize = 15.sp,
                            color = Primary,
                            fontWeight = FontWeight.Bold
                        ),
                        textAlign = TextAlign.End
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { loginUser(username.value, password.value, rememberMe.value) },
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFFFFA500) // Orange color
                    )
                ) {
                    Text(stringResource(id = R.string.signIn))
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
                onClick = { },
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
fun loginUser(username: String, password: String, rememberMe: Boolean) {
    LoginViewModel(IUserRepository(RetrofitInstance.autoMaatApi)).loginUser(username, password, rememberMe)
}

class LoginViewModalFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(IUserRepository(RetrofitInstance.autoMaatApi)) as T
    }
}

@Composable
@Preview
fun LoginPreview() {
    LoginScreen()
}