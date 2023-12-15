package com.example.pizzeria.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.classes.data.getPizzaLocal
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.LocalViewModel
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.components.local.MyLocal
import com.example.pizzeria.dialogs.LoginNeededOrderPizzaDialog
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog

@Composable
fun PizzaLocal(navController: NavController, localViewModel: LocalViewModel, dialogViewModel: DialogViewModel, productViewModel: ProductViewModel, context: Context) {
    val locals = getPizzaLocal()
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
            items(getPizzaLocal().size) {
                Spacer(modifier = Modifier.height(10.dp))
                MyLocal(locals[it], navController, localViewModel)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
    if (dialogViewModel.dialogLoginToAccessProfile.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoginNeededToAccessProfileDialog(navController, productViewModel, dialogViewModel, context)
        }
    }
}