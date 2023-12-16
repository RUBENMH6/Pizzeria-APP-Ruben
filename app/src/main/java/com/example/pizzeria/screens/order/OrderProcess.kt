package com.example.pizzeria.screens.order

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.components.products.order.MyOrderProcessButton
import com.example.pizzeria.components.products.order.MyTicket
import com.example.pizzeria.dialogs.ConfirmOrderPizzaDialog
import com.example.pizzeria.dialogs.LoginNeededOrderPizzaDialog
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog
import com.example.pizzeria.ui.theme.Palette_1_10
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_4
import com.example.pizzeria.ui.theme.Palette_1_8
import com.example.pizzeria.ui.theme.Palette_1_9
import com.example.pizzeria.ui.theme.tostadito
import java.text.DecimalFormat

@Composable
fun OrderProcess(navController: NavController, productViewModel: ProductViewModel, dialogViewModel: DialogViewModel, context: Context, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().background(tostadito),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Siempre y cuando hayan pizzas en el mapa, aparecerá el Ticket
        if (productViewModel.getQuantityProductTotal() != 0) {
            MyTicket(productViewModel)
            Spacer(modifier = Modifier.height(20.dp))
            MyOrderProcessButton(dialogViewModel, userViewModel)
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                //Si se llega a vaciar la lista de pedidos en el OrderProcess,
                //muestra por pantalla el mensaje de que no hay pizzas en el pedido
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
        }

    }
    if (dialogViewModel.dialogLoginNeededOrderPizza.value) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            LoginNeededOrderPizzaDialog(navController, productViewModel, dialogViewModel, context)
        }
    }
    if (dialogViewModel.dialogLoginToAccessProfile.value) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Transparent.copy(0.2f)),
            contentAlignment = Alignment.Center
        ) {
            dialogViewModel.commingToLoginNeededOrderPizza.value = true
            LoginNeededToAccessProfileDialog(navController, productViewModel, dialogViewModel, context)
        }
    }

    if (dialogViewModel.dialogConfirmOrderPizza.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ConfirmOrderPizzaDialog(navController, productViewModel, dialogViewModel, context)
        }
    }

}







