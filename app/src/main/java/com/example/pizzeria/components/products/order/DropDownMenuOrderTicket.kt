package com.example.pizzeria.components.products.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pizzeria.R
import com.example.pizzeria.models.data.ProductInfo
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.Palette_1_10
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.tostadito

@Composable
fun MyDropDownMenu(expanded: Boolean, onExpandedChange: (Boolean) -> Unit, viewModel: ProductViewModel, productInfo: ProductInfo) {
    var selectedText by remember { mutableStateOf("") }
    val items = listOf("Remove all", "Remove", "Add")
    Spacer(modifier = Modifier.height(65.dp))
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onExpandedChange(false)
        },
        modifier = Modifier.background(tostadito)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalAlignment = Alignment.End
        ) {
            Text(productInfo.name, fontWeight = FontWeight.Bold, color = Palette_1_10)
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Palette_1_11)
        }
        items.forEach {
            DropdownMenuItem(
                leadingIcon = {
                    when (it) {
                        "Remove all" -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_sync_24),
                                contentDescription = null,
                                tint = Palette_1_11
                            )
                        }
                        "Remove" -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_remove_24),
                                contentDescription = null,
                                tint = Palette_1_11
                            )
                        }
                        "Add" -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                                contentDescription = null,
                                tint = Palette_1_11
                            )
                        }
                    }
                },
                text = {
                    Text(
                        text = it,
                        color = Palette_1_11,
                        fontSize = 16.sp) },
                onClick = {
                    when (it) {
                        "Remove all" -> {
                            viewModel.decrementCounterProduct(productInfo)
                            viewModel.removeProductToList(productInfo)
                            viewModel.removeProductToMap(productInfo)
                        }
                        "Remove" -> {
                            if (viewModel.getQuantityProduct(productInfo)!! <= 1) {
                                viewModel.decrementCounterProduct(productInfo)
                                viewModel.removeProductToList(productInfo)
                                viewModel.removeProductToMap(productInfo)
                            } else {
                                viewModel.decrementCounterProduct(productInfo)
                            }
                        }
                        "Add" -> {
                            viewModel.incrementCounterProduct(productInfo)
                        }
                    }
                    selectedText = it
                }
            )
        }
    }
}
