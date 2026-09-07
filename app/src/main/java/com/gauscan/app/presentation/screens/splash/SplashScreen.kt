package com.gauscan.app.presentation.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gauscan.app.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    isLoggedIn: Boolean
) {
    // Animations
    val logoScale = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val subtitleAlpha = remember { Animatable(0f) }
    val taglineAlpha = remember { Animatable(0f) }
    val dotScale1 = remember { Animatable(0f) }
    val dotScale2 = remember { Animatable(0f) }
    val dotScale3 = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Logo bounce in
        logoScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        // Title fade in
        titleAlpha.animateTo(1f, animationSpec = tween(500))
        delay(200)
        subtitleAlpha.animateTo(1f, animationSpec = tween(400))
        delay(300)
        taglineAlpha.animateTo(1f, animationSpec = tween(400))

        // Loading dots
        delay(400)
        dotScale1.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy))
        delay(100)
        dotScale2.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy))
        delay(100)
        dotScale3.animateTo(1f, animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy))

        delay(800)
        navController.navigate(
            if (isLoggedIn) Screen.Home.route else Screen.Auth.route
        ) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B5E20),
                        Color(0xFF2E7D32),
                        Color(0xFF388E3C)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative circles (background)
        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.TopEnd)
                .offset(x = 80.dp, y = (-60).dp)
                .alpha(0.08f)
                .background(Color.White, shape = androidx.compose.foundation.shape.CircleShape)
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-60).dp, y = 60.dp)
                .alpha(0.06f)
                .background(Color.White, shape = androidx.compose.foundation.shape.CircleShape)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo emoji with scale animation
            Text(
                text = "🐄",
                fontSize = 96.sp,
                modifier = Modifier.scale(logoScale.value)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // App Name
            Text(
                text = "GauScan",
                fontSize = 42.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                letterSpacing = 2.sp,
                modifier = Modifier.alpha(titleAlpha.value)
            )

            // Subtitle
            Text(
                text = "BREED RECOGNITION",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.7f),
                letterSpacing = 4.sp,
                modifier = Modifier
                    .alpha(subtitleAlpha.value)
                    .padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Tagline
            Text(
                text = "Scan any Indian cattle or buffalo\nand identify the breed instantly",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier
                    .alpha(taglineAlpha.value)
                    .padding(horizontal = 40.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading dots
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                listOf(dotScale1, dotScale2, dotScale3).forEachIndexed { i, dot ->
                    Box(
                        modifier = Modifier
                            .scale(dot.value)
                            .size(8.dp)
                            .background(
                                Color.White.copy(alpha = if (i == 1) 1f else 0.5f),
                                shape = androidx.compose.foundation.shape.CircleShape
                            )
                    )
                }
            }
        }

        // Bottom branding
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .alpha(taglineAlpha.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Powered by",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.4f)
            )
            Text(
                text = "Gemini AI",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White.copy(alpha = 0.6f),
                letterSpacing = 1.sp
            )
        }
    }
}