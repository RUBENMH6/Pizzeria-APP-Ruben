package com.example.pizzeria.screens.products


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.components.products.drink.MyDrinkCard
import com.example.pizzeria.components.products.pizza.MyPizzaCard
import com.example.pizzeria.ui.theme.Palette_1_4

@Composable
fun DrinkMenu(navController: NavController, productViewModel: ProductViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Palette_1_4)
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(productViewModel.productList) { product ->
                if (product.type == "DRINK") {
                    when(product.name) {
                        "Margarita" -> MyPizzaCard(product, productViewModel, R.drawable.pizza1)
                        "Proscuito" -> MyPizzaCard(product, productViewModel, R.drawable.pizza2)
                        "Regina" -> MyPizzaCard(product, productViewModel, R.drawable.pizza3)
                        "Provinciale" -> MyPizzaCard(product, productViewModel, R.drawable.pizza4)
                        "Carbonara" -> MyPizzaCard(product, productViewModel, R.drawable.pizza5)
                        "Calzone" -> MyPizzaCard(product, productViewModel, R.drawable.pizza7)
                    }
                }

            }
        }
    }
}

