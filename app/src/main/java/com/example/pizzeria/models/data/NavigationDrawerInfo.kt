package com.example.pizzeria.models.data

import com.example.pizzeria.R

data class NavigationDrawerInfo(val name: String, val icon: Int, val url: String = "MainMenu")

fun getNavDrawInfo(): List<NavigationDrawerInfo> {
    return listOf(
        NavigationDrawerInfo("Menu", R.drawable.baseline_menu_24 ),
        NavigationDrawerInfo("Pizzas", R.drawable.pizzaicon, "PizzaMenu"),
        NavigationDrawerInfo("Pastas", R.drawable.pastaicon, "PastaMenu"),
        NavigationDrawerInfo("Meals", R.drawable.mealicon, "MealMenu"),
        NavigationDrawerInfo("Drinks", R.drawable.drinkicon, "DrinkMenu"),
        NavigationDrawerInfo("Login", R.drawable.baseline_login_24, "Login"),
    )
}