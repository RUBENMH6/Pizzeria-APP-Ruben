package com.example.pizzeria.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.classes.data.getPizzaLocal
import com.example.pizzeria.classes.viewmodels.LocalViewModel
import com.example.pizzeria.components.local.MyLocal

@Composable
fun PizzaLocal(navController: NavController, localViewModel: LocalViewModel) {
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
}