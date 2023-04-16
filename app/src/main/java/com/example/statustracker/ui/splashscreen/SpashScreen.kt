package com.example.statustracker.ui.splashscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.statustracker.R
import com.example.statustracker.router.Screen
import kotlinx.coroutines.delay

//@Preview(showBackground = true)
//@Composable
//fun SplashScreenPreview() {
//    SplashScreen()
//}

@Composable
fun SplashScreen(navController: NavHostController) {

    val startAnimation =  remember{ mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(
        targetValue = if(startAnimation.value) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true){
        startAnimation.value=true
        delay(3000)
        navController.popBackStack()
        navController.navigate(Screen.Home.route)

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.splashbackground),
            contentDescription = "Splash Image",
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alphaAnim.value),
            contentScale = ContentScale.Crop
        )
        Text(
            text = "\"Find a place where you can easily access your goals, plan ahead, and track your progress\"",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.Serif,
            modifier = Modifier
                .padding(
                    horizontal = 20.dp,
                    vertical = 10.dp
                )
                .alpha(alphaAnim.value),
            lineHeight = 20.sp
        )
    }
}