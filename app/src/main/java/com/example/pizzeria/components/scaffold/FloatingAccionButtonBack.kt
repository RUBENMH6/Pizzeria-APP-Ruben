package com.example.pizzeria.components.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.scaffold

@Composable
fun MyFABtoBack(
    navController: NavController,
    currentRoute: String,
    productViewModel: ProductViewModel,
    userViewModel: UserViewModel
) {

    FloatingActionButton(
        containerColor = scaffold,
        onClick = {
            when (currentRoute) {
                Routes.OrderProduct.route -> navController.navigate(Routes.PizzaMenu.route)
                Routes.OrderProcess.route -> {
                    if (productViewModel.getQuantityProductTotal() != 0) {
                        navController.navigate(Routes.OrderProduct.route)
                    } else {
                        navController.navigate(Routes.PizzaMenu.route)
                    }
                }

                Routes.MainMenu.route, Routes.Login.route -> {
                    if (userViewModel.auth.currentUser != null) {
                        userViewModel.auth.signOut()
                        productViewModel.safeDeleteOrderMap()
                    }
                    navController.navigate(Routes.Login.route)
                }

                Routes.PizzaMenu.route, Routes.PastaMenu.route, Routes.MealMenu.route, Routes.DrinkMenu.route -> navController.navigate(
                    Routes.MainMenu.route
                )
            }
        }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "ArrowBack",
            tint = Color.White
        )
    }
}