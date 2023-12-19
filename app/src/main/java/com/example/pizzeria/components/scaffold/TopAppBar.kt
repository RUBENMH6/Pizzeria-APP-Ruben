package com.example.pizzeria.components.scaffold

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.models.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_11
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(route: String, navController: NavController, productViewModel: ProductViewModel, scope: CoroutineScope, drawerState: DrawerState, userViewModel: UserViewModel, dialogViewModel: DialogViewModel, context: Context) {
    var expandedDDMIcon by remember { mutableStateOf(false) }
    var expandedDDMLog by remember { mutableStateOf(false) }
    TopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(Palette_1_11),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = when (route) {
                        Routes.MainMenu.route -> context.getString(R.string.screen_top_name_mainmenu)
                        Routes.PizzaMenu.route  -> context.getString(R.string.screen_top_name_pizzamenu)
                        Routes.PastaMenu.route -> context.getString(R.string.screen_top_name_pastamenu)
                        Routes.MealMenu.route  -> context.getString(R.string.screen_top_name_mealmenu)
                        Routes.DrinkMenu.route  -> context.getString(R.string.screen_top_name_drinkmenu)
                        Routes.OrderProduct.route  -> context.getString(R.string.screen_top_name_orderproduct)
                        Routes.OrderProcess.route  -> context.getString(R.string.screen_top_name_orderprocess)
                        Routes.Login.route  -> "Sign in"
                        Routes.CreateUser.route  -> "Register"
                        else -> ""
                    },
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight(500),
                    fontFamily = FontCWGSans
                )
            }


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
                    BadgedBox(
                        badge = {
                            Text(
                                text = if (productViewModel.getQuantityProductTotal() != 0) productViewModel.getQuantityProductTotal().toString() else "",
                                color = Palette_1_1,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .size(18.dp)
                                    .offset((-50).dp, (10).dp)

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
                        MyDropDownMenuIconFood(expandedDDMIcon, { expandedDDMIcon = it}, productViewModel, navController, route)
                    }

                    IconButton(
                        onClick = { expandedDDMLog = true }
                    ) {
                        Icon(
                            painter = painterResource(if (userViewModel.auth.currentUser == null) R.drawable.baseline_login_24 else R.drawable.baseline_logout_24 ),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    MyDropDownMenuLog(expandedDDMLog, { expandedDDMLog= it}, navController, route, dialogViewModel,  userViewModel, context)


        }
    )
}
