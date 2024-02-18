package com.example.pizzeria.ui.views.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.data.UserInfo
import com.example.pizzeria.models.data.setUserToFirestore
import com.example.pizzeria.models.viewmodels.DialogViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


class UserViewModel : ViewModel() {
    val auth: FirebaseAuth = Firebase.auth
    val _loading = MutableLiveData(false)
    var userList = mutableStateListOf<UserInfo>()

    private val _authState = mutableStateOf(AuthState.UNAUTHENTICATED)
    val authState: State<AuthState> = _authState
    init {
        // Listen for changes in authentication state
        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                _authState.value = AuthState.AUTHENTICATED
            } else {
                _authState.value = AuthState.UNAUTHENTICATED
            }
        }
    }
    enum class AuthState {
        AUTHENTICATED,
        UNAUTHENTICATED
    }


    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        navController: NavController,
        dialogViewModel: DialogViewModel
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userId = user?.uid
                    Log.d("Firebase", "Sign in. User ID: $userId")
                    if (dialogViewModel.commingToLoginNeededOrderPizza.value) {
                        navController.navigate(Routes.OrderProcess.route)
                    } else {
                        navController.navigate(Routes.MainMenu.route)
                    }

                } else {
                    Toast.makeText(context, "${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
        } catch (ex: IllegalArgumentException) {
            Toast.makeText(context, "${ex.message}", Toast.LENGTH_LONG).show()
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        navController: NavController,
        name: String
    ) {
        try {
            if (_loading.value == false) {
                _loading.value = true
                auth.createUserWithEmailAndPassword(email, password)
                    //Se ha compleado el proceso de creación de usuario correctamente
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            user?.updateProfile(profileUpdates)
                                //Se ha completado el proceso de modificar el usuario
                                ?.addOnCompleteListener { updateProfileTask ->
                                    if (updateProfileTask.isSuccessful) {
                                        // El displayName se ha establecido correctamente
                                        val userInfo = UserInfo(user.uid, name, email)
                                        setUserToFirestore(userInfo)
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.user_create_successfully),
                                            Toast.LENGTH_LONG
                                        ).show()
                                        navController.navigate(Routes.MainMenu.route)
                                    } else {
                                        // Fallo al establecer el displayName
                                        Toast.makeText(
                                            context,
                                            context.getString(R.string.display_name_error),
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    _loading.value = false
                                }
                        } else {
                            //Ha habido un error al crear el usuario
                            Toast.makeText(context, "${task.exception?.message}", Toast.LENGTH_LONG)
                                .show()
                        }
                        _loading.value = false

                    }
            }
        } catch (ex: IllegalArgumentException) {
            //Fallo al introducir los datos
            Toast.makeText(context, "${ex.message}", Toast.LENGTH_LONG).show()
        }

    }
}
