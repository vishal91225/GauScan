package com.gauscan.app.presentation.screens.result

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.gauscan.app.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    navController: NavController,
    scanId: String,
    viewModel: ResultViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scan = uiState.scan
    val context = LocalContext.current

    LaunchedEffect(scanId) { viewModel.loadResult(scanId) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Scan Result", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        if (scan != null) Text(scan.breedName, fontSize = 12.sp, color = Color(0xFF9CA3AF))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = false }
                        }
                    }) {
                        Icon(Icons.Default.Home, contentDescription = "Home", tint = Color(0xFF374151))
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.shareResult(context, uiState) }) {
                        Icon(Icons.Default.Share, contentDescription = "Share", tint = Color(0xFF374151))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F5F0))
            )
        },
        containerColor = Color(0xFFF8F5F0)
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color(0xFF1B4332))
                        Text("Loading result...", fontSize = 14.sp, color = Color(0xFF9CA3AF), modifier = Modifier.padding(top = 12.dp))
                    }
                }
            }
            scan == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Result not found", color = Color(0xFF9CA3AF))
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Hero Image
                    Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                        AsyncImage(
                            model = scan.imageUrl.ifEmpty { scan.localImagePath },
                            contentDescription = "Scanned",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().height(100.dp)
                                .align(Alignment.BottomCenter)
                                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xFFF8F5F0))))
                        )
                        if (scan.diseaseDetected && scan.diseaseName.isNotEmpty()) {
                            Surface(
                                modifier = Modifier.align(Alignment.TopStart).padding(12.dp),
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFDC2626)
                            ) {
                                Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Disease Detected", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                                }
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                        // Main Breed Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B4332))
                        ) {
                            Column(modifier = Modifier.padding(20.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFFD97706).copy(alpha = 0.3f), modifier = Modifier.wrapContentWidth()) {
                                            Text(scan.category.uppercase(), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFBBF24), letterSpacing = 1.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(scan.breedName, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                                        Text(scan.originState, fontSize = 13.sp, color = Color.White.copy(alpha = 0.6f), modifier = Modifier.padding(top = 3.dp))
                                    }
                                    val statusColor = when (scan.conservationStatus) {
                                        "Endangered" -> Color(0xFFFCA5A5)
                                        "Vulnerable" -> Color(0xFFFDE68A)
                                        else -> Color(0xFF6EE7B7)
                                    }
                                    Surface(shape = RoundedCornerShape(8.dp), color = statusColor.copy(alpha = 0.2f)) {
                                        Text(scan.conservationStatus.ifEmpty { "Stable" }, fontSize = 11.sp, color = statusColor, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                                HorizontalDivider(color = Color.White.copy(alpha = 0.1f))
                                Spacer(modifier = Modifier.height(14.dp))
                                val confidence = scan.confidence
                                val confColor = when { confidence >= 0.8f -> Color(0xFF6EE7B7); confidence >= 0.5f -> Color(0xFFFDE68A); else -> Color(0xFFFCA5A5) }
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                    Text("AI Confidence", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
                                    Text("${(confidence * 100).toInt()}%", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = confColor)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(
                                    progress = { confidence },
                                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                                    color = confColor, trackColor = Color.White.copy(alpha = 0.12f)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Disease Alert Card
                        if (scan.diseaseDetected && scan.diseaseName.isNotEmpty()) {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
                                border = BorderStroke(1.5.dp, Color(0xFFDC2626).copy(alpha = 0.4f))
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFDC2626)), contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.LocalHospital, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("Health Alert", fontSize = 12.sp, color = Color(0xFF9B1C1C), fontWeight = FontWeight.SemiBold)
                                            Text(scan.diseaseName, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFFDC2626))
                                        }
                                        if (scan.diseaseSeverity.isNotEmpty()) {
                                            val sevColor = when (scan.diseaseSeverity) { "High" -> Color(0xFFDC2626); "Medium" -> Color(0xFFD97706); else -> Color(0xFF059669) }
                                            Surface(shape = RoundedCornerShape(6.dp), color = sevColor.copy(alpha = 0.15f)) {
                                                Text("${scan.diseaseSeverity} Risk", fontSize = 11.sp, color = sevColor, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                            }
                                        }
                                    }

                                    if (scan.diseaseSymptoms.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(14.dp))
                                        Text("Observed Symptoms", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7F1D1D))
                                        Spacer(modifier = Modifier.height(6.dp))
                                        scan.diseaseSymptoms.forEach { s ->
                                            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                                                Box(modifier = Modifier.padding(top = 6.dp).size(5.dp).background(Color(0xFFDC2626), CircleShape))
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(s, fontSize = 13.sp, color = Color(0xFF7F1D1D))
                                            }
                                        }
                                    }

                                    if (scan.diseaseTreatment.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(14.dp))
                                        Text("Recommended Treatment", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF7F1D1D))
                                        Spacer(modifier = Modifier.height(6.dp))
                                        scan.diseaseTreatment.forEachIndexed { i, step ->
                                            Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                                                Box(modifier = Modifier.size(20.dp).background(Color(0xFFDC2626).copy(alpha = 0.15f), CircleShape), contentAlignment = Alignment.Center) {
                                                    Text("${i + 1}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                                                }
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Text(step, fontSize = 13.sp, color = Color(0xFF7F1D1D), modifier = Modifier.padding(top = 2.dp))
                                            }
                                        }
                                    }

                                    if (scan.vetAdvice.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)).background(Color(0xFFDC2626).copy(alpha = 0.1f)).padding(12.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Column {
                                                Text("Vet Advice", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                                                Text(scan.vetAdvice, fontSize = 13.sp, color = Color(0xFF7F1D1D), modifier = Modifier.padding(top = 2.dp))
                                            }
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        // Quick info grid
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            QuickInfoTile(Modifier.weight(1f), Icons.Default.Water, "Milk Yield", scan.milkYield.ifEmpty { "N/A" }, Color(0xFF059669), Color(0xFFD1FAE5))
                            QuickInfoTile(Modifier.weight(1f), Icons.Default.LocationOn, "Origin", scan.originState.ifEmpty { "India" }, Color(0xFF1E3A5F), Color(0xFFDBEAFE))
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            QuickInfoTile(Modifier.weight(1f), Icons.Default.Favorite, "Lifespan", scan.lifespan.ifEmpty { "15–20 yrs" }, Color(0xFF7C3AED), Color(0xFFEDE9FE))
                            QuickInfoTile(Modifier.weight(1f), Icons.Default.FitnessCenter, "Weight", scan.bodyWeight.ifEmpty { "N/A" }, Color(0xFF92400E), Color(0xFFFEF3C7))
                        }
                        Spacer(modifier = Modifier.height(14.dp))

                        // About
                        SectionCard("About this Breed", Icons.Default.Info) {
                            Text(scan.description, fontSize = 14.sp, lineHeight = 22.sp, color = Color(0xFF4B5563))
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        // Characteristics
                        if (scan.characteristics.isNotEmpty()) {
                            SectionCard("Key Characteristics", Icons.Default.CheckCircle) {
                                scan.characteristics.forEach { t ->
                                    Row(modifier = Modifier.padding(vertical = 3.dp), verticalAlignment = Alignment.Top) {
                                        Box(modifier = Modifier.padding(top = 6.dp).size(5.dp).background(Color(0xFF1B4332), CircleShape))
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(t, fontSize = 13.sp, color = Color(0xFF374151))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // Details
                        val details = listOfNotNull(
                            if (scan.temperament.isNotEmpty()) "Temperament" to scan.temperament else null,
                            if (scan.climateAdaptability.isNotEmpty()) "Climate" to scan.climateAdaptability else null,
                            if (scan.dietaryNeeds.isNotEmpty()) "Diet Needs" to scan.dietaryNeeds else null,
                            if (scan.economicValue.isNotEmpty()) "Economic Value" to scan.economicValue else null
                        )
                        if (details.isNotEmpty()) {
                            SectionCard("Breed Details", Icons.Default.Assignment) {
                                details.forEachIndexed { i, (label, value) ->
                                    DetailRow(label, value)
                                    if (i < details.size - 1) HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFF3F4F6))
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        // Uses
                        if (scan.uses.isNotEmpty()) {
                            SectionCard("Primary Uses", Icons.Default.Star) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.horizontalScroll(rememberScrollState())) {
                                    scan.uses.forEach { use ->
                                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFECFDF5), border = BorderStroke(1.dp, Color(0xFFD1FAE5))) {
                                            Text(use, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Color(0xFF065F46), modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
                                        }
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                        }

                        // Actions
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = { navController.navigate(Screen.Scan.route) },
                                modifier = Modifier.weight(1f).height(50.dp),
                                shape = RoundedCornerShape(14.dp),
                                border = BorderStroke(1.dp, Color(0xFF1B4332))
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF1B4332))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Scan Again", color = Color(0xFF1B4332), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }
                            Button(
                                onClick = { navController.navigate(Screen.Encyclopedia.route) },
                                modifier = Modifier.weight(1f).height(50.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332))
                            ) {
                                Icon(Icons.Default.MenuBook, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Encyclopedia", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionCard(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                Box(modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFD1FAE5)), contentAlignment = Alignment.Center) {
                    Icon(icon, contentDescription = null, tint = Color(0xFF1B4332), modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
            }
            content()
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 13.sp, color = Color(0xFF9CA3AF), modifier = Modifier.weight(0.38f))
        Text(value, fontSize = 13.sp, color = Color(0xFF111827), fontWeight = FontWeight.Medium, modifier = Modifier.weight(0.62f))
    }
}

@Composable
private fun QuickInfoTile(modifier: Modifier, icon: ImageVector, label: String, value: String, iconColor: Color, bg: Color) {
    Card(modifier = modifier, shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = bg)) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(label, fontSize = 11.sp, color = iconColor.copy(alpha = 0.7f))
                Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = iconColor)
            }
        }
    }
}