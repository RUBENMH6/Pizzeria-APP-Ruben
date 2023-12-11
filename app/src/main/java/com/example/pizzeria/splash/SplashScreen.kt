package com.example.pizzeria.splash

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.data.getProductsFromFirestore
import com.example.pizzeria.classes.data.getUserFromFirestore
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import kotlinx.coroutines.delay
@Composable
fun SplashScreen(navController: NavController, userViewModel: UserViewModel, productViewModel: ProductViewModel) {
    LaunchedEffect(true) {
        delay(3000)
        userViewModel.auth.signOut()
        getProductsFromFirestore { productViewModel.productList = it }
        getUserFromFirestore { userViewModel.userList = it }
        navController.navigate(Routes.MainMenu.route)
    }
    AnimatedSplash()
    
}

@Composable
fun AnimatedSplash() {
    val rotationState by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 45f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1500
                0f at 0 with LinearOutSlowInEasing
                180f at 750 with FastOutLinearInEasing
                360f at 1500 with LinearOutSlowInEasing},
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.pizzalogo),
            contentDescription = "Logo",
            modifier = Modifier
                .graphicsLayer(rotationZ = rotationState)
        )
    }
}
