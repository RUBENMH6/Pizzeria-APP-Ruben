package com.example.pizzeria.components.scaffold

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.data.getNavDrawInfo
import com.example.pizzeria.models.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.FontCWGSans
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_7
import com.example.pizzeria.ui.theme.scaffold
import com.example.pizzeria.ui.theme.tostadito
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyModalDrawerSheet(scope: CoroutineScope, drawerState: DrawerState, navController: NavController, currentRoute: String?, userViewModel: UserViewModel, configuration: Configuration, context: Context) {
    val items = getNavDrawInfo(context)
    ModalDrawerSheet(
        drawerContainerColor = tostadito,
        drawerTonalElevation = 2.dp,
        modifier = Modifier
            //Ajustar el ModalDrawer para cada tipo de orientación
            .fillMaxWidth(if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) 0.7f else 0.25f)
            .padding(end = if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) 0.dp else 70.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                Row(
                    modifier = Modifier.weight(0.3f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Spacer(modifier = Modifier.height(50.dp))
                        Image(
                            painter = painterResource(R.drawable.pizzalogo),
                            contentDescription = "Logo",
                            modifier = Modifier.size(200.dp)
                        )
                    }
                    
                }
            }
            Row(
                modifier = Modifier.weight(if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) 0.7f else 1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    items.forEach { item ->
                        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        text =
                                        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                                            if (item.name == "Login") {
                                                if (userViewModel.auth.currentUser == null) context.getString(R.string.log_in) else context.getString(R.string.log_out)
                                            } else {
                                                item.name
                                            }
                                        } else "",
                                        fontFamily = FontCWGSans,
                                        color = if (item.url == currentRoute) Color.White else Palette_1_11

                                    )
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(item.icon),
                                        contentDescription = item.name,
                                        modifier = Modifier.size(30.dp),
                                        tint = if (item.url == currentRoute) Color.White else Palette_1_11
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                },
                                selected = item.url == currentRoute,
                                onClick = {
                                    if (item.name == "Login") {
                                        if (userViewModel.auth.currentUser != null) {
                                            userViewModel.auth.signOut()
                                        }
                                    }
                                    scope.launch { drawerState.apply { drawerState.close() } }
                                    navController.navigate(item.url)
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    unselectedContainerColor = Color.Transparent,
                                    selectedTextColor = Color.White,
                                    selectedIconColor = Color.White,
                                    selectedContainerColor = Palette_1_11
                                )

                            )
                        } else {
                            if (item.name != "Menu") {
                                NavigationDrawerItem(
                                    label = {
                                        Text(text = "")
                                    },
                                    icon = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(item.icon),
                                                contentDescription = item.name,
                                                modifier = Modifier.size(30.dp),
                                                tint = scaffold
                                            )
                                        }
                                    },
                                    selected = item.url == currentRoute,
                                    onClick = {
                                        if (item.name == "Login") {
                                            if (userViewModel.auth.currentUser != null) {
                                                userViewModel.auth.signOut()
                                            }
                                        }
                                        scope.launch { drawerState.apply { drawerState.close() } }
                                        navController.navigate(item.url)
                                    },
                                    colors = NavigationDrawerItemDefaults.colors(
                                        unselectedContainerColor = Color.Transparent,
                                        selectedTextColor = Color.White,
                                        selectedIconColor = Color.White,
                                        selectedContainerColor = Palette_1_7
                                    )
                                )
                            }
                        }
                    }
                }

            }

        }
    }
}