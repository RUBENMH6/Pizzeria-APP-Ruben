package com.example.pizzeria.classes.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

data class UserInfo(val id: String, val name: String, val email: String)

fun getUserFromFirestore(onSuccess: (SnapshotStateList<UserInfo>) -> Unit) {
    val firestore = Firebase.firestore
    val collectionReference = firestore.collection("users")

    collectionReference.get()
        .addOnSuccessListener { result ->
            val userList = mutableStateListOf<UserInfo>()

            for (document in result) {
                val userId = document.getString("userId") ?: ""
                val name = document.getString("display_Name") ?: ""
                val email = document.getString("email") ?: ""

                val user = UserInfo(userId, name, email)
                userList.add(user)
            }
            Log.d("Firestore", "Users obtained successfully")
            onSuccess.invoke(userList)
        }
        .addOnFailureListener { e ->
            //Failure message
            Log.e("Firestore", "Failed to retrieve users", e)
        }
}


fun setUserToFirestore(user: UserInfo) {

    val db = Firebase.firestore // Instance of the Firestore database.
    val collectionReference = db.collection("users") // Reference to the "users" collection.

    val newUser = hashMapOf(
        "userId" to user.id,
        "name" to user.name,
        "email" to user.email
    )

    collectionReference.add(newUser)
        .addOnSuccessListener {
            //Success message
            Log.d("Firestore", "User added successfully. Document ID:${it.id}")
        }
        .addOnFailureListener {
            //Failure message
            Log.e("Firestore", "Error adding user", it)
        }
}
