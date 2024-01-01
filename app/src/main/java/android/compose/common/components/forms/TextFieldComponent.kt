package android.compose.common.components.forms

import android.compose.ui.theme.LightGray
import android.compose.ui.theme.White
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldComponent(value: String, label: String, painterResource: Painter, onTextChanged: (String) -> Unit) {

    TextField(
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        label = { Text(text = label)},
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = White,
            focusedContainerColor = White,
            unfocusedIndicatorColor = LightGray,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = "",
        onValueChange = {
            onTextChanged(it)
        },
        leadingIcon = { Icon(painter = painterResource, contentDescription = "") }
    )

    Spacer(modifier = Modifier.height(30.dp))
}