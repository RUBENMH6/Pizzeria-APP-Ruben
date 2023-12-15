package com.example.pizzeria.components.products.pizza

import com.example.pizzeria.classes.data.ProductInfo


fun getIngredientsFromPizza(product: ProductInfo): String {
    var ingredientes = ""
    for (i in 0..product.ingredients.size - 1) {
        if (i == product.ingredients.size - 1) {
            ingredientes += "y ${product.ingredients[i]}"
        } else {
            if (i + 1 == product.ingredients.size - 1) {
                ingredientes += "${product.ingredients[i]} "
            } else {
                ingredientes += "${product.ingredients[i]}, "
            }
        }
    }
    return ingredientes
}

