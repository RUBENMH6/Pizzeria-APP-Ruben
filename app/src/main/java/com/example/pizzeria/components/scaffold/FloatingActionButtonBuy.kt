package com.example.pizzeria.components.scaffold


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.models.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.Palette_1_11

@Composable
fun MyFABtoBuy(navController: NavController, currentRoute: String?, productViewModel: ProductViewModel, userViewModel: UserViewModel, context: Context, dialogViewModel: DialogViewModel) {
    when (currentRoute) {
        Routes.PizzaMenu.route, Routes.PastaMenu.route, Routes.MealMenu.route, Routes.DrinkMenu.route, Routes.MainMenu.route -> {
            FloatingActionButton(
                containerColor = Palette_1_11,
                onClick = {
                    if (productViewModel.selectedProductList.isEmpty() || productViewModel.getQuantityProductTotal() == 0) {
                        Toast.makeText(context, "You have not selected any product", Toast.LENGTH_LONG).show()
                    } else {
                        navController.navigate(Routes.OrderProduct.route)
                    }
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_shopping_cart_24),
                    contentDescription = "Shopping Product",
                    tint = Color.White
                )
            }
        }
        Routes.OrderProduct.route -> {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FloatingActionButton(
                    containerColor = Palette_1_11,
                    onClick = {
                        if (!productViewModel.selectedProductMap.isEmpty() || productViewModel.getQuantityProductTotal() > 0) {
                            navController.navigate(Routes.OrderProcess.route)
                        } else {
                            Toast.makeText(context,"You have not selected any product", Toast.LENGTH_LONG).show()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_attach_money_24),
                        contentDescription = "Order process",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                FloatingActionButton(
                    containerColor = Palette_1_11,
                    onClick = {
                        if (!productViewModel.selectedProductMap.isEmpty() || productViewModel.getQuantityProductTotal() > 0) {
                            dialogViewModel.dialogRemoveOrderPizza.value = true
                        } else {
                            Toast.makeText(context,"You have not selected any product", Toast.LENGTH_LONG).show()
                        }

                    }
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