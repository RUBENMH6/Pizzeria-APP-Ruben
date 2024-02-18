package com.example.pizzeria.ui.views.main

import com.example.pizzeria.models.Routes

fun listOfButtons() : List<String> {
    return listOf(
        Routes.PizzaMenu.route,
        Routes.PastaMenu.route,
        Routes.MealMenu.route,
        Routes.DrinkMenu.route,
        Routes.Local.route)
}

