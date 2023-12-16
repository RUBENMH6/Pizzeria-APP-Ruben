package com.example.pizzeria.components.products.pizza

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
import coil.compose.ImagePainter
import com.example.pizzeria.R
import com.example.pizzeria.classes.data.ProductInfo
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_4
import com.example.pizzeria.ui.theme.Palette_1_6
import com.example.pizzeria.ui.theme.Palette_1_8
import com.example.pizzeria.ui.theme.Palette_1_9
import com.example.pizzeria.ui.theme.VerdeItalia
import com.example.pizzeria.ui.theme.tostadito
import com.google.firebase.Firebase
import com.google.firebase.storage.storage


@Composable
fun MyPizzaCard(
    productInfo: ProductInfo,
    viewModel: ProductViewModel,
    imageId: Int,
    configuration: Configuration
) {
    var ingredients by remember { mutableStateOf("") }
    ingredients = getIngredientsFromPizza(productInfo)


    Card(
        colors = CardDefaults.cardColors(tostadito),
        elevation = CardDefaults.cardElevation(10.dp),
        modifier = Modifier.padding(5.dp)
    ) {
        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {


            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(imageId),
                        contentDescription = productInfo.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxSize()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
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
                                    .padding(start = 12.dp),
                                fontFamily = FontCWGSans
                            )
                        }
                        Row(
                            modifier = Modifier.background(
                                if (viewModel.getQuantityProduct(productInfo) != null) {
                                    if (viewModel.getQuantityProduct(productInfo)!! > 0) {
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
                            if (viewModel.getQuantityProduct(productInfo) != null) {
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
                                    modifier = Modifier.background(
                                        if (viewModel.selectedProductMap[productInfo] != null) {
                                            if (viewModel.selectedProductMap[productInfo]!! > 0) {
                                                Palette_1_11
                                            } else {
                                                Color.Transparent
                                            }
                                        } else {
                                            Color.Transparent
                                        }
                                    )
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.baseline_remove_24),
                                        contentDescription = "Remove",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .width(30.dp)
                                    )
                                }
                                if (viewModel.getQuantityProduct(productInfo) != null) {
                                    if (viewModel.getQuantityProduct(productInfo)!! > 0) {
                                        Text(
                                            text = "${viewModel.selectedProductMap[productInfo]}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    } else {
                                        Text(
                                            text = "",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                } else {
                                    Text(
                                        text = "",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        if (!viewModel.selectedProductMap.contains(productInfo)) {
                                            viewModel.addProductToOrder(productInfo)
                                            viewModel.addProductInMap(productInfo)
                                        }
                                        viewModel.incrementCounterProduct(productInfo)
                                    },
                                    modifier = Modifier.background(
                                        if (viewModel.selectedProductMap[productInfo] != null) {
                                            if (viewModel.selectedProductMap[productInfo]!! > 0) {
                                                Palette_1_11
                                            } else {
                                                Color.Transparent
                                            }
                                        } else {
                                            Color.Transparent
                                        }
                                    )
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                                        contentDescription = null,
                                        tint = if (viewModel.selectedProductMap[productInfo] != null) {
                                            if (viewModel.selectedProductMap[productInfo]!! > 0) {
                                                Color.White
                                            } else {
                                                Palette_1_11
                                            }
                                        } else {
                                            Palette_1_11
                                        }
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = {
                                        if (!viewModel.selectedProductMap.contains(productInfo)) {
                                            viewModel.addProductToOrder(productInfo)
                                            viewModel.addProductInMap(productInfo)
                                        }
                                        viewModel.incrementCounterProduct(productInfo)
                                    },
                                    modifier = Modifier.background(
                                        if (viewModel.getQuantityProduct(productInfo) != null) {

                                            Palette_1_11

                                        } else {
                                            Color.Transparent
                                        }
                                    )
                                ) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                                        contentDescription = "Add",
                                        tint = if (viewModel.selectedProductMap[productInfo] != null) {
                                            if (viewModel.selectedProductMap[productInfo]!! > 0) {
                                                Color.White
                                            } else {
                                                Palette_1_11
                                            }
                                        } else {
                                            Palette_1_11
                                        }
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Divider(color = Palette_1_11)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
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
                    Divider(color = Palette_1_11)
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
                            text = "${productInfo.price} €",
                            fontWeight = FontWeight.Bold,
                            color = Palette_1_11
                        )
                    }
                }

            }
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells
            ) {

            }
        }
    }
}
