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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.data.getProductsFromFirestore
import com.example.pizzeria.classes.data.getUserFromFirestore
import com.example.pizzeria.classes.viewmodels.ProductViewModel
import com.example.pizzeria.classes.viewmodels.UserViewModel
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_4
import com.example.pizzeria.ui.theme.tostadito
import kotlinx.coroutines.delay
@Composable
fun SplashScreen(navController: NavController, userViewModel: UserViewModel, productViewModel: ProductViewModel) {
    var splashText by remember { mutableStateOf("Loading packages...")}
    LaunchedEffect(true) {
        userViewModel.auth.signOut()
        delay(1500)
        getProductsFromFirestore { productViewModel.productList = it }
        splashText = "Loading products..."
        delay(1500)
        getUserFromFirestore { userViewModel.userList = it }
        splashText = "Loading users..."
        delay(1500)
        splashText = "Getting everything ready"
        navController.navigate(Routes.MainMenu.route)

    }
    AnimatedSplash(splashText)
    
}

@Composable
fun AnimatedSplash(splashText: String) {
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

    Column(
        modifier = Modifier.fillMaxSize().background(tostadito),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.pizzalogo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(160.dp)
                .graphicsLayer(rotationZ = rotationState)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp).background(
                Palette_1_11),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = splashText,
                color = Color.White
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "Created by Rubén",
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Palette_1_11
        )
    }
}
