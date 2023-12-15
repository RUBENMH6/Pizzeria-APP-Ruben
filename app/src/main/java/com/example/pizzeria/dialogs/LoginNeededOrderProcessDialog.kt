package com.example.pizzeria.dialogs


import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.ui.theme.*


@Composable
fun LoginNeededOrderPizzaDialog(navController: NavController, productViewModel: ProductViewModel, dialogViewModel: DialogViewModel, context: Context) {

    AlertDialog(
        onDismissRequest = { dialogViewModel.dialogLoginNeededOrderPizza.value = false },
        title = { Text(text = "Please. Sign in!") },
        text = {
            Divider(color = Palette_1_8)
            Text("\nYou need to be logged in to place orders.", color = Palette_1_10) },
        confirmButton = {
            Button(
                onClick = {
                    dialogViewModel.dialogLoginNeededOrderPizza.value = false
                    navController.navigate(Routes.Login.route)
                          },
                colors = ButtonDefaults.buttonColors(Palette_1_8),
                modifier = Modifier.padding(end = 10.dp)) {
                Text("Sign in")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    dialogViewModel.dialogLoginNeededOrderPizza.value = false
                          },
                colors = ButtonDefaults.buttonColors(Palette_1_8),
                modifier = Modifier.padding(end = 40.dp)) {
                Text("Dismiss")
            }
        },
        containerColor = Palette_1_2,
        titleContentColor = Palette_1_11,
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.border(1.dp, Palette_1_11, RoundedCornerShape(24.dp))
    )
}