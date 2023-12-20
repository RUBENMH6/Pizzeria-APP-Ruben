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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.pizzeria.models.viewmodels.UserViewModel
import com.example.pizzeria.screens.MainMenu
import com.example.pizzeria.screens.log.CreateUser
import com.example.pizzeria.screens.log.Login
import com.example.pizzeria.screens.log.Profile
import com.example.pizzeria.screens.order.OrderPizza
import com.example.pizzeria.screens.order.OrderProcess
import com.example.pizzeria.screens.products.DrinkMenu
import com.example.pizzeria.screens.products.MealMenu
import com.example.pizzeria.screens.products.PastaMenu
import com.example.pizzeria.screens.products.PizzaMenu
import com.example.pizzeria.splash.SplashOrderConfirmed
import com.example.pizzeria.splash.SplashScreen
import com.example.pizzeria.ui.theme.*

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
                        if (currentRoute != Routes.SplashScreen.route && currentRoute != Routes.SplashScreenOrderConfirmed.route){
                            BadgedBox(
                                badge = {
                                    Text(
                                        text = if (productViewModel.getQuantityProductTotal() != 0) productViewModel.getQuantityProductTotal()
                                            .toString() else "",
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .size(18.dp)
                                            .offset((-25).dp, (-30).dp).background(if (productViewModel.getQuantityProductTotal() != 0)Palette_1_11 else Color.Transparent, CircleShape)

                                    )
                                }) {
                                if (currentRoute != Routes.OrderProduct.route) {
                                    IconButton(
                                        onClick = {
                                            if (productViewModel.selectedProductList.isEmpty() || productViewModel.getQuantityProductTotal() == 0) {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.order_empty),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } else {
                                                navController.navigate(Routes.OrderProduct.route)
                                            }
                                        },
                                        modifier = Modifier
                                            .size(20.dp)
                                            .background(Palette_1_8, RoundedCornerShape(16.dp))
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.baseline_shopping_cart_24),
                                            contentDescription = "Shopping Product",
                                            tint = Color.White
                                        )
                                    }
                                } else {

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
                                startDestination = Routes.SplashScreen.route
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
                                    Login(
                                        context,
                                        navController,
                                        userViewModel,
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
                                    CreateUser(
                                        context,
                                        navController,
                                        userViewModel,
                                        dialogViewModel
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
                                    PizzaMenu(navController, productViewModel, dialogViewModel, configuration)
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
                                    PastaMenu(navController, productViewModel, dialogViewModel, configuration)
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
                                    DrinkMenu(navController, productViewModel, dialogViewModel, configuration)
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
                                    MealMenu(navController, productViewModel, dialogViewModel, configuration)
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
                                    OrderPizza(navController, productViewModel, dialogViewModel, configuration)
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
                                    OrderProcess(navController, productViewModel, dialogViewModel, userViewModel)
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
                                    PizzaLocal(navController, localViewModel, dialogViewModel)
                                }

                            }
                        }

                    }
                }
            }
        }
    }
}







