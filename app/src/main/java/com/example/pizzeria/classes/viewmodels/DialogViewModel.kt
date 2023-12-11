package com.example.pizzeria.classes.viewmodels;

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope


class DialogViewModel : ViewModel() {
    var dialogRemoveOrderPizza = mutableStateOf(false)
    var dialogConfirmOrderPizza = mutableStateOf(false)
    var dialogLoginNeededOrderPizza = mutableStateOf(false)
    var commingToLoginNeededOrderPizza = mutableStateOf(false)


    fun changeStateDialogRemoveOrderPizza() {
        dialogRemoveOrderPizza.value = !dialogRemoveOrderPizza.value
    }

    fun changeStateDialogConfirmOrderPizza() {
        dialogConfirmOrderPizza.value = !dialogConfirmOrderPizza.value
    }

    fun changeStateDialogLoginNeededOrderPizza() {
        dialogLoginNeededOrderPizza.value = !dialogLoginNeededOrderPizza.value
    }

    fun changeStateCommingToLoginNeededOrderPizza() {
        commingToLoginNeededOrderPizza.value = !commingToLoginNeededOrderPizza.value
    }



}