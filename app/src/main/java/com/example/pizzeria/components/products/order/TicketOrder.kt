package com.example.pizzeria.components.products.order

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pizzeria.R
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.Palette_1_11
import java.text.DecimalFormat

@Composable
fun MyTicket(viewModel: ProductViewModel, context: Context) {
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
                    fontWeight = FontWeight.Bold,
                    color = Palette_1_11
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
                            textAlign = TextAlign.Center,
                            color = Palette_1_11
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
                text = context.getString(R.string.ticket_total_price) + ": $totalPriceIVA€ (${100*IVA}%)",
                modifier = Modifier.weight(0.25f),
                textAlign = TextAlign.Right,
                fontWeight = FontWeight.Bold,
                color = Palette_1_11
            )
        }
    }
}