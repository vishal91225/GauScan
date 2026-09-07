package com.gauscan.app.presentation.screens.tools

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedCalculatorScreen(navController: NavController) {

    // State
    var selectedAnimal by remember { mutableStateOf("Cattle") }
    var selectedPurpose by remember { mutableStateOf("Dairy") }
    var bodyWeight by remember { mutableStateOf("") }
    var milkProduction by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }
    var feedResult by remember { mutableStateOf<FeedResult?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Feed Calculator", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Text("Daily nutrition planner", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF374151))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF8F5F0))
            )
        },
        containerColor = Color(0xFFF8F5F0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Header card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B4332))
            ) {
                Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(52.dp).clip(RoundedCornerShape(14.dp)).background(Color.White.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Calculate, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Daily Feed Calculator", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Get exact daily nutrition\nrequirements for your animal", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f), lineHeight = 18.sp)
                    }
                }
            }

            // Animal Type
            SectionLabel("Animal Type")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Cattle" to "🐄", "Buffalo" to "🐃").forEach { (type, emoji) ->
                    SelectionChip(
                        modifier = Modifier.weight(1f),
                        title = "$emoji $type",
                        isSelected = selectedAnimal == type,
                        onClick = { selectedAnimal = type; showResult = false }
                    )
                }
            }

            // Purpose
            SectionLabel("Primary Purpose")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Dairy", "Draft", "Beef").forEach { purpose ->
                    SelectionChip(
                        modifier = Modifier.weight(1f),
                        title = purpose,
                        isSelected = selectedPurpose == purpose,
                        onClick = { selectedPurpose = purpose; showResult = false }
                    )
                }
            }

            // Body Weight
            SectionLabel("Body Weight (kg)")
            OutlinedTextField(
                value = bodyWeight,
                onValueChange = { if (it.length <= 4 && it.all { c -> c.isDigit() }) { bodyWeight = it; showResult = false } },
                placeholder = { Text("e.g. 450", color = Color(0xFF9CA3AF)) },
                leadingIcon = { Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = Color(0xFF1B4332), modifier = Modifier.size(20.dp)) },
                suffix = { Text("kg", color = Color(0xFF9CA3AF)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1B4332),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            // Milk production (only for dairy)
            AnimatedVisibility(visible = selectedPurpose == "Dairy") {
                Column {
                    SectionLabel("Daily Milk Production (L)")
                    OutlinedTextField(
                        value = milkProduction,
                        onValueChange = { if (it.length <= 3 && it.all { c -> c.isDigit() }) { milkProduction = it; showResult = false } },
                        placeholder = { Text("e.g. 10", color = Color(0xFF9CA3AF)) },
                        leadingIcon = { Icon(Icons.Default.Water, contentDescription = null, tint = Color(0xFF1B4332), modifier = Modifier.size(20.dp)) },
                        suffix = { Text("L/day", color = Color(0xFF9CA3AF)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1B4332),
                            unfocusedBorderColor = Color(0xFFE5E7EB),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )
                }
            }

            // Calculate button
            Button(
                onClick = {
                    val weight = bodyWeight.toFloatOrNull() ?: 0f
                    val milk = milkProduction.toFloatOrNull() ?: 0f
                    if (weight > 0) {
                        feedResult = calculateFeed(selectedAnimal, selectedPurpose, weight, milk)
                        showResult = true
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                enabled = bodyWeight.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B4332),
                    disabledContainerColor = Color(0xFFE5E7EB)
                )
            ) {
                Icon(Icons.Default.Calculate, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Calculate Feed Requirements", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            }

            // Result
            AnimatedVisibility(visible = showResult && feedResult != null) {
                feedResult?.let { result ->
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        // Daily summary
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
                            border = BorderStroke(1.dp, Color(0xFF059669).copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 12.dp)) {
                                    Box(
                                        modifier = Modifier.size(32.dp).clip(CircleShape).background(Color(0xFF059669)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text("Daily Feed Plan", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))
                                }

                                listOf(
                                    Triple(Icons.Default.Grass, "Dry Fodder", "${result.dryFodder} kg"),
                                    Triple(Icons.Default.Water, "Green Fodder", "${result.greenFodder} kg"),
                                    Triple(Icons.Default.Grain, "Concentrate Mix", "${result.concentrate} kg"),
                                    Triple(Icons.Default.LocalDrink, "Water", "${result.water} liters"),
                                    Triple(Icons.Default.Science, "Mineral Mix", "${result.mineralMix} g")
                                ).forEach { (icon, label, value) ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(icon, contentDescription = null, tint = Color(0xFF059669), modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(label, fontSize = 14.sp, color = Color(0xFF374151))
                                        }
                                        Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))
                                    }
                                    if (label != "Mineral Mix") HorizontalDivider(color = Color(0xFF059669).copy(alpha = 0.1f))
                                }
                            }
                        }

                        // Feeding schedule
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Feeding Schedule", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827), modifier = Modifier.padding(bottom = 12.dp))
                                listOf(
                                    Triple("🌅", "Morning (6 AM)", "${result.morningFeed} kg fodder + ${result.morningConcentrate} kg concentrate"),
                                    Triple("☀️", "Afternoon (12 PM)", "${result.afternoonFeed} kg green fodder"),
                                    Triple("🌆", "Evening (5 PM)", "${result.eveningFeed} kg fodder + ${result.eveningConcentrate} kg concentrate")
                                ).forEach { (emoji, time, amount) ->
                                    Row(modifier = Modifier.padding(vertical = 6.dp), verticalAlignment = Alignment.Top) {
                                        Text(emoji, fontSize = 20.sp)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(time, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF111827))
                                            Text(amount, fontSize = 12.sp, color = Color(0xFF6B7280))
                                        }
                                    }
                                }
                            }
                        }

                        // Tips
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                            border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.3f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("💡 Pro Tips", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E), modifier = Modifier.padding(bottom = 8.dp))
                                result.tips.forEach { tip ->
                                    Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                                        Box(modifier = Modifier.padding(top = 6.dp).size(5.dp).background(Color(0xFFD97706), CircleShape))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(tip, fontSize = 13.sp, color = Color(0xFF92400E).copy(alpha = 0.85f))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// Feed calculation logic
data class FeedResult(
    val dryFodder: Float,
    val greenFodder: Float,
    val concentrate: Float,
    val water: Float,
    val mineralMix: Float,
    val morningFeed: Float,
    val morningConcentrate: Float,
    val afternoonFeed: Float,
    val eveningFeed: Float,
    val eveningConcentrate: Float,
    val tips: List<String>
)

fun calculateFeed(animal: String, purpose: String, weight: Float, milkLiters: Float): FeedResult {
    // Standard livestock nutrition formulas
    val dryMatterNeed = weight * 0.025f  // 2.5% of body weight

    val dryFodder: Float
    val greenFodder: Float
    val concentrate: Float
    val water: Float
    val mineralMix: Float

    when (purpose) {
        "Dairy" -> {
            val maintenanceConc = weight * 0.004f
            val milkConc = milkLiters * 0.35f
            concentrate = maintenanceConc + milkConc
            dryFodder = (dryMatterNeed * 0.4f)
            greenFodder = (dryMatterNeed * 1.8f)
            water = milkLiters * 3f + weight * 0.05f
            mineralMix = 50f + (milkLiters * 5f)
        }
        "Draft" -> {
            concentrate = weight * 0.005f + 2f
            dryFodder = dryMatterNeed * 0.5f
            greenFodder = dryMatterNeed * 1.5f
            water = weight * 0.06f
            mineralMix = 40f
        }
        else -> { // Beef
            concentrate = weight * 0.006f
            dryFodder = dryMatterNeed * 0.45f
            greenFodder = dryMatterNeed * 1.6f
            water = weight * 0.055f
            mineralMix = 35f
        }
    }

    val tips = when (purpose) {
        "Dairy" -> listOf(
            "Increase concentrate by 0.35 kg per extra liter of milk",
            "Always provide clean water — milk yield drops with dehydration",
            "Add 50g bypass fat for high-yielding animals (>15L/day)",
            "Ensure 16–18% crude protein in concentrate mix"
        )
        "Draft" -> listOf(
            "Increase concentrate by 20% on heavy work days",
            "Give extra rest and water after prolonged draft work",
            "Ensure 10–12% crude protein in diet"
        )
        else -> listOf(
            "Ensure 12–14% crude protein for good growth",
            "Weigh animal monthly to adjust feed quantity",
            "Provide mineral lick blocks free access"
        )
    }

    return FeedResult(
        dryFodder = String.format("%.1f", dryFodder).toFloat(),
        greenFodder = String.format("%.1f", greenFodder).toFloat(),
        concentrate = String.format("%.1f", concentrate).toFloat(),
        water = String.format("%.0f", water).toFloat(),
        mineralMix = mineralMix,
        morningFeed = String.format("%.1f", dryFodder * 0.5f).toFloat(),
        morningConcentrate = String.format("%.1f", concentrate * 0.5f).toFloat(),
        afternoonFeed = String.format("%.1f", greenFodder * 0.5f).toFloat(),
        eveningFeed = String.format("%.1f", dryFodder * 0.5f).toFloat(),
        eveningConcentrate = String.format("%.1f", concentrate * 0.5f).toFloat(),
        tips = tips
    )
}

@Composable
private fun SectionLabel(text: String) {
    Text(text, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
}

@Composable
private fun SelectionChip(modifier: Modifier, title: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) Color(0xFF1B4332) else Color.White)
            .border(BorderStroke(1.dp, if (isSelected) Color(0xFF1B4332) else Color(0xFFE5E7EB)), RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            title,
            fontSize = 13.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) Color.White else Color(0xFF374151),
            textAlign = TextAlign.Center
        )
    }
}