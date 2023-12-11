package com.example.pizzeria.classes.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.pizzeria.classes.data.ProductInfo

class ProductViewModel : ViewModel() {
    var selectedProductList = mutableStateListOf<ProductInfo>()
    var productList = mutableStateListOf<ProductInfo>()
    var selectedProductMap = mutableStateMapOf<ProductInfo,Int>()
    var selectedQuantityMap = mutableStateMapOf<String, Int>()



    fun getOrderProduct() :SnapshotStateList<ProductInfo> {
        return selectedProductList
    }

    fun addProductToOrder(productInfo: ProductInfo) {
        selectedProductList.add(productInfo)
    }

    fun addProductInMap(productInfo: ProductInfo) {
        selectedProductMap[productInfo] = 0
    }

    fun removeProductToList(productInfo: ProductInfo) {
        selectedProductList.remove(productInfo)
    }

    fun removeProductToMap(productInfo: ProductInfo) {
        selectedProductMap.remove(productInfo)
    }

    fun incrementCounterProduct(productInfo: ProductInfo) {
        val click = selectedProductMap[productInfo]!!
        selectedProductMap[productInfo] = click + 1
    }

    fun decrementCounterProduct(productInfo: ProductInfo) {
        val click = selectedProductMap[productInfo]!!
        if (selectedProductMap[productInfo] != 0) {
            selectedProductMap[productInfo] = click - 1
        }

    }

    fun getQuantityProduct(productInfo: ProductInfo) : Int?  {
        return selectedProductMap[productInfo]
    }

    fun getQuantityProductTotal() : Int {
        var total = 0
        for (pizza in selectedProductMap) {
            total += pizza.value
        }
        return total
    }

    fun getTotalPrice() : Float {
        var total = 0.0f
        for(pizza in selectedProductMap) {
            total += (pizza.value * pizza.key.price).toFloat()
        }
        return total
    }

    fun isMapEmpty() : Boolean {
        return selectedProductMap.isEmpty()
    }

    fun isListEmpty() : Boolean {
        return selectedProductList.isEmpty()
    }

    fun getQuantityPizza() : Int {
        var total = 0
        for (pizza in selectedProductMap){
            if (pizza.key.type == "PIZZA") {
                total += pizza.value
            }
        }
        return total
    }

    fun getQuantityProducts() : Map<String, Int> {
        var countPizza = 0
        var countPasta = 0
        var countMeal = 0
        var countDrink = 0
        for (product in selectedProductMap) {
            when (product.key.type) {
                "PIZZA" ->  countPizza += product.value
                "PASTA" ->  countPasta += product.value
                "MEAL" ->  countMeal += product.value
                "DRINK" ->  countDrink += product.value
            }
        }
        selectedQuantityMap["PIZZA"] = countPizza
        selectedQuantityMap["PASTA"] = countPasta
        selectedQuantityMap["MEAL"] = countMeal
        selectedQuantityMap["DRINK"] = countDrink

        return selectedQuantityMap
    }

    fun getQuantityTypeProduct(typeProduct: String) : Int {
        var total = 0
        for (product in selectedProductMap) {
            if (product.key.type == typeProduct) {
                total += product.value
            }
         }
        return total
    }

    fun getListOfTypeProduct() : List<String> {
        return listOf("PIZZA", "PASTA", "MEAL", "DRINK")
    }

    fun safeDeleteOrderMap() {
        if (!selectedProductMap.isEmpty() || getQuantityProductTotal() > 0) {
            for (pizza in selectedProductMap) {
                removeProductToList(pizza.key)
            }
            selectedProductMap.clear()
        }
    }



}