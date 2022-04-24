package com.shoshin.rickandmorty.common.images.interfaces

import android.widget.ImageView

interface ImageLoader {
    fun load(imageView: ImageView, url: String?)
}