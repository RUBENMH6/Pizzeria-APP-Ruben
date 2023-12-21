package com.example.pizzeria.components.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pizzeria.R
import com.example.pizzeria.models.data.ProductInfo
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.Palette_1_11

@Composable
fun RowPriceAndCounter(productViewModel: ProductViewModel, productInfo: ProductInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
                .padding(start = 10.dp, top = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "${productInfo.price} €",
                fontWeight = FontWeight.Bold,
                color = Palette_1_11
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (productViewModel.selectedProductMap[productInfo] != null) {
                            if (productViewModel.selectedProductMap[productInfo]!! > 0) {
                                Palette_1_11
                            } else {
                                Color.Transparent
                            }
                        } else {
                            Color.Transparent
                        }
                    ),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (productViewModel.getQuantityProduct(productInfo) != null) {
                        if (productViewModel.getQuantityProduct(productInfo)!! > 0) {
                            RowButtonsActived(productViewModel, productInfo)
                        }
                } else {
                    RowButtonDesactived(productViewModel, productInfo)
                }
            }
        }
    }
}

@Composable
fun RowButtonsActived(productViewModel : ProductViewModel, productInfo: ProductInfo) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(0.33f)
        ) {
            IconButton(
                onClick = {
                    if (productViewModel.selectedProductMap[productInfo]!! > 1) {
                        productViewModel.decrementCounterProduct(productInfo)
                    } else {
                        productViewModel.decrementCounterProduct(productInfo)
                        productViewModel.removeProductToList(productInfo)
                        productViewModel.removeProductToMap(productInfo)
                    }
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_remove_24),
                    contentDescription = "Remove",
                    tint = if (productViewModel.selectedProductMap[productInfo] != null) {
                        if (productViewModel.selectedProductMap[productInfo]!! > 0) {
                            Color.White
                        } else {
                            Palette_1_11
                        }
                    } else {
                        Palette_1_11
                    },
                    modifier = Modifier.width(20.dp)
                )
            }
        }
        Column(
            modifier = Modifier.weight(0.33f).padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Text(
                text = "${productViewModel.selectedProductMap[productInfo]}",
                color = if (productViewModel.selectedProductMap[productInfo] != null) {
                    if (productViewModel.selectedProductMap[productInfo]!! > 0) {
                        Color.White
                    } else {
                        Palette_1_11
                    }
                } else {
                    Palette_1_11
                },
                fontWeight = FontWeight.Bold
            )
        }
        Column(
            modifier = Modifier.weight(0.33f)

        ) {
            IconButton(
                onClick = {
                    if (!productViewModel.selectedProductMap.contains(productInfo)) {
                        productViewModel.addProductToOrder(productInfo)
                        productViewModel.addProductInMap(productInfo)
                    }
                    productViewModel.incrementCounterProduct(productInfo)
                },
                modifier = Modifier.background(
                    if (productViewModel.selectedProductMap[productInfo] != null) {
                        if (productViewModel.selectedProductMap[productInfo]!! > 0) {
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
                    tint = if (productViewModel.selectedProductMap[productInfo] != null) {
                        if (productViewModel.selectedProductMap[productInfo]!! > 0) {
                            Color.White
                        } else {
                            Palette_1_11
                        }
                    } else {
                        Palette_1_11
                    },
                    modifier = Modifier.width(20.dp)

                )
            }
        }

    }


}

@Composable
fun RowButtonDesactived(productViewModel: ProductViewModel, productInfo: ProductInfo) {
    IconButton(
        onClick = {
            if (!productViewModel.selectedProductMap.contains(productInfo)) {
                productViewModel.addProductToOrder(productInfo)
                productViewModel.addProductInMap(productInfo)
            }
            productViewModel.incrementCounterProduct(productInfo)
        },
        modifier = Modifier.background(
            if (productViewModel.getQuantityProduct(productInfo) != null) {
                Palette_1_11

            } else {
                Color.Transparent
            }
        )
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
            contentDescription = "Add",
            tint = if (productViewModel.selectedProductMap[productInfo] != null) {
                if (productViewModel.selectedProductMap[productInfo]!! > 0) {
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