package com.example.pizzeria.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pizzeria.R

@Composable
fun MyRatingBar(ratingValue: Float, onRatingChanged: (Float) -> Unit) {
    var estadoRating = 0f
    var maxRating = 5
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            for (rating in 1..maxRating) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "star",
                    modifier = Modifier
                        .size(30.dp).weight(0.156f)
                        .clickable {
                            estadoRating = rating.toFloat()
                            onRatingChanged(rating.toFloat())
                        },
                    tint =
                    if (rating <= estadoRating || rating <= ratingValue) {
                        Color.Black
                    } else {
                        Color.White
                    }
                )
            }
            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

