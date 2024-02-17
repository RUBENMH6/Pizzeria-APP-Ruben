package com.example.pizzeria

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.components.local.PizzaLocal
import com.example.pizzeria.components.scaffold.MyModalDrawerSheet
import com.example.pizzeria.components.scaffold.MyTopAppBar
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.viewmodels.DialogViewModel
import com.example.pizzeria.models.viewmodels.LocalViewModel
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.screens.MainMenu
import com.example.pizzeria.screens.log.Profile
import com.example.pizzeria.screens.order.OrderProcess
import com.example.pizzeria.screens.order.OrderProduct
import com.example.pizzeria.screens.products.DrinkMenu
import com.example.pizzeria.screens.products.MealMenu
import com.example.pizzeria.screens.products.PastaMenu
import com.example.pizzeria.screens.products.PizzaMenu
import com.example.pizzeria.splash.SplashOrderConfirmed
import com.example.pizzeria.splash.SplashScreen
import com.example.pizzeria.ui.theme.*
import com.example.pizzeria.ui.views.auth.LoginView
import com.example.pizzeria.ui.views.auth.RegisterView
import com.example.pizzeria.ui.views.auth.UserViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PizzeriaTheme {
                val userViewModel: UserViewModel = viewModel()
                val navController = rememberNavController()
                val productViewModel: ProductViewModel = viewModel()
                val dialogViewModel: DialogViewModel = viewModel()
                val localViewModel: LocalViewModel = viewModel()
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.destination?.route
                val context = LocalContext.current
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                val configuration = LocalConfiguration.current




                Scaffold(
                    topBar = {
                        if (currentRoute != null) {
                            if (currentRoute != Routes.SplashScreen.route && currentRoute != Routes.SplashScreenOrderConfirmed.route) {
                                MyTopAppBar(
                                    currentRoute,
                                    navController,
                                    productViewModel,
                                    scope,
                                    drawerState,
                                    userViewModel,
                                    dialogViewModel,
                                    context
                                )
                            }

                        }
                    },
                    floatingActionButton = {
                        if (currentRoute != Routes.SplashScreen.route && currentRoute != Routes.SplashScreenOrderConfirmed.route) {

                                when (currentRoute) {
                                    Routes.PizzaMenu.route, Routes.PastaMenu.route, Routes.MealMenu.route, Routes.DrinkMenu.route -> {
                                        Column(
                                            modifier = Modifier.height(100.dp),
                                        ) {

                                        }
                                    }

                                    Routes.OrderProduct.route -> {
                                        Column(
                                            modifier = Modifier.height(100.dp),
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    if (!productViewModel.selectedProductMap.isEmpty() || productViewModel.getQuantityProductTotal() > 0) {
                                                        navController.navigate(Routes.OrderProcess.route)
                                                    } else {
                                                        Toast.makeText(
                                                            context,
                                                            "You have not selected any product",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                },
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .background(
                                                        Palette_1_8,
                                                        RoundedCornerShape(16.dp)
                                                    )
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.baseline_attach_money_24),
                                                    contentDescription = "Order process",
                                                    tint = Color.White
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(30.dp))
                                            IconButton(
                                                onClick = {
                                                    if (!productViewModel.selectedProductMap.isEmpty() || productViewModel.getQuantityProductTotal() > 0) {
                                                        dialogViewModel.dialogRemoveOrderPizza.value =
                                                            true
                                                    } else {
                                                        Toast.makeText(
                                                            context,
                                                            "You have not selected any product",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }

                                                },
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .background(
                                                        Palette_1_8,
                                                        RoundedCornerShape(16.dp)
                                                    )
                                            ) {
                                                Icon(
                                                    painter = painterResource(R.drawable.baseline_sync_24),
                                                    contentDescription = "Clear Order",
                                                    tint = Color.White
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }

                                    }

                            }

                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = it.calculateTopPadding()
                            ),

                        ) {
                        ModalNavigationDrawer(
                            drawerState = drawerState,
                            drawerContent = {
                                MyModalDrawerSheet(
                                    scope,
                                    drawerState,
                                    navController,
                                    currentRoute,
                                    userViewModel,
                                    configuration,
                                    context)
                            }
                        ) {

                            NavHost(
                                navController = navController,
                                startDestination = Routes.Login.route
                            ) {
                                composable(
                                    route = Routes.SplashScreen.route,
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    SplashScreen(navController, userViewModel, productViewModel, context)
                                }
                                composable(
                                    route = Routes.SplashScreenOrderConfirmed.route,
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    SplashOrderConfirmed(navController, productViewModel, context)
                                }

                                composable(
                                    route = Routes.Login.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    LoginView(
                                        navController,
                                        userViewModel,
                                        context,
                                        dialogViewModel
                                    )
                                }
                                composable(
                                    route = Routes.CreateUser.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    RegisterView(
                                        navController,
                                        context
                                    )
                                }
                                composable(
                                    route = Routes.Profile.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    Profile()
                                }
                                composable(
                                    route = Routes.MainMenu.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    MainMenu(
                                        navController,
                                        dialogViewModel,
                                        configuration,
                                        context
                                    )
                                }
                                composable(
                                    route = Routes.PizzaMenu.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    PizzaMenu(navController, productViewModel, dialogViewModel, configuration, context)
                                }
                                composable(
                                    route = Routes.PastaMenu.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    PastaMenu(navController, productViewModel, dialogViewModel, configuration, context)
                                }
                                composable(
                                    route = Routes.DrinkMenu.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    DrinkMenu(navController, productViewModel, dialogViewModel, configuration, context)
                                }
                                composable(
                                    route = Routes.MealMenu.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    MealMenu(navController, productViewModel, dialogViewModel, configuration, context)
                                }
                                composable(
                                    route = Routes.OrderProduct.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    OrderProduct(navController, productViewModel, dialogViewModel, configuration, context)
                                }
                                composable(
                                    route = Routes.OrderProcess.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    OrderProcess(navController, productViewModel, dialogViewModel, userViewModel, context)
                                }
                                composable(
                                    route = Routes.Local.route,
                                    exitTransition = {
                                        slideOutOfContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    },
                                    enterTransition = {
                                        slideIntoContainer(
                                            towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                                            animationSpec = tween(700)
                                        )
                                    }
                                ) {
                                    PizzaLocal(navController, localViewModel, dialogViewModel, context)
                                }

                            }
                        }

                    }
                }
            }
        }
    }
}







