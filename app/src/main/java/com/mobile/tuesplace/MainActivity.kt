package com.mobile.tuesplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.mobile.tuesplace.ui.navigation.NavHost
import com.mobile.tuesplace.ui.theme.TuesplaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TuesplaceTheme {
                val navController = rememberNavController()
                Scaffold(
                    content = { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            NavHost(navController)
                        }
                    }
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TuesplaceTheme {
    }
}