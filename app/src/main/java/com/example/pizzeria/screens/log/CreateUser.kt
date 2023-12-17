package com.example.pizzeria.screens.log

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.models.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_9
import com.example.pizzeria.ui.theme.tostadito

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateUser(
    context: Context,
    navController: NavController,
    userViewModel: UserViewModel,
    dialogViewModel: DialogViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().background(tostadito),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(R.drawable.pizzalogo),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp),
                contentScale = ContentScale.Crop
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .width(250.dp)
                    .height(300.dp)
                    .border(1.dp, Palette_1_11, RoundedCornerShape(16.dp))
                    .background(Palette_1_11, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = name,
                        onValueChange = { name = it.replace(" ", "") },
                        label = { Text("Name") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Next),
                        modifier = Modifier.width(200.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Palette_1_11,
                            unfocusedBorderColor = Palette_1_9,
                            cursorColor = Palette_1_11,
                            containerColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Palette_1_11,
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = email,
                        onValueChange = { email = it.replace(" ", "") },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Next),
                        modifier = Modifier.width(200.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Palette_1_11,
                            unfocusedBorderColor = Palette_1_9,
                            cursorColor = Palette_1_11,
                            containerColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Palette_1_11
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it.replace(" ", "") },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
                        modifier = Modifier.width(200.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Palette_1_11,
                            unfocusedBorderColor = Palette_1_9,
                            cursorColor = Palette_1_11,
                            containerColor = Color.White,
                            focusedLabelColor = Color.White,
                            unfocusedLabelColor = Palette_1_11
                        )
                    )

                        Button(
                            onClick = {
                                    userViewModel.createUserWithEmailAndPassword(email, password, context, navController, name)
                            },
                            modifier = Modifier.padding(top = 16.dp).border(1.dp, Color.White, ShapeDefaults.Small).background(
                                tostadito, ShapeDefaults.Small),
                            colors =  ButtonDefaults.buttonColors(tostadito)
                        ) {
                            Text(
                                text = "Create account",
                                color = Palette_1_11)
                        }



                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Do you already have an account?",
                fontSize = 10.sp,
                modifier = Modifier.height(20.dp))
            TextButton(
                onClick = { navController.navigate(Routes.Login.route) },
                modifier = Modifier.height(20.dp)
            ) {
                Text(
                    text = "Sign in",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
    if (dialogViewModel.dialogLoginToAccessProfile.value) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            LoginNeededToAccessProfileDialog(navController, dialogViewModel)
        }
    }
}


