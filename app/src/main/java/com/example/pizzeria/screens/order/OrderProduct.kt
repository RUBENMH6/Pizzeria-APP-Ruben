package com.example.pizzeria.screens.order

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.R
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.data.ProductInfo
import com.example.pizzeria.components.products.pizza.MyPizzaCard
import com.example.pizzeria.components.products.pizza.getIngredientsFromPizza
import com.example.pizzeria.dialogs.LoginNeededOrderPizzaDialog
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog
import com.example.pizzeria.dialogs.RemoveOrderPizzaDialog
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_4
import com.example.pizzeria.ui.theme.Palette_1_8
import com.example.pizzeria.ui.theme.Palette_1_9
import java.text.DecimalFormat

@Composable
fun OrderPizza(navController: NavController, productViewModel: ProductViewModel, dialogViewModel: DialogViewModel, context: Context) {
    var totalPrice by remember { mutableStateOf(0.0) }
    totalPrice = getInitialPrice(productViewModel)
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.padding(10.dp)) {
            items(productViewModel.selectedProductList) { product ->
                Spacer(modifier = Modifier.height(10.dp))
                when(product.name) {
                    "Margarita" -> MyCardPedido(product, { totalPrice = it }, productViewModel, R.drawable.pizza1)
                    "Proscuito" -> MyCardPedido(product, { totalPrice = it }, productViewModel, R.drawable.pizza2)
                    "Regina" -> MyCardPedido(product, { totalPrice = it }, productViewModel, R.drawable.pizza3)
                    "Provinciale" -> MyCardPedido(product, { totalPrice = it }, productViewModel, R.drawable.pizza4)
                    "Carbonara" -> MyCardPedido(product, { totalPrice = it }, productViewModel, R.drawable.pizza5)
                    "Calzone" -> MyCardPedido(product, { totalPrice = it }, productViewModel, R.drawable.pizza7)
                }
                Spacer(modifier = Modifier.weight(0.10f))
            }
        }

    }
    if (dialogViewModel.dialogRemoveOrderPizza.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            RemoveOrderPizzaDialog(productViewModel, dialogViewModel)
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
                .fillMaxSize()
                .padding(30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                elevation = CardDefaults.cardElevation(6.dp),
                colors = CardDefaults.cardColors(Palette_1_9),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier
                        .height(40.dp)
                        .weight(0.1f))
                    val decimalFormat = DecimalFormat("#.##")
                    decimalFormat.minimumFractionDigits = 2
                    Text(
                        text = "Total price: ${decimalFormat.format(productViewModel.getTotalPrice() * 1.21)}€ (IVA include) ",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier
                        .height(40.dp)
                        .weight(0.1f))
                }
            }
        }
    }
    if (dialogViewModel.dialogLoginToAccessProfile.value) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            LoginNeededToAccessProfileDialog(navController, productViewModel, dialogViewModel, context)
        }
    }
}

@Composable
fun MyCardPedido(
    productInfo: ProductInfo,
    onTotalPriceChange: (Double) -> Unit,
    viewModel: ProductViewModel,
    imageId: Int
) {
    var ingredients by remember { mutableStateOf("") }
    val totalPrice = viewModel.selectedProductMap[productInfo]?.times(productInfo.price)

    ingredients = getIngredientsFromPizza(productInfo)
    Card(
        colors = CardDefaults.cardColors(Palette_1_8),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(imageId),
                contentDescription = productInfo.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(0.4f)
                    .size(200.dp)
            )
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = productInfo.name,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(start = 12.dp),
                        fontFamily = FontCWGSans
                    )
                    Column() {
                        Row(
                            modifier = Modifier.background(Palette_1_9),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    if (viewModel.selectedProductMap[productInfo]!! > 1) {
                                        viewModel.decrementCounterProduct(productInfo)
                                    } else {
                                        viewModel.decrementCounterProduct(productInfo)
                                        viewModel.removeProductToList(productInfo)
                                        viewModel.removeProductToMap(productInfo)

                                    }
                                },
                                modifier = Modifier.background(Palette_1_9)
                            ) {
                                Icon(
                                    imageVector = ImageVector.Companion.vectorResource(R.drawable.baseline_remove_24),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            Text(
                                text = "${viewModel.selectedProductMap[productInfo]}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(Palette_1_9)
                                    .fillMaxHeight()
                            )
                            IconButton(
                                onClick = {
                                    viewModel.incrementCounterProduct(productInfo)
                                    if (totalPrice != null) {
                                        onTotalPriceChange(totalPrice + productInfo.price)
                                    }
                                },
                                modifier = Modifier.background(Palette_1_9)
                            ) {
                                Icon(
                                    imageVector = ImageVector.Companion.vectorResource(R.drawable.baseline_add_24),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Divider(color = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Row(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = ingredients,
                        textAlign = TextAlign.Center,
                        letterSpacing = 2.sp,
                        lineHeight = 16.sp,
                        color = Color.White
                    )
                }
                Divider(color = Color.White)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text =
                        if (!viewModel.selectedProductMap.isEmpty()) {
                            if (viewModel.selectedProductMap[productInfo]!! < 2) {
                                "${productInfo.price} €"
                            } else {
                                "(${productInfo.price} €) $totalPrice €"
                            }
                        } else {
                            "0"
                        },
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
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




