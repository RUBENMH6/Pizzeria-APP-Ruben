package com.example.pizzeria

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pizzeria.components.local.PizzaLocal
import com.example.pizzeria.components.scaffold.MyBottomAppBar
import com.example.pizzeria.components.scaffold.MyFABtoBack
import com.example.pizzeria.components.scaffold.MyFABtoBuy
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
                    bottomBar = {
                        if (configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                            if (currentRoute != Routes.SplashScreen.route && currentRoute != Routes.SplashScreenOrderConfirmed.route) {
                                MyBottomAppBar(
                                    currentRoute,
                                    navController,
                                    productViewModel,
                                    dialogViewModel,
                                    context,
                                    userViewModel
                                )
                            }
                        }


                    },
                    floatingActionButton = {
                        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            if (currentRoute != Routes.SplashScreen.route && currentRoute != Routes.SplashScreenOrderConfirmed.route) {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.weight(0.5f).fillMaxWidth().padding(start = 30.dp),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        MyFABtoBack(
                                            navController,
                                            currentRoute,
                                            productViewModel,
                                            userViewModel
                                        )
                                    }
                                    Column(
                                        modifier = Modifier.weight(0.5f).fillMaxWidth(),
                                        horizontalAlignment = Alignment.End
                                    ) {
                                        MyFABtoBuy(
                                            navController,
                                            currentRoute,
                                            productViewModel,
                                            userViewModel,
                                            context, dialogViewModel
                                        )
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
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
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
                                    SplashScreen(navController, userViewModel, productViewModel)
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
                                        configuration
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







