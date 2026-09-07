package com.gauscan.app.presentation.screens.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gauscan.app.data.model.ScanRecord
import com.gauscan.app.presentation.navigation.Screen
import com.gauscan.app.presentation.theme.ConfidenceHigh
import com.gauscan.app.presentation.theme.ConfidenceLow
import com.gauscan.app.presentation.theme.ConfidenceMedium
import kotlinx.coroutines.delay

// ─── Color Palette ─────────────────────────────────────────────────────────────
private val PrimaryGreen = Color(0xFF1B4332)
private val SecondaryGreen = Color(0xFF2D6A4F)
private val AccentGold = Color(0xFFD97706)
private val AccentGoldLight = Color(0xFFFBBF24)
private val SurfaceBg = Color(0xFFFAF9F7)
private val CardWhite = Color(0xFFFFFFFF)
private val TextPrimary = Color(0xFF111827)
private val TextSecondary = Color(0xFF6B7280)
private val TextTertiary = Color(0xFF9CA3AF)
private val BorderLight = Color(0xFFF3F4F6)
private val MintLight = Color(0xFFD1FAE5)
private val AmberLight = Color(0xFFFEF3C7)
private val BlueLight = Color(0xFFDBEAFE)
private val PurpleLight = Color(0xFFEDE9FE)

// ─── Tips ──────────────────────────────────────────────────────────────────────
private val breedTips = listOf(
    "💡 Gir cattle from Gujarat are known for their A2 milk — highly nutritious and easy to digest.",
    "💡 Murrah buffalo produces the highest milk fat content (7–8%) among all buffalo breeds.",
    "💡 Sahiwal is the top dairy breed in South Asia — thrives in hot, humid conditions.",
    "💡 Ongole cattle are exported globally as Nelore breed and are valued for their muscle build.",
    "💡 Bhadawari buffalo milk has the highest fat % (12–14%) of any buffalo breed worldwide.",
    "💡 Kankrej is one of the heaviest Indian zebu breeds, also exported to Brazil as Guzerat.",
    "💡 Hallikar cattle from Karnataka are famous for their speed and were used in bull races.",
    "💡 Red Sindhi cattle are highly disease and tick resistant — ideal for harsh climates.",
    "💡 Tharparkar cattle survive desert conditions and still produce 8–12 liters of milk daily.",
    "💡 Vechur cattle from Kerala are the world's smallest cattle breed — listed in Guinness records."
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showTipsDialog by remember { mutableStateOf(false) }
    var currentTipIndex by remember { mutableStateOf((0..9).random()) }

    // Greeting based on time
    val greeting = remember {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        when {
            hour < 12 -> "Good Morning"
            hour < 17 -> "Good Afternoon"
            else -> "Good Evening"
        }
    }

    // Tips Dialog
    if (showTipsDialog) {
        TipsDialog(
            tip = breedTips[currentTipIndex],
            onDismiss = { showTipsDialog = false },
            onNextTip = { currentTipIndex = (currentTipIndex + 1) % breedTips.size },
            onExplore = {
                navController.navigate(Screen.Encyclopedia.route)
                showTipsDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                greeting = greeting,
                userName = uiState.userName,
                userPhoto = uiState.userPhoto,
                onTipsClick = { showTipsDialog = true },
                onProfileClick = { navController.navigate(Screen.Profile.route) }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, currentRoute = Screen.Home.route)
        },
        floatingActionButton = {
            LargeFloatingActionButton(
                onClick = { navController.navigate(Screen.Scan.route) },
                containerColor = PrimaryGreen,
                contentColor = Color.White,
                shape = RoundedCornerShape(22.dp),
                modifier = Modifier
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(22.dp),
                        ambientColor = PrimaryGreen.copy(alpha = 0.3f),
                        spotColor = PrimaryGreen.copy(alpha = 0.4f)
                    )
            ) {
                Icon(
                    Icons.Rounded.CameraAlt,
                    contentDescription = "Scan",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = SurfaceBg
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // ── Hero Banner ──────────────────────────────────────
            HeroBanner(
                onScanClick = { navController.navigate(Screen.Scan.route) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Quick Stats ──────────────────────────────────────
            QuickStatsSection(
                totalScans = uiState.totalScans,
                cattleScans = uiState.cattleScans,
                buffaloScans = uiState.buffaloScans
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Explore (Encyclopedia & Compare) ─────────────────
            ExploreSection(
                onEncyclopediaClick = { navController.navigate(Screen.Encyclopedia.route) },
                onCompareClick = { navController.navigate(Screen.BreedCompare.route) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Farmer Tools (Feed Calc, Milk Tracker, Health Diary) ──
            ToolsSection(
                onFeedCalcClick = { navController.navigate(Screen.FeedCalculator.route) },
                onMilkTrackerClick = { navController.navigate(Screen.MilkTracker.route) },
                onHealthDiaryClick = { navController.navigate(Screen.HealthDiary.route) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ── Recent Scans ──────────────────────────────────────
            RecentScansSection(
                recentScans = uiState.recentScans,
                onViewAll = { navController.navigate(Screen.History.route) },
                onScanClick = { scan ->
                    navController.navigate(Screen.Result.createRoute(scan.id))
                },
                onFirstScan = { navController.navigate(Screen.Scan.route) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ── Breed of the Day ──────────────────────────────────
            BreedOfTheDayCard(
                breedName = uiState.breedOfTheDay,
                onClick = { navController.navigate(Screen.Encyclopedia.route) }
            )

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

// ─── Top App Bar ───────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    greeting: String,
    userName: String,
    userPhoto: String,
    onTipsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "$greeting 🙏",
                    fontSize = 12.sp,
                    color = TextTertiary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = userName.ifEmpty { "Welcome!" },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        },
        actions = {
            // Notification bell with tip badge
            IconButton(
                onClick = onTipsClick,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF3F4F6))
            ) {
                BadgedBox(
                    badge = {
                        Badge(
                            containerColor = AccentGold,
                            modifier = Modifier.size(8.dp)
                        ) {}
                    }
                ) {
                    Icon(
                        Icons.Outlined.Notifications,
                        contentDescription = "Tips",
                        tint = TextPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Avatar
            Box(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(2.dp, MintLight, CircleShape)
                    .background(PrimaryGreen)
                    .clickable(onClick = onProfileClick),
                contentAlignment = Alignment.Center
            ) {
                if (userPhoto.isNotEmpty()) {
                    AsyncImage(
                        model = userPhoto,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = userName.firstOrNull()?.uppercaseChar()?.toString() ?: "G",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SurfaceBg
        )
    )
}

// ─── Hero Banner ───────────────────────────────────────────────────────────────
@Composable
private fun HeroBanner(onScanClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "hero_pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.04f,
        targetValue = 0.12f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(28.dp))
            .clickable(onClick = onScanClick)
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1B4332),
                        Color(0xFF2D6A4F),
                        Color(0xFF40916C)
                    )
                )
            )
            .padding(24.dp)
    ) {
        // Animated decorative circles
        Box(
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.TopEnd)
                .offset(x = 60.dp, y = (-40).dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = pulseAlpha))
        )
        Box(
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-30).dp, y = 30.dp)
                .clip(CircleShape)
                .background(AccentGoldLight.copy(alpha = 0.05f))
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                // AI badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AccentGold.copy(alpha = 0.2f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            Icons.Rounded.AutoAwesome,
                            contentDescription = null,
                            tint = AccentGoldLight,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "AI Powered",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AccentGoldLight,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Identify Any\nIndian Breed",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Snap a photo and get breed details,\nmilk yield & origin instantly",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.75f),
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // CTA Button
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White,
                    modifier = Modifier
                        .shadow(
                            8.dp,
                            RoundedCornerShape(14.dp),
                            ambientColor = Color.Black.copy(alpha = 0.1f)
                        )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Rounded.CameraAlt,
                            contentDescription = null,
                            tint = PrimaryGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "Start Scanning",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Decorative icon container
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White.copy(alpha = 0.1f))
                    .border(
                        1.dp,
                        Color.White.copy(alpha = 0.15f),
                        RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.CameraAlt,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.85f),
                    modifier = Modifier.size(44.dp)
                )
            }
        }
    }
}

// ─── Quick Stats ───────────────────────────────────────────────────────────────
@Composable
private fun QuickStatsSection(
    totalScans: Int,
    cattleScans: Int,
    buffaloScans: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Rounded.QueryStats,
            value = totalScans.toString(),
            label = "Total Scans",
            tint = PrimaryGreen,
            bgGradient = listOf(Color(0xFFD1FAE5), Color(0xFFECFDF5))
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Rounded.Pets,
            value = cattleScans.toString(),
            label = "Cattle",
            tint = Color(0xFF92400E),
            bgGradient = listOf(Color(0xFFFEF3C7), Color(0xFFFFFBEB))
        )
        StatCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Rounded.Pets,
            value = buffaloScans.toString(),
            label = "Buffalo",
            tint = Color(0xFF1E40AF),
            bgGradient = listOf(Color(0xFFDBEAFE), Color(0xFFEFF6FF))
        )
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    icon: ImageVector,
    value: String,
    label: String,
    tint: Color,
    bgGradient: List<Color>
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.verticalGradient(bgGradient))
                .padding(14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(tint.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = tint
            )
            Text(
                text = label,
                fontSize = 11.sp,
                color = tint.copy(alpha = 0.65f),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

// ─── Explore Section (Encyclopedia + Compare) ─────────────────────────────────
@Composable
private fun ExploreSection(
    onEncyclopediaClick: () -> Unit,
    onCompareClick: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Explore",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ActionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Rounded.MenuBook,
                title = "Encyclopedia",
                subtitle = "100+ breeds",
                bgColor = PurpleLight,
                iconBg = Color(0xFF5B21B6).copy(alpha = 0.12f),
                iconColor = Color(0xFF5B21B6),
                onClick = onEncyclopediaClick
            )
            ActionCard(
                modifier = Modifier.weight(1f),
                icon = Icons.Rounded.CompareArrows,
                title = "Compare",
                subtitle = "Side-by-side",
                bgColor = Color(0xFFFCE7F3),
                iconBg = Color(0xFF9D174D).copy(alpha = 0.12f),
                iconColor = Color(0xFF9D174D),
                onClick = onCompareClick
            )
        }
    }
}

// ─── Farmer Tools Section ─────────────────────────────────────────────────────
@Composable
private fun ToolsSection(
    onFeedCalcClick: () -> Unit,
    onMilkTrackerClick: () -> Unit,
    onHealthDiaryClick: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Farmer Tools",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            ToolCard(
                modifier = Modifier.weight(1f),
                emoji = "🧮",
                title = "Feed\nCalculator",
                bgColor = Color(0xFFECFDF5),
                textColor = Color(0xFF065F46),
                onClick = onFeedCalcClick
            )
            ToolCard(
                modifier = Modifier.weight(1f),
                emoji = "🥛",
                title = "Milk\nTracker",
                bgColor = Color(0xFFEFF6FF),
                textColor = Color(0xFF1E40AF),
                onClick = onMilkTrackerClick
            )
            ToolCard(
                modifier = Modifier.weight(1f),
                emoji = "💉",
                title = "Health\nDiary",
                bgColor = Color(0xFFFFF1F2),
                textColor = Color(0xFF9F1239),
                onClick = onHealthDiaryClick
            )
        }
    }
}

@Composable
private fun ToolCard(
    modifier: Modifier,
    emoji: String,
    title: String,
    bgColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.Start) {
            Text(emoji, fontSize = 26.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                lineHeight = 16.sp
            )
        }
    }
}

// ─── Action Card (reusable for Explore/QuickActions) ──────────────────────────
@Composable
private fun ActionCard(
    modifier: Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    bgColor: Color,
    iconBg: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

// ─── Recent Scans ──────────────────────────────────────────────────────────────
@Composable
private fun RecentScansSection(
    recentScans: List<ScanRecord>,
    onViewAll: () -> Unit,
    onScanClick: (ScanRecord) -> Unit,
    onFirstScan: () -> Unit
) {
    Column {
        if (recentScans.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Recent Scans",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                TextButton(onClick = onViewAll) {
                    Text(
                        "View All",
                        fontSize = 13.sp,
                        color = PrimaryGreen,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Icon(
                        Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        tint = PrimaryGreen,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(recentScans) { scan ->
                    RecentScanCard(
                        scan = scan,
                        onClick = { onScanClick(scan) }
                    )
                }
            }
        } else {
            EmptyStateCard(onScanClick = onFirstScan)
        }
    }
}

@Composable
private fun EmptyStateCard(onScanClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        border = BorderStroke(1.dp, BorderLight)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFF3F4F6), Color(0xFFE5E7EB))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.CameraAlt,
                    contentDescription = null,
                    tint = TextTertiary,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "No scans yet",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Take your first photo to identify a breed",
                fontSize = 13.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onScanClick,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Icon(
                    Icons.Rounded.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Take First Scan", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun RecentScanCard(scan: ScanRecord, onClick: () -> Unit) {
    val confidence = scan.confidence
    val confColor = when {
        confidence >= 0.8f -> ConfidenceHigh
        confidence >= 0.5f -> ConfidenceMedium
        else -> ConfidenceLow
    }

    Card(
        modifier = Modifier
            .width(160.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .background(Color(0xFFF9FAFB)),
                contentAlignment = Alignment.Center
            ) {
                if (scan.imageUrl.isNotEmpty() || scan.localImagePath.isNotEmpty()) {
                    AsyncImage(
                        model = scan.imageUrl.ifEmpty { scan.localImagePath },
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        Icons.Rounded.Pets,
                        contentDescription = null,
                        tint = Color(0xFFD1D5DB),
                        modifier = Modifier.size(40.dp)
                    )
                }

                // Confidence badge overlay
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = confColor.copy(alpha = 0.9f)
                ) {
                    Text(
                        text = "${(confidence * 100).toInt()}%",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = scan.breedName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (scan.category.lowercase().contains("cattle"))
                            AmberLight else BlueLight
                    ) {
                        Text(
                            text = scan.category,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (scan.category.lowercase().contains("cattle"))
                                Color(0xFF92400E) else Color(0xFF1E40AF),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

// ─── Breed of the Day ──────────────────────────────────────────────────────────
@Composable
private fun BreedOfTheDayCard(breedName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF1B4332), Color(0xFF2D6A4F), Color(0xFF40916C))
                    )
                )
                .padding(22.dp)
        ) {
            // Decorative element
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = (-20).dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.06f))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            Icons.Rounded.AutoAwesome,
                            contentDescription = null,
                            tint = AccentGoldLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            "BREED OF THE DAY",
                            fontSize = 10.sp,
                            color = Color(0xFFA7F3D0),
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = breedName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tap to explore characteristics & details",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.6f)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.12f))
                        .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
        }
    }
}

// ─── Tips Dialog ───────────────────────────────────────────────────────────────
@Composable
private fun TipsDialog(
    tip: String,
    onDismiss: () -> Unit,
    onNextTip: () -> Unit,
    onExplore: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = CardWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(MintLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Rounded.Lightbulb,
                                contentDescription = null,
                                tint = PrimaryGreen,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            "Did You Know?",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryGreen
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFF3F4F6))
                    ) {
                        Icon(
                            Icons.Rounded.Close,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = TextSecondary
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 16.dp),
                    color = BorderLight,
                    thickness = 1.dp
                )

                // Tip content
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF0FDF4),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = tip,
                        fontSize = 15.sp,
                        lineHeight = 24.sp,
                        color = Color(0xFF374151),
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onNextTip,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.5.dp, PrimaryGreen),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Icon(
                            Icons.Rounded.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = PrimaryGreen
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Next Tip", color = PrimaryGreen, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Button(
                        onClick = onExplore,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Icon(
                            Icons.Rounded.MenuBook,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Explore", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// ─── Bottom Navigation ─────────────────────────────────────────────────────────
@Composable
fun BottomNavigationBar(navController: NavController, currentRoute: String) {
    NavigationBar(
        containerColor = CardWhite,
        tonalElevation = 0.dp,
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f)
            )
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        val items = listOf(
            Triple(Screen.Home.route, Icons.Rounded.Home, "Home"),
            Triple(Screen.Scan.route, Icons.Rounded.CameraAlt, "Scan"),
            Triple(Screen.History.route, Icons.Rounded.History, "History"),
            Triple(Screen.Encyclopedia.route, Icons.Rounded.MenuBook, "Breeds")
        )
        items.forEach { (route, icon, label) ->
            val isSelected = currentRoute == route
            NavigationBarItem(
                icon = {
                    Icon(
                        icon,
                        contentDescription = label,
                        modifier = Modifier.size(if (isSelected) 24.dp else 22.dp)
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            launchSingleTop = true
                            restoreState = true

                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryGreen,
                    selectedTextColor = PrimaryGreen,
                    unselectedIconColor = TextTertiary,
                    unselectedTextColor = TextTertiary,
                    indicatorColor = MintLight
                )
            )
        }
    }
}