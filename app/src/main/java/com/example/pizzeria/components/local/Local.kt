package com.example.pizzeria.components.local

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzeria.R
import com.example.pizzeria.classes.Routes
import com.example.pizzeria.classes.data.LocalInfo
import com.example.pizzeria.classes.viewmodels.LocalViewModel
import com.example.pizzeria.components.MyRatingBar
import com.example.pizzeria.ui.theme.Palette_1_11
import com.example.pizzeria.ui.theme.Palette_1_5
import com.example.pizzeria.ui.theme.tostadito


@Composable
fun MyLocal(localInfo: LocalInfo ,navController: NavController, localViewModel: LocalViewModel) {
    var rating by remember { mutableFloatStateOf(0f) }
    Card(
        colors = CardDefaults.cardColors(tostadito),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.clickable {
            localViewModel.selectLocal(localInfo)
            navController.navigate(Routes.Local.route)
        }

    ) {
        Image(
            painter = painterResource(id = localInfo.image),
            contentDescription = localInfo.address,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .size(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = localInfo.address,
            fontSize = 20.sp,
            color = Palette_1_11,
            modifier = Modifier.padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        MyRatingBar(rating) { rating = it }
    }
}

