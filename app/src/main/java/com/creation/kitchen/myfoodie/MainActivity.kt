package com.creation.kitchen.myfoodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.creation.kitchen.myfoodie.ui.theme.MyFoodieTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFoodieTheme {
                MyFoodieApp()
            }
        }
    }
}
