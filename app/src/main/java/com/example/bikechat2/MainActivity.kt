package com.example.bikechat2





import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bikechat2.ui.screens.MainScreen
import com.example.bikechat2.ui.theme.BikeChat2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BikeChat2Theme {
                MainScreen()
            }
        }
    }
}
