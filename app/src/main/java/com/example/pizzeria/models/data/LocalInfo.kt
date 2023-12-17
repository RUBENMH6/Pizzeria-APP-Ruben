package com.example.pizzeria.models.data

import com.example.pizzeria.R

data class LocalInfo(val image: Int, val address: String)

fun getPizzaLocal() : List<LocalInfo> {
    return listOf(
        LocalInfo(R.drawable.caballeros, "C/ Caballeros 55, Castellón"),
        LocalInfo(R.drawable.caballeros, "C/ San Luís 83, Almazora"),
        LocalInfo(R.drawable.caballeros, "Centro Comercial Salera, Castellón"),
        LocalInfo(R.drawable.caballeros, "Avda. Ferrandis Salvador, 136, Benicàssim")
    )
}