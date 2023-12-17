package com.example.pizzeria.components.products.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzeria.R
import com.example.pizzeria.components.products.pizza.getIngredientsFromPizza
import com.example.pizzeria.models.data.ProductInfo
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.VerdeItalia
import com.example.pizzeria.ui.theme.tostadito

@Composable
fun MyCardPedido(productInfo: ProductInfo, onTotalPriceChange: (Double) -> Unit, viewModel: ProductViewModel, imageId: Int) {
    var ingredients by remember { mutableStateOf("") }
    val totalPrice = viewModel.selectedProductMap[productInfo]?.times(productInfo.price)

    ingredients = getIngredientsFromPizza(productInfo)
    Card(
        colors = CardDefaults.cardColors(tostadito),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.padding(5.dp)
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
                        color = Palette_1_11,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(0.8f)
                            .padding(start = 12.dp),
                        fontFamily = FontCWGSans,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis
                    )
                    Column() {
                        Row(
                            modifier = Modifier.background(Palette_1_11),
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
                                modifier = Modifier.background(Palette_1_11)
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.baseline_remove_24),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            Text(
                                text = "${viewModel.selectedProductMap[productInfo]}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .background(Palette_1_11)
                                    .fillMaxHeight()
                            )
                            IconButton(
                                onClick = {
                                    viewModel.incrementCounterProduct(productInfo)
                                    if (totalPrice != null) {
                                        onTotalPriceChange(totalPrice + productInfo.price)
                                    }
                                },
                                modifier = Modifier.background(Palette_1_11)
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(1.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp).border(1.dp, Palette_1_11)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(0.33f)
                            .fillMaxHeight()
                            .background(VerdeItalia)
                    ) {}
                    Column(
                        modifier = Modifier
                            .weight(0.33f)
                            .fillMaxHeight()
                            .background(Color.White)
                    ) {}
                    Column(
                        modifier = Modifier
                            .weight(0.33f)
                            .fillMaxHeight()
                            .background(Palette_1_11)
                    ) {}
                }
                Spacer(modifier = Modifier.height(1.dp))
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
                        color = Palette_1_11
                    )
                }
                Divider(color = Palette_1_11)
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
                        color = Palette_1_11
                    )
                }
            }
        }
    }
}