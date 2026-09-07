package com.gauscan.app.presentation.screens.tools

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.*

data class MilkEntry(
    val id: String = UUID.randomUUID().toString(),
    val date: String = "",
    val morning: Float = 0f,
    val evening: Float = 0f,
    val animalName: String = "",
    val notes: String = ""
) {
    val total: Float get() = morning + evening
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MilkTrackerScreen(navController: NavController) {

    // In-memory state (persists during session)
    val milkEntries = remember { mutableStateListOf<MilkEntry>() }
    var showAddDialog by remember { mutableStateOf(false) }
    var animalName by remember { mutableStateOf("") }
    var morningMilk by remember { mutableStateOf("") }
    var eveningMilk by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }

    val today = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
    val todayEntries = milkEntries.filter { it.date == today }
    val todayTotal = todayEntries.sumOf { it.total.toDouble() }.toFloat()

    // Weekly data
    val weeklyData = milkEntries
        .groupBy { it.date }
        .map { (date, entries) -> date to entries.sumOf { it.total.toDouble() }.toFloat() }
        .takeLast(7)

    if (showAddDialog) {
        Dialog(onDismissRequest = { showAddDialog = false }) {
            Card(shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Column(modifier = Modifier.padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text("Add Milk Record", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                    HorizontalDivider(color = Color(0xFFE5E7EB))

                    OutlinedTextField(
                        value = animalName,
                        onValueChange = { animalName = it },
                        label = { Text("Animal Name / Tag") },
                        leadingIcon = { Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        singleLine = true
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedTextField(
                            value = morningMilk,
                            onValueChange = { if (it.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?\$"))) morningMilk = it },
                            label = { Text("Morning (L)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = eveningMilk,
                            onValueChange = { if (it.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?\$"))) eveningMilk = it },
                            label = { Text("Evening (L)") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                            singleLine = true
                        )
                    }

                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes (optional)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        maxLines = 2
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(
                            onClick = { showAddDialog = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                        ) { Text("Cancel", color = Color(0xFF374151)) }

                        Button(
                            onClick = {
                                val m = morningMilk.toFloatOrNull() ?: 0f
                                val e = eveningMilk.toFloatOrNull() ?: 0f
                                if (m > 0 || e > 0) {
                                    milkEntries.add(0, MilkEntry(
                                        date = today,
                                        morning = m,
                                        evening = e,
                                        animalName = animalName.ifEmpty { "Animal" },
                                        notes = notes
                                    ))
                                }
                                showAddDialog = false
                                animalName = ""; morningMilk = ""; eveningMilk = ""; notes = ""
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332))
                        ) { Text("Save") }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Milk Tracker", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Text("Track daily milk production", fontSize = 12.sp, color = Color(0xFF9CA3AF))
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF1B4332),
                contentColor = Color.White,
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add record")
            }
        },
        containerColor = Color(0xFFF8F5F0)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Today summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1B4332))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Today — $today", fontSize = 12.sp, color = Color.White.copy(alpha = 0.6f))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                String.format("%.1f", todayTotal),
                                fontSize = 40.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                            Text(" L total", fontSize = 16.sp, color = Color.White.copy(alpha = 0.7f), modifier = Modifier.padding(bottom = 6.dp))
                        }
                        if (todayEntries.isNotEmpty()) {
                            Row(modifier = Modifier.padding(top = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                Text("🌅 Morning: ${String.format("%.1f", todayEntries.sumOf { it.morning.toDouble() })} L", fontSize = 13.sp, color = Color.White.copy(alpha = 0.8f))
                                Text("🌆 Evening: ${String.format("%.1f", todayEntries.sumOf { it.evening.toDouble() })} L", fontSize = 13.sp, color = Color.White.copy(alpha = 0.8f))
                            }
                        } else {
                            Text("No records today. Tap + to add.", fontSize = 13.sp, color = Color.White.copy(alpha = 0.6f), modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }

            // Weekly bar chart
            if (weeklyData.size > 1) {
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB))) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Weekly Trend", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827), modifier = Modifier.padding(bottom = 16.dp))
                            val maxVal = weeklyData.maxOfOrNull { it.second } ?: 1f
                            Row(
                                modifier = Modifier.fillMaxWidth().height(100.dp),
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.Bottom
                            ) {
                                weeklyData.forEach { (date, total) ->
                                    val heightFrac = if (maxVal > 0) total / maxVal else 0f
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Bottom
                                    ) {
                                        Text(String.format("%.0f", total), fontSize = 8.sp, color = Color(0xFF059669), fontWeight = FontWeight.Bold)
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height((70 * heightFrac).dp.coerceAtLeast(4.dp))
                                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                                .background(Color(0xFF1B4332))
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(date.take(2), fontSize = 8.sp, color = Color(0xFF9CA3AF))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Records list
            if (milkEntries.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB))) {
                        Column(modifier = Modifier.padding(32.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Water, contentDescription = null, tint = Color(0xFFD1D5DB), modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("No records yet", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
                            Text("Tap + to add your first milk record", fontSize = 13.sp, color = Color(0xFF9CA3AF), textAlign = TextAlign.Center, modifier = Modifier.padding(top = 6.dp))
                        }
                    }
                }
            } else {
                item { Text("All Records (${milkEntries.size})", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827)) }
                items(milkEntries) { entry ->
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB))) {
                        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFD1FAE5)), contentAlignment = Alignment.Center) {
                                Text(String.format("%.0f", entry.total), fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF065F46))
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(entry.animalName, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF111827))
                                Text("${entry.date}  •  🌅 ${entry.morning}L  🌆 ${entry.evening}L", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                                if (entry.notes.isNotEmpty()) Text(entry.notes, fontSize = 11.sp, color = Color(0xFF6B7280), modifier = Modifier.padding(top = 2.dp))
                            }
                            Text("${String.format("%.1f", entry.total)} L", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF059669))
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}