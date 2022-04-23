package com.shoshin.rickandmorty.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shoshin.rickandmorty.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}