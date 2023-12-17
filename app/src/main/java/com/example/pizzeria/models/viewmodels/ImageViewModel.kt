package com.example.pizzeria.models.viewmodels

import android.graphics.ImageDecoder
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class ImageViewModel : ViewModel() {
    var imageList = mutableStateListOf<ImageDecoder.ImageInfo>()
}