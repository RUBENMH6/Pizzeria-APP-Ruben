package com.example.pizzeria.ui.views.auth

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_3
import com.example.pizzeria.ui.views.components.EmailField
import com.example.pizzeria.ui.views.components.LoginSubmitForm
import com.example.pizzeria.ui.views.components.PasswordField
import com.example.pizzeria.ui.views.components.PasswordForgot

@Composable
fun LoginView(navController: NavController, userViewModel: UserViewModel, context: Context, dialogViewModel: DialogViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Login(navController, userViewModel, context, dialogViewModel)
    }
}

@Composable
fun Login(navController: NavController, userViewModel: UserViewModel, context: Context, dialogViewModel: DialogViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        HeaderImage()
        Spacer(modifier = Modifier.height(30.dp))
        LoginForm(userViewModel, navController, context, dialogViewModel)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoRegister(navController)

    }
}

@Composable
fun HeaderImage() {
    Image(
        painter = painterResource(R.drawable.sanluisicono),
        contentDescription = "Header",
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun LoginForm(userViewModel: UserViewModel, navController: NavController, context: Context, dialogViewModel: DialogViewModel) {
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}

    Column(
        modifier = Modifier
            .padding(16.dp)
            .border(BorderStroke(1.dp, Palette_1_11), shape = ShapeDefaults.Small)
            .background(Palette_1_3, shape = ShapeDefaults.Small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(email, {email = it}, 16)
        PasswordField(password,{password = it} , stringResource(id = R.string.label_password) ) 
        LoginSubmitForm(email, password, stringResource(id = R.string.log_in), userViewModel, navController, context, dialogViewModel )
        PasswordForgot()
    }
}

@Composable
fun GoRegister(navController: NavController) {
    Text(
        text = "¿No tienes una cuenta?",
        fontSize = 12.sp
    )
    Text(
        text = "Registrate",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(12.dp)
            .clickable { navController.navigate(Routes.CreateUser.route) })
}
