package com.gauscan.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.gauscan.app.presentation.screens.auth.AuthScreen
import com.gauscan.app.presentation.screens.encyclopedia.EncyclopediaScreen
import com.gauscan.app.presentation.screens.history.HistoryScreen
import com.gauscan.app.presentation.screens.home.HomeScreen
import com.gauscan.app.presentation.screens.onboarding.OnboardingScreen
import com.gauscan.app.presentation.screens.profile.ProfileScreen
import com.gauscan.app.presentation.screens.result.ResultScreen
import com.gauscan.app.presentation.screens.scan.ScanScreen
import com.gauscan.app.presentation.screens.splash.SplashScreen
import com.gauscan.app.presentation.screens.tools.BreedCompareScreen
import com.gauscan.app.presentation.screens.tools.FeedCalculatorScreen
import com.gauscan.app.presentation.screens.tools.HealthDiaryScreen
import com.gauscan.app.presentation.screens.tools.MilkTrackerScreen

// ── All screen routes ─────────────────────────────────────────
sealed class Screen(val route: String) {
    object Splash      : Screen("splash")
    object Onboarding  : Screen("onboarding")
    object Auth        : Screen("auth")
    object Home        : Screen("home")
    object Scan        : Screen("scan")
    object History     : Screen("history")
    object Encyclopedia: Screen("encyclopedia")
    object Profile     : Screen("profile")

    // Result with scanId param
    object Result : Screen("result/{scanId}") {
        fun createRoute(scanId: String) = "result/$scanId"
    }

    // New tool screens
    object FeedCalculator : Screen("feed_calculator")
    object MilkTracker    : Screen("milk_tracker")
    object HealthDiary    : Screen("health_diary")
    object BreedCompare   : Screen("breed_compare")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val isLoggedIn = FirebaseAuth.getInstance().currentUser != null

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // ── Core screens ──────────────────────────────────────
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController, isLoggedIn = isLoggedIn)
        }
        composable(Screen.Onboarding.route) {
            OnboardingScreen(navController = navController)
        }
        composable(Screen.Auth.route) {
            AuthScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Scan.route) {
            ScanScreen(navController = navController)
        }
        composable(Screen.Result.route) { backStackEntry ->
            val scanId = backStackEntry.arguments?.getString("scanId") ?: ""
            ResultScreen(navController = navController, scanId = scanId)
        }
        composable(Screen.History.route) {
            HistoryScreen(navController = navController)
        }
        composable(Screen.Encyclopedia.route) {
            EncyclopediaScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        // ── Tool screens ──────────────────────────────────────
        composable(Screen.FeedCalculator.route) {
            FeedCalculatorScreen(navController = navController)
        }
        composable(Screen.MilkTracker.route) {
            MilkTrackerScreen(navController = navController)
        }
        composable(Screen.HealthDiary.route) {
            HealthDiaryScreen(navController = navController)
        }
        composable(Screen.BreedCompare.route) {
            BreedCompareScreen(navController = navController)
        }
    }
}