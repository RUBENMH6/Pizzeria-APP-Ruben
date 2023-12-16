package com.example.pizzeria.components.scaffold

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.viewmodels.DialogViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.Palette_1_1
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_8
import com.example.pizzeria.ui.theme.tostadito

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDownMenuLog(expanded: Boolean, onExpandedChange: (Boolean) -> Unit, navController: NavController, currentRoute: String, dialogViewModel: DialogViewModel,  userViewModel: UserViewModel, context: Context) {
    val items = listOf("Profile", "Log In", "Log Out")

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            onExpandedChange(false)
        },
        modifier = Modifier
            .background(tostadito)
            .width(160.dp),
        properties = PopupProperties(dismissOnBackPress = true)
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        painter = painterResource(
                            when(item) {
                                "Log In" -> R.drawable.baseline_login_24
                                "Log Out" -> R.drawable.baseline_logout_24
                                "Profile" -> R.drawable.baseline_person_24
                                else -> R.drawable.baseline_person_24
                            }
                        ),
                        contentDescription = item,
                        tint = when(item) {
                            "Log In" -> if (currentRoute == Routes.Login.route) Palette_1_1 else Palette_1_11
                            "Profile" -> if (currentRoute == Routes.Profile.route) Palette_1_1 else Palette_1_11
                            else -> Palette_1_11
                        }
                    )
                },
                text = {
                    Text(
                        text = item,
                        color = when(item) {
                            "Log In" -> if (currentRoute == Routes.Login.route) Palette_1_1 else Palette_1_11
                            "Profile" -> if (currentRoute == Routes.Profile.route) Palette_1_1 else Palette_1_11
                            else -> Palette_1_11
                        }

                    )
                },
                onClick = {
                    when (item) {
                        "Log In" -> navController.navigate(Routes.Login.route)
                        "Profile" ->  {
                            if (userViewModel.authState.value == UserViewModel.AuthState.UNAUTHENTICATED) {
                                if (currentRoute != Routes.Login.route) {
                                    dialogViewModel.dialogLoginToAccessProfile.value = true
                                } else {
                                    Toast.makeText(context, "Please log in first", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                navController.navigate(Routes.Profile.route)
                            }
                        }
                        else -> {
                            userViewModel.auth.signOut()
                            navController.navigate(Routes.MainMenu.route)
                        }
                    }
                    onExpandedChange(false)
                },
                modifier = Modifier
                    .height(60.dp)
                    .background(
                        when (item) {
                            "Log In" -> if (currentRoute != Routes.Login.route) Color.Transparent else Palette_1_8
                            "Profile" -> if (currentRoute != Routes.Profile.route) Color.Transparent else Palette_1_8
                            else -> Color.Transparent
                        }
                    ),
                contentPadding = PaddingValues(start = 20.dp)
            )
            if (index < items.size -1) {
                Divider(color = Palette_1_11)
            }

        }
    }


}