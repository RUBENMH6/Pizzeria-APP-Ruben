package com.example.pizzeria.screens.products

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.components.products.pizza.MyPizzaCard
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog


@Composable
fun PizzaMenu(
    navController: NavController,
    productViewModel: ProductViewModel,
    dialogViewModel: DialogViewModel,
    configuration: Configuration
) {
    val listState = rememberLazyStaggeredGridState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {

            LazyColumn(modifier = Modifier.padding()) {
                items(productViewModel.productList) { product ->
                    if (product.type == "PIZZA") {
                        when (product.name) {
                            "Margarita" -> MyPizzaCard(product, productViewModel, R.drawable.pizza1, configuration)
                            "Proscuito" -> MyPizzaCard(product, productViewModel, R.drawable.pizza2, configuration)
                            "Regina" -> MyPizzaCard(product, productViewModel, R.drawable.pizza3, configuration)
                            "Provinciale" -> MyPizzaCard(product, productViewModel, R.drawable.pizza4, configuration)
                            "Carbonara" -> MyPizzaCard(product, productViewModel, R.drawable.pizza5, configuration)
                            "Calzone" -> MyPizzaCard(product, productViewModel, R.drawable.pizza7, configuration)
                        }
                    }
                }
            }
        } else {
            val list = productViewModel.productList
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(3),
                modifier = Modifier.padding(10.dp),
                state = listState
            ) {
                items(list.size) {
                    if (list[it].type == "PIZZA") {
                        when (list[it].name) {
                            "Margarita" -> MyPizzaCard(list[it], productViewModel, R.drawable.pizza1, configuration)
                            "Proscuito" -> MyPizzaCard(list[it], productViewModel, R.drawable.pizza2, configuration)
                            "Regina" -> MyPizzaCard(list[it], productViewModel, R.drawable.pizza3, configuration)
                            "Provinciale" -> MyPizzaCard(list[it], productViewModel, R.drawable.pizza4, configuration)
                            "Carbonara" -> MyPizzaCard(list[it], productViewModel, R.drawable.pizza5, configuration)
                            "Calzone" -> MyPizzaCard(list[it], productViewModel, R.drawable.pizza7, configuration)
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
            LoginNeededToAccessProfileDialog(navController, dialogViewModel)
        }
    }
}


