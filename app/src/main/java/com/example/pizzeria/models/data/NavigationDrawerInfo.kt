package com.example.pizzeria.models.data

import android.content.Context
import com.example.pizzeria.R

data class NavigationDrawerInfo(val name: String, val icon: Int, val url: String = "MainMenu")

fun getNavDrawInfo(context: Context): List<NavigationDrawerInfo> {
    return listOf(
        NavigationDrawerInfo(context.getString(R.string.modal_button_mainmenu), R.drawable.baseline_menu_24 ),
        NavigationDrawerInfo(context.getString(R.string.modal_button_pizzamenu), R.drawable.pizzaicon, "PizzaMenu"),
        NavigationDrawerInfo(context.getString(R.string.modal_button_pastamenu), R.drawable.pastaicon, "PastaMenu"),
        NavigationDrawerInfo(context.getString(R.string.modal_button_mealmenu), R.drawable.mealicon, "MealMenu"),
        NavigationDrawerInfo(context.getString(R.string.modal_button_drinkmenu), R.drawable.drinkicon, "DrinkMenu"),
        NavigationDrawerInfo("Login", R.drawable.baseline_login_24, "Login"),
    )
}