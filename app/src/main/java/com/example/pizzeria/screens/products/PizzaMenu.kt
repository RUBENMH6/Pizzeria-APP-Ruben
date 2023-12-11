package com.example.pizzeria.screens.products

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.pizzeria.R
import com.example.pizzeria.classes.data.ProductInfo
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.components.products.pizza.MyPizzaCard
import com.example.pizzeria.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PizzaMenu(navController: NavController, productViewModel: ProductViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Palette_1_4)
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(productViewModel.productList) { product ->
                Spacer(modifier = Modifier.height(10.dp))
                if (product.type == "PIZZA") {
                    when(product.name) {
                        "Margarita" -> MyPizzaCard(product, productViewModel, R.drawable.pizza1)
                        "Proscuito" -> MyPizzaCard(product, productViewModel, R.drawable.pizza2)
                        "Regina" -> MyPizzaCard(product, productViewModel, R.drawable.pizza3)
                        "Provinciale" -> MyPizzaCard(product, productViewModel, R.drawable.pizza4)
                        "Carbonara" -> MyPizzaCard(product, productViewModel, R.drawable.pizza5)
                        "Calzone" -> MyPizzaCard(product, productViewModel, R.drawable.pizza7)
                    }

                }
                Spacer(modifier = Modifier.weight(0.10f))
            }
        }
    }
}


