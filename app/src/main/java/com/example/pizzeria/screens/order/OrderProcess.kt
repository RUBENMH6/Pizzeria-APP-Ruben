package com.example.pizzeria.screens.order

import android.content.Context
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
import com.example.pizzeria.dialogs.ConfirmOrderPizzaDialog
import com.example.pizzeria.dialogs.LoginNeededOrderPizzaDialog
import com.example.pizzeria.ui.theme.Palette_1_10
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_4
import com.example.pizzeria.ui.theme.Palette_1_8
import com.example.pizzeria.ui.theme.Palette_1_9
import java.text.DecimalFormat

@Composable
fun OrderProcess(navController: NavController, pizzaViewModel: ProductViewModel, dialogViewModel: DialogViewModel, context: Context, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Siempre y cuando hayan pizzas en el mapa, aparecerá el Ticket
        if (pizzaViewModel.getQuantityProductTotal() != 0) {
            MyTicket(pizzaViewModel)
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
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoginNeededOrderPizzaDialog(navController, pizzaViewModel, dialogViewModel, context)
        }
    }

    if (dialogViewModel.dialogConfirmOrderPizza.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ConfirmOrderPizzaDialog(navController, pizzaViewModel, dialogViewModel, context)
        }
    }

}

@Composable
fun MyTicket(viewModel: ProductViewModel) {
    val pizzaMap = viewModel.selectedProductMap //Recibimos el mapa con las pizzas y sus respectivas cantidades
    val headerTable = listOf("PRODUCT", "QUANTITY", "PRICE", "SUBTOTAL") //Se hace una lista para poder iterar el Header de la tabla
    val IVA = 0.21
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row() {
            //Primera iteración para mostrar la cabecera de la tabla
            headerTable.forEach {
                Text(
                    text = it,
                    modifier = Modifier.weight(0.25f), //Para que ocupen las 4 columnas lo mismo.
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        //Recorremos el mapa y obtenemos en cada iteración la key, que es un pizzaInfo
        for (pizza in pizzaMap) {
            var expanded by remember { mutableStateOf(false) } //Variable que controla la visibilidad del MyDropDownMenu

            //Creamos por cada iteracion una lista que constituirá una fila del Ticket
            //Lo hacemos en una lista para poder iterar después
            val file = listOf(
                pizza.key.name.capitalize(), //El nombre de la pizza con la primera letra en mayusculas
                pizza.value.toString(), //La cantidad de pizzas que hay de ese tipo
                pizza.key.price.toString(), //El precio de esa pizza
                (pizza.value * pizza.key.price).toString() //Calculamos el subtotal multiplicando el precio de la pizza por la cantidad de esa pizza
            )

            //Usamos este if, para que desaparezcan las filas a las cuales hemos eliminado
            if (pizza.value > 0) {
                Row(
                    modifier = Modifier
                        .clickable { expanded = !expanded }
                        .height(25.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    ) {
                    file.forEach {
                        Text(
                            text = it,
                            modifier = Modifier.weight(0.25f), //Seguimos el mismo formato que la cabecera para que guarde cierta cohesión y visualmente se vea bien
                            textAlign = TextAlign.Center
                        )
                    }
                    //Antes de acabar la Row, añadimos el MyDropDownMenu para que aparezca a la derecha, por estética
                    Column {
                        MyDropDownMenu(expanded, { expanded = it }, viewModel, pizza.key)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            val decimalFormat = DecimalFormat("#.##")
            decimalFormat.minimumFractionDigits = 2
            val totalPriceIVA = decimalFormat.format(viewModel.getTotalPrice() * (1  + IVA))
            Text(
                text = "TOTAL PRICE: $totalPriceIVA€ (${100*IVA}%)",
                modifier = Modifier.weight(0.25f),
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


//Función para mostrar el botón que corresponde con la acción de realizar un pedido
@Composable
fun MyOrderProcessButton(dialogViewModel: DialogViewModel, userViewModel: UserViewModel) {
    Button(
        onClick = {
            dialogViewModel.dialogConfirmOrderPizza.value = false
            dialogViewModel.dialogLoginNeededOrderPizza.value = false
            if (userViewModel.auth.currentUser != null ) {
                dialogViewModel.dialogConfirmOrderPizza.value = true
            } else {
                dialogViewModel.dialogLoginNeededOrderPizza.value = true
            }

        },
        modifier = Modifier
            .width(200.dp)
            .height(40.dp)
            .clip(RectangleShape),
        colors = ButtonDefaults.buttonColors(Palette_1_9)

    ) {
        Text("Process order")
    }
}


@Composable
fun MyDropDownMenu(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    viewModel: ProductViewModel,
    productInfo: ProductInfo
) {
    var selectedText by remember { mutableStateOf("") }
    val items = listOf("Remove file", "Remove item", "Add item")
    Spacer(modifier = Modifier.height(65.dp))
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onExpandedChange(false)
        },
        modifier = Modifier.background(Palette_1_4)
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
                        "Remove file" -> {
                            Icon(
                                imageVector = ImageVector.Companion.vectorResource(R.drawable.baseline_sync_24),
                                contentDescription = null,
                                tint = Palette_1_11
                            )
                        }
                        "Remove item" -> {
                            Icon(
                                imageVector = ImageVector.Companion.vectorResource(R.drawable.baseline_remove_24),
                                contentDescription = null,
                                tint = Palette_1_11
                            )
                        }
                        "Add item" -> {
                            Icon(
                                imageVector = ImageVector.Companion.vectorResource(R.drawable.baseline_add_24),
                                contentDescription = null,
                                tint = Palette_1_11
                            )
                        }
                    }
                },
                text = { Text(text = it, color = Palette_1_11, fontSize = 16.sp) },
                onClick = {
                    when (it) {
                        "Remove file" -> {
                            viewModel.decrementCounterProduct(productInfo)
                            viewModel.removeProductToList(productInfo)
                            viewModel.removeProductToMap(productInfo)
                        }
                        "Remove item" -> {
                            if (viewModel.getQuantityProduct(productInfo)!! <= 1) {
                                viewModel.decrementCounterProduct(productInfo)
                                viewModel.removeProductToList(productInfo)
                                viewModel.removeProductToMap(productInfo)
                            } else {
                                viewModel.decrementCounterProduct(productInfo)
                            }
                        }
                        "Add item" -> {
                            viewModel.incrementCounterProduct(productInfo)
                        }
                    }
                    selectedText = it
                }
            )
        }
    }
}
