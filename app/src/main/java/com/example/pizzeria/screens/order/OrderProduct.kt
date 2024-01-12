package com.example.pizzeria.screens.order

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.components.products.order.MyCardPedido
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog
import com.example.pizzeria.dialogs.RemoveOrderPizzaDialog
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.Palette_1_10
import com.example.pizzeria.ui.theme.Palette_1_8
import java.text.DecimalFormat

@Composable
fun OrderPizza(navController: NavController, productViewModel: ProductViewModel, dialogViewModel: DialogViewModel, configuration: Configuration, context: Context) {
    var totalPrice by remember { mutableDoubleStateOf(0.0) }
    totalPrice = getInitialPrice(productViewModel)
    val listState = rememberLazyStaggeredGridState()
    val list = productViewModel.selectedProductList
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            LazyColumn(modifier = Modifier.padding()) {
                items(list) { product ->
                    when (product.name) {
                        "Margarita" -> MyCardPedido(
                            product,
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza1
                        )

                        "Proscuito" -> MyCardPedido(
                            product,
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza2
                        )

                        "Regina" -> MyCardPedido(
                            product,
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza3
                        )

                        "Provinciale" -> MyCardPedido(
                            product,
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza4
                        )

                        "Carbonara" -> MyCardPedido(
                            product,
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza5
                        )

                        "Calzone" -> MyCardPedido(
                            product,
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza7
                        )
                    }
                }
            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.padding(10.dp),
                state = listState
            ) {
                items(list.size) { index ->
                    when (list[index].name) {
                        "Margarita" -> MyCardPedido(
                            list[index],
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza1
                        )

                        "Proscuito" -> MyCardPedido(
                            list[index],
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza2
                        )

                        "Regina" -> MyCardPedido(
                            list[index],
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza3
                        )

                        "Provinciale" -> MyCardPedido(
                            list[index],
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza4
                        )

                        "Carbonara" -> MyCardPedido(
                            list[index],
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza5
                        )

                        "Calzone" -> MyCardPedido(
                            list[index],
                            { totalPrice = it },
                            productViewModel,
                            R.drawable.pizza7
                        )
                    }

                }
            }
        }
    }
    if (dialogViewModel.dialogRemoveOrderPizza.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            RemoveOrderPizzaDialog(productViewModel, dialogViewModel, context)
        }
    }

    if (productViewModel.selectedProductMap.isEmpty() || productViewModel.getQuantityProductTotal() == 0) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp)
                    .background(
                        Palette_1_8,
                        RoundedCornerShape(20.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "There is no pizza in the order",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize( )
                .padding(start = 10.dp, end = 10.dp,  bottom = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(Palette_1_10),
                modifier = Modifier
                    .fillMaxWidth(0.50f)
                    .height(32.dp),
                shape = ShapeDefaults.Small,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val decimalFormat = DecimalFormat("#.##")
                    decimalFormat.minimumFractionDigits = 2
                    Text(
                        text =
                        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE)
                            "${decimalFormat.format(productViewModel.getTotalPrice() * 1.21)}€ (IVA)"
                        else
                            "${decimalFormat.format(productViewModel.getTotalPrice() * 1.21)}€ (IVA)",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
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

}

fun getInitialPrice(viewModel: ProductViewModel): Double {
    var totalPrice = 0.0
    for (pizza in viewModel.selectedProductMap) {
        totalPrice += pizza.key.price * pizza.value.toDouble()
    }
    return totalPrice
}




