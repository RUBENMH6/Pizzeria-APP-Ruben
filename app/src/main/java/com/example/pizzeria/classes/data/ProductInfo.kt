package com.example.pizzeria.classes.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


data class ProductInfo( val name: String, val price: Double, val ingredients: List<String> = listOf(), val type: String)






fun setProductToFirestore(productList: List<ProductInfo>) {

    val products = productList.distinctBy { it.name } // Ensuring that there are no products with the same name.
    val db = Firebase.firestore // Instance of the Firestore database.
    val collectionReference = db.collection("products") // Reference to the "products" collection.



// We iterate through the product list, adding each product to a map.
// Where the 'key' is the column and the 'value' is the content.
    for (product in products) {
        val newProduct = hashMapOf(
            "name" to product.name, //Aquí la key sería 'name' y el valor sería el contenido del nombre
            "price" to product.price,
            "ingredients" to product.ingredients,
            "type" to product.type
        )

        collectionReference.add(newProduct)
            .addOnSuccessListener {
                //Success message
                Log.d("Firestore", "Product added successfully. Document ID:${it.id}")
            }
            .addOnFailureListener {
                //Failure message
                Log.e("Firestore", "Error adding product", it)
            }
    }
}

fun getProductsFromFirestore(onSuccess: (SnapshotStateList<ProductInfo>) -> Unit) {
    val firestore = Firebase.firestore
    val collectionReference = firestore.collection("products")

    collectionReference.get()
        .addOnSuccessListener { result ->
            val productList = mutableStateListOf<ProductInfo>()

            for (document in result) {
                val name = document.getString("name") ?: ""
                val price = document.getDouble("price") ?: 0.0
                val ingredientsList = document.get("ingredients") as? ArrayList<String> ?: ArrayList()
                val type = document.getString("type") ?: ""

                val product = ProductInfo(name, price, ingredientsList, type)
                productList.add(product)
            }
            //Success message
            Log.d("Firestore", "Products obtained successfully")
            onSuccess.invoke(productList)
        }
        .addOnFailureListener { e ->
            //Failure message
            Log.e("Firestore", "Failed to retrieve products", e)
        }
}






