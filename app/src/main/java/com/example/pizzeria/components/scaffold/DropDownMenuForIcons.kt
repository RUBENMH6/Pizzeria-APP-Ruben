package com.example.pizzeria.components.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.tostadito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDownMenuIconFood(expanded: Boolean, onExpandedChange: (Boolean) -> Unit, productViewModel: ProductViewModel, navController: NavController, currentRoute: String) {
    val items = productViewModel.getListOfTypeProduct()
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onExpandedChange(false)
        },
        modifier = Modifier
            .background(tostadito)
            .width(160.dp),
        properties = PopupProperties(dismissOnBackPress = true)
    ) {
        items.forEachIndexed { index, it ->
            DropdownMenuItem(
                leadingIcon = {
                    when (it) {
                        "PIZZA" -> {
                            BadgedBox(
                                badge = {
                                    Text(
                                        text = if (productViewModel.getQuantityTypeProduct(it) != 0) productViewModel.getQuantityTypeProduct(it)
                                            .toString() else "",
                                        color = Palette_1_1,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(x = (-40).dp, y = 35.dp)
                                            .size(18.dp)
                                            .background(
                                                if (productViewModel.getQuantityTypeProduct(it) != 0) Palette_1_11 else Color.Transparent,
                                                RoundedCornerShape(30.dp)
                                            )
                                    )
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.pizzaicon),
                                    contentDescription = it,
                                    tint = if (currentRoute == Routes.PizzaMenu.route) Color.White else Palette_1_11,
                                    modifier = Modifier.size(30.dp).padding(start = 5.dp)
                                )
                            }
                        }

                        "PASTA" -> {
                            BadgedBox(
                                badge = {
                                    Text(
                                        text =
                                        if (productViewModel.getQuantityTypeProduct(it) != 0) productViewModel.getQuantityTypeProduct(it)
                                            .toString() else "",
                                        color = Palette_1_1,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(x = (-40).dp, y = 35.dp)
                                            .size(18.dp)
                                            .background(
                                                if (productViewModel.getQuantityTypeProduct(it) != 0) Palette_1_11 else Color.Transparent,
                                                RoundedCornerShape(30.dp)
                                            )
                                    )
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.pastaicon),
                                    contentDescription = it,
                                    tint =  if (currentRoute == Routes.PastaMenu.route) Color.White else Palette_1_11,
                                    modifier = Modifier.size(30.dp).padding(start = 5.dp)
                                )
                            }

                        }

                        "MEAL" -> {
                            BadgedBox(
                                badge = {
                                    Text(
                                        text = if (productViewModel.getQuantityTypeProduct(it) != 0) productViewModel.getQuantityTypeProduct(it)
                                            .toString() else "",
                                        color = Palette_1_1,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(x = (-40).dp, y = 35.dp)
                                            .size(18.dp)
                                            .background(
                                                if (productViewModel.getQuantityTypeProduct(it) != 0) Palette_1_11 else Color.Transparent,
                                                RoundedCornerShape(30.dp)
                                            )
                                    )
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.mealicon),
                                    contentDescription = it,
                                    tint =  if (currentRoute == Routes.MealMenu.route) Color.White else Palette_1_11,
                                    modifier = Modifier.size(30.dp).padding(start = 5.dp)
                                )
                            }
                        }

                        "DRINK"-> {
                            BadgedBox(
                                badge = {
                                    Text(
                                        text = if (productViewModel.getQuantityTypeProduct(it) != 0) productViewModel.getQuantityTypeProduct(it)
                                            .toString() else "",
                                        color = Palette_1_1,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .offset(x = (-40).dp, y = 35.dp)
                                            .size(18.dp)
                                            .background(
                                                if (productViewModel.getQuantityTypeProduct(it) != 0) Palette_1_11 else Color.Transparent,
                                                RoundedCornerShape(30.dp)
                                            )
                                    )
                                }) {
                                Icon(
                                    painter = painterResource(R.drawable.drinkicon),
                                    contentDescription = it,
                                    tint =  if (currentRoute == Routes.DrinkMenu.route) Color.White else Palette_1_11,
                                    modifier = Modifier.size(30.dp).padding(start = 5.dp)
                                )
                            }
                        }
                    }
                },
                text = {
                    when (it) {
                        "PIZZA" -> Text(
                            text = if (currentRoute != Routes.PizzaMenu.route) "Pizzas" else "PIZZAS",
                            fontSize = if (currentRoute != Routes.PizzaMenu.route) 16.sp else 20.sp,
                            color = if (currentRoute != Routes.PizzaMenu.route) Palette_1_11 else Color.White,
                            fontWeight = if (currentRoute != Routes.PizzaMenu.route) FontWeight.Light else FontWeight.ExtraBold
                        )

                        "PASTA" -> Text(
                            text = if (currentRoute != Routes.PastaMenu.route) "Pastas" else "PASTAS",
                            fontSize =  if (currentRoute != Routes.PastaMenu.route) 16.sp else 20.sp,
                            color = if (currentRoute != Routes.PastaMenu.route) Palette_1_11 else Color.White,
                            fontWeight = if (currentRoute != Routes.PastaMenu.route) FontWeight.Light else FontWeight.ExtraBold
                        )

                        "MEAL"-> Text(
                            text = if (currentRoute != Routes.MealMenu.route) "Meals" else "MEALS",
                            fontSize =  if (currentRoute != Routes.MealMenu.route) 16.sp else 20.sp,
                            color = if (currentRoute != Routes.MealMenu.route) Palette_1_11 else Color.White,
                            fontWeight = if (currentRoute != Routes.MealMenu.route) FontWeight.Light else FontWeight.ExtraBold
                        )

                        "DRINK" -> Text(
                            text = if (currentRoute != Routes.DrinkMenu.route) "Drinks" else "DRINKS",
                            fontSize =  if (currentRoute != Routes.DrinkMenu.route) 16.sp else 20.sp,
                            color = if (currentRoute != Routes.DrinkMenu.route) Palette_1_11 else Color.White,
                            fontWeight = if (currentRoute != Routes.DrinkMenu.route) FontWeight.Light else FontWeight.ExtraBold
                        )

                    }
                },
                onClick = {
                    when (it) {
                        "PIZZA" -> navController.navigate(Routes.PizzaMenu.route)
                        "PASTA" -> navController.navigate(Routes.PastaMenu.route)
                        "MEAL" -> navController.navigate(Routes.MealMenu.route)
                        "DRINK" -> navController.navigate(Routes.DrinkMenu.route)
                    }
                    onExpandedChange(false)
                },
                modifier = Modifier.height(60.dp).background(
                    when (it) {
                        "PIZZA" -> if (currentRoute == Routes.PizzaMenu.route) Palette_1_11 else Color.Transparent
                        "PASTA" -> if (currentRoute == Routes.PastaMenu.route) Palette_1_11 else Color.Transparent
                        "MEAL" -> if (currentRoute == Routes.MealMenu.route) Palette_1_11 else Color.Transparent
                        "DRINK" -> if (currentRoute == Routes.DrinkMenu.route) Palette_1_11 else Color.Transparent
                        else -> Color.Transparent
                    }
                ),
                contentPadding = PaddingValues(start = 20.dp)
            )

            if (index < items.size - 1) {
                Divider(color = Palette_1_11)
            }
        }
    }
}