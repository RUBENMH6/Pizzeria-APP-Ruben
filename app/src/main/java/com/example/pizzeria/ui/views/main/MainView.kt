package com.example.pizzeria.ui.views.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.Routes
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_11

@Composable
fun MainView(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Menu(navController)
    }
}

@Composable
fun Menu(navController: NavController) {
    Column {
        HeaderImage()
        ListButtons(navController)
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
fun ListButtons(navController: NavController) {
    var listOfButtons = listOfButtons()

    listOfButtons.forEach { name ->
        ItemButton(name, navController)
    }

}

@Composable
fun ItemButton(route: String, navController: NavController) {
    Button(
        onClick = { navController.navigate(route) },
        shape = ShapeDefaults.Small,
        colors = ButtonDefaults.buttonColors(Palette_1_11),
        modifier = Modifier.width(250.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
    ) {
        Text(
            text = when (route) {
                Routes.PizzaMenu.route -> stringResource(R.string.button_mainmenu_pizza)
                Routes.PastaMenu.route -> stringResource(R.string.button_mainmenu_pasta)
                Routes.MealMenu.route -> stringResource(R.string.button_mainmenu_meal)
                Routes.DrinkMenu.route -> stringResource(R.string.button_mainmenu_drink)
                Routes.Local.route -> stringResource(R.string.button_mainmenu_location)
                else -> ""
            },
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = FontCWGSans
        )
    }
}
