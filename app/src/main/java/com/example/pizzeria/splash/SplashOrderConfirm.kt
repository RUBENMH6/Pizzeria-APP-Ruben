package com.example.pizzeria.splash

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.models.Routes
import com.example.pizzeria.models.viewmodels.ProductViewModel
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.tostadito
import kotlinx.coroutines.delay

@Composable
fun SplashOrderConfirmed(navController: NavController, productViewModel: ProductViewModel, context: Context) {
    LaunchedEffect(true) {
        delay(3000)
        productViewModel.safeDeleteOrderMap()
        navController.navigate(Routes.MainMenu.route)
        Toast.makeText(
            context,
            context.getString(R.string.order_successfully),
            Toast.LENGTH_LONG
        ).show()
    }
    AnimatedSplashOrderConfirmed(context)
}

@Composable
fun AnimatedSplashOrderConfirmed(context: Context) {
    val translationState  by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 45f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                -1f at 0 with LinearEasing
                1f at 2000 with LinearEasing
            },
            repeatMode = RepeatMode.Restart
        ), label = ""
    )


    Column(
        modifier = Modifier.fillMaxSize().background(tostadito),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.reparto),
            contentDescription = "Moto reparto",
            modifier = Modifier
                .scale(-1f, 1f)
                .graphicsLayer(translationX = translationState * -500)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth().height(40.dp).background(Palette_1_11),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = context.getString(R.string.splash_ordering),
                color = Color.White
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(bottom = 10.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = context.getString(R.string.splash_created_by),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            color = Palette_1_11
        )
    }
}