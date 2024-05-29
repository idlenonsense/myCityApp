package com.idlenonsense.krasnodar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.idlenonsense.krasnodar.screens.KrasnodarApp
import com.idlenonsense.krasnodar.ui.theme.KrasnodarTheme

class MainActivity : ComponentActivity() {
    @ExperimentalMaterial3WindowSizeClassApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KrasnodarTheme {
                val layoutDirection = LocalLayoutDirection.current
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateEndPadding(layoutDirection)
                        ),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(activity = this)
                    KrasnodarApp(windowSize.widthSizeClass)
                }
            }
        }
    }
}