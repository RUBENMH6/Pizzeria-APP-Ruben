package com.example.pizzeria.models


sealed class Routes(val route : String){
    object Login: Routes("Login")
    object CreateUser: Routes("CreateUser")
    object MainMenu: Routes("MainMenu")
    object PizzaMenu: Routes("PizzaMenu")
    object PastaMenu: Routes("PastaMenu")
    object DrinkMenu: Routes("DrinkMenu")
    object MealMenu: Routes("MealMenu")
    object OrderProduct: Routes("OrderProduct")
    object OrderProcess: Routes("OrderProcess")
    object Local: Routes("Local")
    object Profile: Routes("Profile")
    object SplashScreen: Routes("SplashScreen")
    object SplashScreenOrderConfirmed: Routes("SplashScreenOrderConfirmed")
}