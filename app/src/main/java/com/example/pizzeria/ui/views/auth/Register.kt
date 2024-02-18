package com.example.pizzeria.ui.views.auth

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.Routes
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_3

@Composable
fun RegisterView(navController: NavController, context: Context) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Register(navController, context)
    }
}

@Composable
fun Register(navController: NavController, context: Context) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        HeaderImage()
        Spacer(modifier = Modifier.height(30.dp))
        RegisterForm(context)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoLogin(navController)

    }
}

@Composable
fun RegisterForm(context: Context) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(BorderStroke(1.dp, Palette_1_11), shape = ShapeDefaults.Small)
            .background(Palette_1_3, shape = ShapeDefaults.Small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UsernameField(username) {username = it}
        EmailField(email, { email = it }, 0)
        PasswordField(password, {password = it} , stringResource(id = R.string.label_password))
        PasswordField(confirmPassword, {confirmPassword = it} , stringResource(id = R.string.label_confirm_password))
        RegisterSubmitForm(username, email, password, confirmPassword, stringResource(id = R.string.text_createuser), context)
    }
}

@Composable
fun GoLogin(navController: NavController) {
    Text(
        text = "¿Ya tienes una cuenta?",
        fontSize = 12.sp
    )
    Text(
        text = "Inicia sesión",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(12.dp)
            .clickable { navController.navigate(Routes.Login.route) })
}
