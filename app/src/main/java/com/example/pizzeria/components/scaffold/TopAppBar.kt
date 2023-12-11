package com.example.pizzeria.components.scaffold

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_10
import com.example.pizzeria.ui.theme.Palette_1_11
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    route: String,
    navController: NavController,
    productViewModel: ProductViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState,
    userViewModel: UserViewModel,
    dialogViewModel: DialogViewModel,
    context: Context
) {
    var expandedDDMIcon by remember { mutableStateOf(false) }
    var expandedDDMLog by remember { mutableStateOf(false) }
    TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(Palette_1_11),
        title = {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = when (route) {
                        Routes.MainMenu.route -> "Menu "
                        Routes.PizzaMenu.route  -> "Pizzas"
                        Routes.PastaMenu.route -> "Pastas"
                        Routes.MealMenu.route  -> "Meals"
                        Routes.DrinkMenu.route  -> "Drinks"
                        Routes.OrderProduct.route  -> "Your order"
                        Routes.OrderProcess.route  -> "Confirm order"
                        Routes.Login.route  -> "Sign in"
                        Routes.CreateUser.route  -> "Create account"
                        else -> ""
                    },
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontCWGSans
                )

        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_menu_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        actions = {
            when(route) {
                "Login", "CreateUser" -> {

                }
                else -> {

                    BadgedBox(
                        badge = {
                            Text(
                                text = productViewModel.getQuantityProductTotal().toString(),
                                color = Palette_1_1,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .size(18.dp)
                                    .offset((-50).dp, (10).dp)
                                    .background(
                                        Palette_1_10,
                                        RoundedCornerShape(30.dp)

                                    )
                            )
                        }) {
                        IconButton(
                            onClick = { expandedDDMIcon = true }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.foodicon),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                    MyDropDownMenuIconFood(expandedDDMIcon, { expandedDDMIcon = it}, productViewModel, navController, route)
                    IconButton(
                        onClick = {
                            expandedDDMLog = true
//                            if (userViewModel.auth.currentUser != null) {
//                                productViewModel.safeDeleteOrderMap()
//                                userViewModel.auth.signOut()
//                            }
//                            navController.navigate("Login")
                        }
                    ) {
                        Icon(
                            painter = painterResource(if (userViewModel.auth.currentUser == null) R.drawable.baseline_login_24 else R.drawable.baseline_logout_24 ),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    MyDropDownMenuLog(expandedDDMLog, { expandedDDMLog= it}, navController, route, dialogViewModel, productViewModel, userViewModel, context)
                }
            }
        }
    )
}
