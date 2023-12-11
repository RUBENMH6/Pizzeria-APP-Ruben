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
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.data.getProductInfo
import com.example.pizzeria.components.products.drink.MyDrinkCard
import com.example.pizzeria.ui.theme.Palette_1_4

@Composable
fun DrinkMenu(navController: NavController, viewModel: ProductViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Palette_1_4)
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(getProductInfo()) {
                if (it.type == "DRINK") {
                    Spacer(modifier = Modifier.height(10.dp))
                    MyDrinkCard(it, viewModel)
                    Spacer(modifier = Modifier.weight(0.10f))
                }

            }
        }
    }
}

