package com.example.pizzeria.ui.views.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_9
import com.example.pizzeria.ui.views.auth.UserViewModel

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun UsernameField(username: String, onUsernameChange: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = username,
        onValueChange = { onUsernameChange(it.replace(" ", "")) }, //No se puede introducir espacios
        label = { Text(stringResource(R.string.label_name)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Palette_1_11,
            unfocusedBorderColor = Palette_1_9,
            cursorColor = Palette_1_11,
            containerColor = Color.White,
            focusedLabelColor = Palette_1_11,
            unfocusedLabelColor = Palette_1_11
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun EmailField(email: String, onEmailChange: (String) -> Unit, padding: Int) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = email,
        onValueChange = { onEmailChange( it.replace(" ", ""))}, //No se puede introducir espacios
        label = { Text(stringResource(R.string.label_email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(start = 16.dp, top = padding.dp, end = 16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Palette_1_11,
            unfocusedBorderColor = Palette_1_9,
            cursorColor = Palette_1_11,
            containerColor = Color.White,
            focusedLabelColor = Palette_1_11,
            unfocusedLabelColor = Palette_1_11
        )
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit, text: String) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it.replace(" ", "")) }, //No se puede introducir espacios
        label = { Text(text) },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(start = 16.dp, end = 16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Palette_1_11,
            unfocusedBorderColor = Palette_1_9,
            cursorColor = Palette_1_11,
            containerColor = Color.White,
            focusedLabelColor = Palette_1_11,
            unfocusedLabelColor = Palette_1_11
        )
    )
}

@Composable
fun LoginSubmitForm(email: String, password: String, text: String, userViewModel: UserViewModel, navController: NavController, context: Context, dialogViewModel: DialogViewModel) {
    Button(
        onClick = {
            userViewModel.signInWithEmailAndPassword(
                email,
                password,
                context,
                navController,
                dialogViewModel
            )
            println(
                "Email: $email \nPassword: $password"
            )
        },
        shape = ShapeDefaults.Small,
        modifier = Modifier
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(Palette_1_11)
    ) {
        Text(
            text = text,
            color = Palette_1_1
        )
    }
}

@Composable
fun RegisterSubmitForm(username: String, email: String, password: String, confirmPassword: String, text: String, context: Context) {
    Button(
        onClick = {
            if (password != confirmPassword) {
                Toast.makeText(context, context.getString(R.string.password_dont_match), Toast.LENGTH_SHORT).show()
            } else {
                println(
                    "Username: $username \nEmail: $email \nPassword: $password"
                )
            }

        },
        shape = ShapeDefaults.Small,
        modifier = Modifier
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(Palette_1_11)
    ) {
        Text(
            text = text,
            color = Palette_1_1
        )
    }
}
@Composable
fun PasswordForgot() {
    Text(
        text = "He olvidado la contraseña",
        fontSize = 12.sp,
        modifier = Modifier.padding(6.dp)
    )
}