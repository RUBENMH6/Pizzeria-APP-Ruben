package com.example.pizzeria.components.products.pizza

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzeria.components.products.RowPriceAndCounter
import com.example.pizzeria.models.data.ProductInfo
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.VerdeItalia
import com.example.pizzeria.ui.theme.tostadito


@Composable
fun MyPizzaCard(productInfo: ProductInfo, productViewModel: ProductViewModel, imageId: Int, configuration: Configuration) {
    var ingredients by remember { mutableStateOf("") }
    ingredients = getIngredientsFromPizza(productInfo)


    Card(
        colors = CardDefaults.cardColors(tostadito),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(tostadito),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(imageId),
                            contentDescription = productInfo.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(160.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .fillMaxSize()
                                .border(6.dp, tostadito, RoundedCornerShape(12.dp))
                        )
                    }
                    Row() {
                        Column(
                            modifier = Modifier
                                .weight(0.6f)
                                .height(50.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = productInfo.name,
                                fontSize = 16.sp,
                                color = Palette_1_11,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(start = 12.dp, end = 12.dp),
                                fontFamily = FontCWGSans,
                                maxLines = 1,
                                softWrap = false,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Row(
                            modifier = Modifier.background(
                                if (productViewModel.getQuantityProduct(productInfo) != null) {
                                    if (productViewModel.getQuantityProduct(productInfo)!! > 0) {
                                        Palette_1_11
                                    } else {
                                        Color.Transparent
                                    }
                                } else {
                                    Color.Transparent
                                }
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .border(1.dp, Palette_1_11)
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
                    RowPriceAndCounter(productViewModel, productInfo)
                }
            }
    }
}
