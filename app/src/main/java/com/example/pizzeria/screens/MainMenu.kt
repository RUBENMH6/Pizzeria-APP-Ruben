package com.example.pizzeria.screens

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.tostadito

@Composable
fun MainMenu(navController: NavController, dialogViewModel: DialogViewModel, configuration: Configuration, context: Context) {
    val listButtons = listOf(
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
                    Row {
                        Button(
                            onClick = { navController.navigate(it) },
                            shape = ShapeDefaults.Small,
                            colors = ButtonDefaults.buttonColors(Palette_1_11),
                            modifier = Modifier.width(250.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                        ) {
                            Text(
                                text = when (it) {
                                    Routes.PizzaMenu.route -> context.getString(R.string.button_mainmenu_pizza)
                                    Routes.PastaMenu.route -> context.getString(R.string.button_mainmenu_pasta)
                                    Routes.MealMenu.route -> context.getString(R.string.button_mainmenu_meal)
                                    Routes.DrinkMenu.route -> context.getString(R.string.button_mainmenu_drink)
                                    Routes.Local.route -> context.getString(R.string.button_mainmenu_location)
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
            LoginNeededToAccessProfileDialog(navController, dialogViewModel, context)
        }
    }
}