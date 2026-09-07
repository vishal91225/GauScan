package com.gauscan.app.presentation.screens.tools

import androidx.compose.animation.AnimatedVisibility
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

data class HealthRecord(
    val id: String = UUID.randomUUID().toString(),
    val animalName: String = "",
    val type: String = "",        // Vaccination / Treatment / Deworming / Checkup
    val title: String = "",
    val date: String = "",
    val nextDueDate: String = "",
    val vetName: String = "",
    val notes: String = "",
    val severity: String = ""    // For treatments: Low/Medium/High
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthDiaryScreen(navController: NavController) {

    val records = remember { mutableStateListOf<HealthRecord>() }
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") }
    var expandedRecord by remember { mutableStateOf<String?>(null) }

    // Dialog state
    var animalName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("Vaccination") }
    var title by remember { mutableStateOf("") }
    var vetName by remember { mutableStateOf("") }
    var nextDue by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val today = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())

    val filteredRecords = if (selectedFilter == "All") records
    else records.filter { it.type == selectedFilter }

    // Add Record Dialog
    if (showAddDialog) {
        Dialog(onDismissRequest = { showAddDialog = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFF1B4332)),
                            contentAlignment = Alignment.Center
                        ) { Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp)) }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Add Health Record", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                    }
                    HorizontalDivider(color = Color(0xFFE5E7EB))

                    // Animal name
                    OutlinedTextField(
                        value = animalName,
                        onValueChange = { animalName = it },
                        label = { Text("Animal Name / Tag No.") },
                        leadingIcon = { Icon(Icons.Default.Pets, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF1B4332)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        singleLine = true
                    )

                    // Record type
                    Text("Record Type", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            "Vaccination" to Color(0xFF059669),
                            "Treatment" to Color(0xFFDC2626),
                            "Deworming" to Color(0xFFD97706),
                            "Checkup" to Color(0xFF1B4332)
                        ).forEach { (type, color) ->
                            val isSelected = selectedType == type
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) color else Color(0xFFF9FAFB),
                                border = BorderStroke(1.dp, if (isSelected) color else Color(0xFFE5E7EB)),
                                modifier = Modifier.clickable { selectedType = type }
                            ) {
                                Text(
                                    type,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else Color(0xFF6B7280),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }

                    // Title/Medicine name
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Vaccine / Medicine / Details") },
                        leadingIcon = { Icon(Icons.Default.Medication, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF1B4332)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        singleLine = true
                    )

                    // Vet name
                    OutlinedTextField(
                        value = vetName,
                        onValueChange = { vetName = it },
                        label = { Text("Vet Name (optional)") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF1B4332)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        singleLine = true
                    )

                    // Next due date
                    OutlinedTextField(
                        value = nextDue,
                        onValueChange = { nextDue = it },
                        label = { Text("Next Due Date (e.g. 15 Sep 2025)") },
                        leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(18.dp), tint = Color(0xFF1B4332)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        singleLine = true
                    )

                    // Notes
                    OutlinedTextField(
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFF1B4332), unfocusedBorderColor = Color(0xFFE5E7EB), focusedContainerColor = Color.White, unfocusedContainerColor = Color.White),
                        maxLines = 3
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        OutlinedButton(
                            onClick = { showAddDialog = false },
                            modifier = Modifier.weight(1f).height(46.dp),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                        ) { Text("Cancel", color = Color(0xFF374151)) }

                        Button(
                            onClick = {
                                if (animalName.isNotEmpty() && title.isNotEmpty()) {
                                    records.add(0, HealthRecord(
                                        animalName = animalName,
                                        type = selectedType,
                                        title = title,
                                        date = today,
                                        nextDueDate = nextDue,
                                        vetName = vetName,
                                        notes = notes
                                    ))
                                }
                                showAddDialog = false
                                animalName = ""; title = ""; vetName = ""; nextDue = ""; notes = ""
                                selectedType = "Vaccination"
                            },
                            modifier = Modifier.weight(1f).height(46.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B4332))
                        ) { Text("Save Record") }
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
                        Text("Health Diary", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Text("Vaccination & treatment records", fontSize = 12.sp, color = Color(0xFF9CA3AF))
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
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
        containerColor = Color(0xFFF8F5F0)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Stats row
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf(
                        Triple("💉", "Vaccinations", records.count { it.type == "Vaccination" }.toString()),
                        Triple("💊", "Treatments", records.count { it.type == "Treatment" }.toString()),
                        Triple("🔬", "Deworming", records.count { it.type == "Deworming" }.toString())
                    ).forEach { (emoji, label, count) ->
                        Card(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                        ) {
                            Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(emoji, fontSize = 20.sp)
                                Text(count, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1B4332))
                                Text(label, fontSize = 10.sp, color = Color(0xFF9CA3AF), textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }

            // Upcoming due reminders
            val upcoming = records.filter { it.nextDueDate.isNotEmpty() }.take(2)
            if (upcoming.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.4f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 10.dp)) {
                                Icon(Icons.Default.NotificationsActive, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Upcoming Due", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                            }
                            upcoming.forEach { record ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text("${record.animalName} — ${record.title}", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF92400E))
                                        Text(record.type, fontSize = 11.sp, color = Color(0xFFD97706))
                                    }
                                    Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFD97706).copy(alpha = 0.15f)) {
                                        Text(record.nextDueDate, fontSize = 11.sp, color = Color(0xFF92400E), fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Filter chips
            item {
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("All", "Vaccination", "Treatment", "Deworming", "Checkup").forEach { filter ->
                        val isSelected = selectedFilter == filter
                        val color = when (filter) {
                            "Vaccination" -> Color(0xFF059669)
                            "Treatment" -> Color(0xFFDC2626)
                            "Deworming" -> Color(0xFFD97706)
                            "Checkup" -> Color(0xFF1B4332)
                            else -> Color(0xFF1B4332)
                        }
                        FilterChip(
                            onClick = { selectedFilter = filter },
                            label = { Text(filter, fontSize = 12.sp) },
                            selected = isSelected,
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = color,
                                selectedLabelColor = Color.White,
                                containerColor = Color.White,
                                labelColor = Color(0xFF6B7280)
                            ),
                            border = FilterChipDefaults.filterChipBorder(enabled = true, selected = isSelected, borderColor = Color(0xFFE5E7EB), selectedBorderColor = color)
                        )
                    }
                }
            }

            // Records
            if (filteredRecords.isEmpty()) {
                item {
                    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB))) {
                        Column(modifier = Modifier.padding(32.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.MedicalServices, contentDescription = null, tint = Color(0xFFD1D5DB), modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("No records yet", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
                            Text("Tap + to add your first health record", fontSize = 13.sp, color = Color(0xFF9CA3AF), textAlign = TextAlign.Center, modifier = Modifier.padding(top = 6.dp))
                        }
                    }
                }
            } else {
                items(filteredRecords) { record ->
                    HealthRecordCard(
                        record = record,
                        isExpanded = expandedRecord == record.id,
                        onToggle = { expandedRecord = if (expandedRecord == record.id) null else record.id },
                        onDelete = { records.remove(record) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
private fun HealthRecordCard(record: HealthRecord, isExpanded: Boolean, onToggle: () -> Unit, onDelete: () -> Unit) {
    val typeColor = when (record.type) {
        "Vaccination" -> Color(0xFF059669)
        "Treatment" -> Color(0xFFDC2626)
        "Deworming" -> Color(0xFFD97706)
        else -> Color(0xFF1B4332)
    }
    val typeIcon = when (record.type) {
        "Vaccination" -> Icons.Default.Vaccines
        "Treatment" -> Icons.Default.LocalHospital
        "Deworming" -> Icons.Default.Science
        else -> Icons.Default.HealthAndSafety
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onToggle),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(if (isExpanded) 1.5.dp else 1.dp, if (isExpanded) typeColor else Color(0xFFE5E7EB))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(42.dp).clip(RoundedCornerShape(12.dp)).background(typeColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(typeIcon, contentDescription = null, tint = typeColor, modifier = Modifier.size(22.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(record.title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                    Text("${record.animalName}  •  ${record.date}", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                }
                Surface(shape = RoundedCornerShape(7.dp), color = typeColor.copy(alpha = 0.1f)) {
                    Text(record.type, fontSize = 10.sp, color = typeColor, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp))
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF3F4F6))
                    if (record.vetName.isNotEmpty()) {
                        InfoRow(Icons.Default.Person, "Vet", record.vetName, typeColor)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    if (record.nextDueDate.isNotEmpty()) {
                        InfoRow(Icons.Default.CalendarToday, "Next Due", record.nextDueDate, Color(0xFFD97706))
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    if (record.notes.isNotEmpty()) {
                        InfoRow(Icons.Default.Notes, "Notes", record.notes, Color(0xFF6B7280))
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                    TextButton(
                        onClick = onDelete,
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFDC2626))
                    ) {
                        Icon(Icons.Default.DeleteOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Delete Record", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String, iconColor: Color) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(15.dp).padding(top = 1.dp))
        Spacer(modifier = Modifier.width(6.dp))
        Text("$label: ", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151))
        Text(value, fontSize = 12.sp, color = Color(0xFF6B7280))
    }
}