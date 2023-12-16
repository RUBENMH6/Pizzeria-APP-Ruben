package com.example.pizzeria.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.*

@Composable
fun RemoveOrderPizzaDialog(productViewModel: ProductViewModel, dialogViewModel: DialogViewModel) {
    AlertDialog(
        onDismissRequest = {
            dialogViewModel.dialogRemoveOrderPizza.value = false
        },
        title = {
            Text(text = "Warning! Deleting order.")
        },
        text =
        {
            Divider(color = Palette_1_11)
            Text(text = "\nAre you sure you want to delete the pizza list? This action cannot be undone.", textAlign = TextAlign.Justify, color = Palette_1_11 ) }
        ,
        confirmButton = {
            Button(
                onClick = {
                    productViewModel.safeDeleteOrderMap()
                    dialogViewModel.dialogRemoveOrderPizza.value = false
                },
                colors = ButtonDefaults.buttonColors(Palette_1_11),
                modifier = Modifier.padding(end = 10.dp)) {
                Text("Confirm")
            }

        },
        dismissButton = {
            Button(
                onClick = {
                    dialogViewModel.dialogRemoveOrderPizza.value = false
                },
                colors = ButtonDefaults.buttonColors(Palette_1_11),
                modifier = Modifier.padding(end = 40.dp)) {
                Text("Dismiss")
            }
        },
        containerColor = tostadito,
        titleContentColor = Palette_1_11,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.border(1.dp, Palette_1_11, RoundedCornerShape(24.dp))
    )
}