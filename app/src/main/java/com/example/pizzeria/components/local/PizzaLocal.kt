package com.example.pizzeria.components.local

import android.content.Context
import androidx.compose.foundation.background
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
import com.example.pizzeria.dialogs.LoginNeededToAccessProfileDialog
import com.example.pizzeria.models.data.getPizzaLocal
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.models.viewmodels.LocalViewModel
import com.example.pizzeria.screens.local.MyLocal
import com.example.pizzeria.ui.theme.tostadito

@Composable
fun PizzaLocal(navController: NavController, localViewModel: LocalViewModel, dialogViewModel: DialogViewModel, context: Context) {
    val locals = getPizzaLocal()
    Column(
        modifier = Modifier.fillMaxWidth().background(tostadito)
    ) {
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
            LoginNeededToAccessProfileDialog(navController,  dialogViewModel, context)
        }
    }
}