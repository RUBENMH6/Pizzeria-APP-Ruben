package com.example.pizzeria.components.products.order

import android.content.Context
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pizzeria.R
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.ui.views.auth.UserViewModel
import com.example.pizzeria.ui.theme.Palette_1_11

//Función para mostrar el botón que corresponde con la acción de realizar un pedido
@Composable
fun MyOrderProcessButton(dialogViewModel: DialogViewModel, userViewModel: UserViewModel, context: Context) {
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
            .height(40.dp),
        shape = ShapeDefaults.Small,
        colors = ButtonDefaults.buttonColors(Palette_1_11)

    ) {
        Text(context.getString(R.string.ticket_button_process_order))
    }
}