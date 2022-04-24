package com.shoshin.rickandmorty.di

import android.content.Context
import com.shoshin.rickandmorty.common.images.PicassoImageLoader
import com.shoshin.rickandmorty.common.images.interfaces.ImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class ImageLoaderModule {

    @Provides
    fun providePicasso(@ApplicationContext context: Context): Picasso =
        Picasso.Builder(context).build()

    @Provides
    fun provideImageLoader(picasso: Picasso): ImageLoader = PicassoImageLoader(picasso)
}