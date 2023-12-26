package android.compose.views.screens.auth

import android.compose.R
import android.compose.components.forms.PasswordComponent
import android.compose.components.forms.TextFieldComponent
import android.compose.components.text.TextComponent
import android.compose.ui.theme.Primary
import android.compose.ui.theme.Secondary
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen() {
    val username = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val confirmPassword = remember {
        mutableStateOf("")
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
            Spacer(modifier = Modifier.height(110.dp))
            TextComponent(value = stringResource(id = R.string.create_account), fontSize = 30, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            TextFieldComponent(
                label = stringResource(id = R.string.username), painterResource = painterResource(
                id = R.drawable.account_circle
            ), onTextChanged = { username.value = it })

            TextFieldComponent(
                label = stringResource(id = R.string.email), painterResource = painterResource(
                id = R.drawable.email), onTextChanged = { email.value = it })

            PasswordComponent(label = stringResource(id = R.string.password), painterResource = painterResource(
                id = R.drawable.lock), onTextChanged = { password.value = it })
            Spacer(modifier = Modifier.height(30.dp))

            PasswordComponent(label = stringResource(id = R.string.confirm_password), painterResource = painterResource(
                id = R.drawable.lock), onTextChanged = { confirmPassword.value = it })
            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ },
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


@Composable
@Preview
fun SignUpPreview() {
    SignUpScreen()
}