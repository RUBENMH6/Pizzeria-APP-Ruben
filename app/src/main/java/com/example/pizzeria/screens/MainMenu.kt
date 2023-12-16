package com.example.pizzeria.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.data.UserInfo
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.dialogs.LoginNeededOrderPizzaDialog
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_3
import com.example.pizzeria.ui.theme.Palette_1_6
import com.example.pizzeria.ui.theme.Palette_1_7
import com.example.pizzeria.ui.theme.Palette_1_8
import com.example.pizzeria.ui.theme.button
import com.example.pizzeria.ui.theme.tostadito
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainMenu(navController: NavController, userViewModel: UserViewModel, dialogViewModel: DialogViewModel, productViewModel: ProductViewModel, context: Context, configuration: Configuration) {
    val listButtons = listOf<String>(
        Routes.PizzaMenu.route,
        Routes.PastaMenu.route,
        Routes.MealMenu.route,
        Routes.DrinkMenu.route,
        Routes.Local.route)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(tostadito)

        ) {

            if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.4f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.pizzalogo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) 0.6f else 1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                listButtons.forEach {
                    Row() {
                        Button(
                            onClick = { navController.navigate(it) },
                            shape = ShapeDefaults.Small,
                            colors = ButtonDefaults.buttonColors(Palette_1_11),
                            modifier = Modifier.width(250.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                        ) {
                            Text(
                                text = when (it) {
                                    Routes.PizzaMenu.route -> "Pizza"
                                    Routes.PastaMenu.route -> "Pasta"
                                    Routes.MealMenu.route -> "Meal"
                                    Routes.DrinkMenu.route -> "Drink"
                                    Routes.Local.route -> "Location"
                                    else -> ""
                                },
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = FontCWGSans
                            )
                        }
                    }
                }
            }
        }
    }
    if (dialogViewModel.dialogLoginToAccessProfile.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            LoginNeededToAccessProfileDialog(navController, productViewModel, dialogViewModel, context)
        }
    }
}