package com.example.appdummy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.appdummy.screens.PantallaBienvenida
import com.example.appdummy.ui.theme.AppDummyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppDummyTheme {
                PantallaBienvenida(
                    onEntrar = {}
                )
            }
        }
    }
}