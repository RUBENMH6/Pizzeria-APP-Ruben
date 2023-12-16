package com.example.pizzeria.components.scaffold

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_8
import com.example.pizzeria.ui.theme.button
import com.example.pizzeria.ui.theme.scaffold

@Composable
fun MyBottomAppBar(
    currentRoute: String?,
    navController: NavController,
    productViewModel: ProductViewModel,
    dialogViewModel: DialogViewModel,
    context: Context,
    userViewModel: UserViewModel
    ) {
    BottomAppBar(
        containerColor = Palette_1_11,
        modifier = Modifier.height(60.dp)
    ) {
        Row(
            modifier = Modifier.weight(0.2f)
        ) {
            IconButton(
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f)
                .padding(16.dp),

            ) {

        }

        Row(
            modifier = Modifier
                .weight(0.3f)
                .padding(end = 15.dp),
            horizontalArrangement = Arrangement.End
        ) {
            when (currentRoute) {
                Routes.PizzaMenu.route, Routes.PastaMenu.route, Routes.MealMenu.route, Routes.DrinkMenu.route, Routes.MainMenu.route -> {
                    IconButton(
                        onClick = {
                            if (productViewModel.selectedProductList.isEmpty() || productViewModel.getQuantityProductTotal() == 0) {
                                Toast.makeText(context, "You have not selected any product", Toast.LENGTH_LONG).show()
                            } else {
                                navController.navigate(Routes.OrderProduct.route)
                            }
                        },
                        modifier = Modifier
                            .size(20.dp)
                            .background(Palette_1_8, RoundedCornerShape(16.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_shopping_cart_24),
                            contentDescription = "Shopping Product",
                            tint = Color.White
                        )
                    }
                }
                Routes.OrderProduct.route -> {
                    IconButton(
                        onClick = {
                            if (!productViewModel.selectedProductMap.isEmpty() || productViewModel.getQuantityProductTotal() > 0) {
                                navController.navigate(Routes.OrderProcess.route)
                            } else {
                                Toast.makeText(context,"You have not selected any product", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier
                            .size(20.dp)
                            .background(Palette_1_8, RoundedCornerShape(16.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_attach_money_24),
                            contentDescription = "Order process",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(30.dp))
                    IconButton(
                        onClick = {
                            if (!productViewModel.selectedProductMap.isEmpty() || productViewModel.getQuantityProductTotal() > 0) {
                                dialogViewModel.dialogRemoveOrderPizza.value = true
                            } else {
                                Toast.makeText(context,"You have not selected any product", Toast.LENGTH_LONG).show()
                            }

                        },
                        modifier = Modifier
                            .size(20.dp)
                            .background(Palette_1_8, RoundedCornerShape(16.dp))
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_sync_24),
                            contentDescription = "Clear Order",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}
