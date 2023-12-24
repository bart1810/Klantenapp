package android.compose.views.screens.authenticate

import android.compose.R
import android.compose.components.forms.PasswordComponent
import android.compose.components.forms.TextFieldComponent
import android.compose.components.text.TextComponent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SignUpScreen() {
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

            TextFieldComponent(label = stringResource(id = R.string.username), painterResource = painterResource(
                id = R.drawable.account_circle
            ))
            TextFieldComponent(label = stringResource(id = R.string.email), painterResource = painterResource(
                id = R.drawable.email))

            PasswordComponent(label = stringResource(id = R.string.password), painterResource = painterResource(
                id = R.drawable.lock))

            PasswordComponent(label = stringResource(id = R.string.confirm_password), painterResource = painterResource(
                id = R.drawable.lock))

        }
    }
}


@Composable
@Preview
fun SignUpPreview() {
    SignUpScreen()
}