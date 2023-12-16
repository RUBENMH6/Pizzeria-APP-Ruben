package com.example.pizzeria.components.products.order

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_9

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
            .height(40.dp),
        shape = ShapeDefaults.Small,
        colors = ButtonDefaults.buttonColors(Palette_1_11)

    ) {
        Text("Process order")
    }
}