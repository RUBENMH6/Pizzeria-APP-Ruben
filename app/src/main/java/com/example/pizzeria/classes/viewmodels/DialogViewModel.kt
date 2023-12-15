package com.example.pizzeria.classes.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class DialogViewModel : ViewModel() {

    var dialogRemoveOrderPizza = mutableStateOf(false)
    var dialogConfirmOrderPizza = mutableStateOf(false)
    var dialogLoginNeededOrderPizza = mutableStateOf(false)
    var dialogLoginToAccessProfile = mutableStateOf(false)
    var commingToLoginNeededOrderPizza = mutableStateOf(false)


}