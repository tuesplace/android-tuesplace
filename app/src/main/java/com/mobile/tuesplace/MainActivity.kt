package com.mobile.tuesplace

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mobile.tuesplace.ui.navigation.LOGIN_SCREEN
import com.mobile.tuesplace.ui.navigation.NavHost
import com.mobile.tuesplace.ui.theme.TuesplaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            true
        }
        Handler(Looper.getMainLooper()).postDelayed({
            splashScreen.setKeepOnScreenCondition {
                false
            }
        }, 1000)
        setContent {
            TuesplaceTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                var visibility: Boolean
                (currentDestination != navController.findDestination(LOGIN_SCREEN)).also { visibility = it }
                Scaffold(
                    topBar = { if (visibility) { topBar() } },
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

@Composable
fun topBar() {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        backgroundColor = colorResource(id = R.color.dark_blue),
        contentPadding = PaddingValues(start = 30.dp)
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
        ) {
            val (text, menu) = createRefs()

            Text(
                text = stringResource(id = R.string.app_name),
                color = Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.constrainAs(text) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
            )

            Icon(
                painter = painterResource(id = R.drawable.menu),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(20.dp)
                    .clickable {}
                    .constrainAs(menu) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TuesplaceTheme {
    }
}