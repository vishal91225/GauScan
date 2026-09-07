package com.gauscan.app.presentation.screens.tools

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.navigation.NavController
import com.gauscan.app.data.model.BreedInfo
import com.gauscan.app.presentation.screens.encyclopedia.AllBreeds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedCompareScreen(navController: NavController) {

    var searchA by remember { mutableStateOf("") }
    var searchB by remember { mutableStateOf("") }
    var selectedA by remember { mutableStateOf<BreedInfo?>(null) }
    var selectedB by remember { mutableStateOf<BreedInfo?>(null) }
    var showDropdownA by remember { mutableStateOf(false) }
    var showDropdownB by remember { mutableStateOf(false) }

    val suggestionsA = if (searchA.length >= 2)
        AllBreeds.list.filter { it.name.contains(searchA, true) }.take(5) else emptyList()
    val suggestionsB = if (searchB.length >= 2)
        AllBreeds.list.filter { it.name.contains(searchB, true) }.take(5) else emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Compare Breeds", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827))
                        Text("Side-by-side breed analysis", fontSize = 12.sp, color = Color(0xFF9CA3AF))
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
                    Icon(Icons.Default.CompareArrows, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text("Breed Comparison Tool", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Search and compare any two breeds\nfrom our database of ${AllBreeds.list.size}+ breeds", fontSize = 12.sp, color = Color.White.copy(alpha = 0.7f), lineHeight = 18.sp)
                    }
                }
            }

            // Search row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Breed A
                Column(modifier = Modifier.weight(1f)) {
                    Text("Breed A", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = searchA,
                        onValueChange = {
                            searchA = it
                            showDropdownA = it.length >= 2
                            if (it.isEmpty()) selectedA = null
                        },
                        placeholder = { Text("Search...", fontSize = 13.sp, color = Color(0xFF9CA3AF)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(18.dp)) },
                        trailingIcon = {
                            if (selectedA != null) {
                                IconButton(onClick = { selectedA = null; searchA = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF9CA3AF))
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1B4332),
                            unfocusedBorderColor = if (selectedA != null) Color(0xFF1B4332) else Color(0xFFE5E7EB),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )
                    if (showDropdownA && suggestionsA.isNotEmpty()) {
                        Card(shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB)), modifier = Modifier.fillMaxWidth()) {
                            Column {
                                suggestionsA.forEach { breed ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().clickable {
                                            selectedA = breed
                                            searchA = breed.name
                                            showDropdownA = false
                                        }.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(if (breed.category == "Buffalo") "🐃" else "🐄", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(breed.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF111827))
                                            Text(breed.originState, fontSize = 11.sp, color = Color(0xFF9CA3AF))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // VS badge
                Box(
                    modifier = Modifier.size(36.dp).align(Alignment.CenterVertically).clip(RoundedCornerShape(10.dp)).background(Color(0xFFE5E7EB)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("VS", fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF6B7280))
                }

                // Breed B
                Column(modifier = Modifier.weight(1f)) {
                    Text("Breed B", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151), modifier = Modifier.padding(bottom = 6.dp))
                    OutlinedTextField(
                        value = searchB,
                        onValueChange = {
                            searchB = it
                            showDropdownB = it.length >= 2
                            if (it.isEmpty()) selectedB = null
                        },
                        placeholder = { Text("Search...", fontSize = 13.sp, color = Color(0xFF9CA3AF)) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(18.dp)) },
                        trailingIcon = {
                            if (selectedB != null) {
                                IconButton(onClick = { selectedB = null; searchB = "" }) {
                                    Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFF9CA3AF))
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF1B4332),
                            unfocusedBorderColor = if (selectedB != null) Color(0xFF1B4332) else Color(0xFFE5E7EB),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        singleLine = true
                    )
                    if (showDropdownB && suggestionsB.isNotEmpty()) {
                        Card(shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFE5E7EB)), modifier = Modifier.fillMaxWidth()) {
                            Column {
                                suggestionsB.forEach { breed ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().clickable {
                                            selectedB = breed
                                            searchB = breed.name
                                            showDropdownB = false
                                        }.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(if (breed.category == "Buffalo") "🐃" else "🐄", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(breed.name, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = Color(0xFF111827))
                                            Text(breed.originState, fontSize = 11.sp, color = Color(0xFF9CA3AF))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Empty state
            if (selectedA == null && selectedB == null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Column(modifier = Modifier.padding(32.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🐄  VS  🐃", fontSize = 32.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Search two breeds above", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF374151), textAlign = TextAlign.Center)
                        Text("Compare milk yield, weight, uses,\ncharacteristics and more", fontSize = 13.sp, color = Color(0xFF9CA3AF), textAlign = TextAlign.Center, lineHeight = 20.sp, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }

            // Comparison table
            if (selectedA != null || selectedB != null) {

                // Name headers
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        // Header row
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(modifier = Modifier.width(90.dp))
                            BreedHeaderCell(selectedA, Color(0xFF1B4332), modifier = Modifier.weight(1f))
                            BreedHeaderCell(selectedB, Color(0xFF7C3AED), modifier = Modifier.weight(1f))
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF3F4F6))

                        // Comparison rows
                        val rows = listOf(
                            Triple("Category", selectedA?.category, selectedB?.category),
                            Triple("Origin", selectedA?.originState, selectedB?.originState),
                            Triple("Milk Yield", selectedA?.milkYield, selectedB?.milkYield),
                            Triple("Conservation", selectedA?.conservationStatus, selectedB?.conservationStatus)
                        )

                        rows.forEachIndexed { index, (label, valA, valB) ->
                            CompareRow(
                                label = label,
                                valueA = valA ?: "—",
                                valueB = valB ?: "—",
                                isAlt = index % 2 == 1,
                                colorA = Color(0xFF1B4332),
                                colorB = Color(0xFF7C3AED)
                            )
                        }
                    }
                }

                // Characteristics comparison
                if (selectedA?.characteristics?.isNotEmpty() == true || selectedB?.characteristics?.isNotEmpty() == true) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Key Characteristics", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827), modifier = Modifier.padding(bottom = 12.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                // A traits
                                Column(modifier = Modifier.weight(1f)) {
                                    selectedA?.let { breed ->
                                        Text(breed.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1B4332), modifier = Modifier.padding(bottom = 6.dp))
                                        breed.characteristics.forEach { trait ->
                                            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                                                Box(modifier = Modifier.padding(top = 5.dp).size(5.dp).background(Color(0xFF1B4332), RoundedCornerShape(50)))
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(trait, fontSize = 11.sp, color = Color(0xFF374151))
                                            }
                                        }
                                    } ?: Text("—", fontSize = 13.sp, color = Color(0xFF9CA3AF))
                                }

                                // Divider
                                Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color(0xFFE5E7EB)))

                                // B traits
                                Column(modifier = Modifier.weight(1f)) {
                                    selectedB?.let { breed ->
                                        Text(breed.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF7C3AED), modifier = Modifier.padding(bottom = 6.dp))
                                        breed.characteristics.forEach { trait ->
                                            Row(modifier = Modifier.padding(vertical = 2.dp), verticalAlignment = Alignment.Top) {
                                                Box(modifier = Modifier.padding(top = 5.dp).size(5.dp).background(Color(0xFF7C3AED), RoundedCornerShape(50)))
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(trait, fontSize = 11.sp, color = Color(0xFF374151))
                                            }
                                        }
                                    } ?: Text("—", fontSize = 13.sp, color = Color(0xFF9CA3AF))
                                }
                            }
                        }
                    }
                }

                // Uses comparison
                if (selectedA?.uses?.isNotEmpty() == true || selectedB?.uses?.isNotEmpty() == true) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Primary Uses", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827), modifier = Modifier.padding(bottom = 12.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    selectedA?.uses?.forEach { use ->
                                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFF1B4332).copy(alpha = 0.1f)) {
                                            Text(use, fontSize = 11.sp, color = Color(0xFF1B4332), fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                        }
                                    } ?: Text("—", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                                }
                                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                    selectedB?.uses?.forEach { use ->
                                        Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFF7C3AED).copy(alpha = 0.1f)) {
                                            Text(use, fontSize = 11.sp, color = Color(0xFF7C3AED), fontWeight = FontWeight.Medium, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                        }
                                    } ?: Text("—", color = Color(0xFF9CA3AF), fontSize = 13.sp)
                                }
                            }
                        }
                    }
                }

                // Description comparison
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("About", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111827), modifier = Modifier.padding(bottom = 12.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Column(modifier = Modifier.weight(1f)) {
                                selectedA?.let {
                                    Text(it.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF1B4332), modifier = Modifier.padding(bottom = 4.dp))
                                    Text(it.description, fontSize = 12.sp, color = Color(0xFF4B5563), lineHeight = 18.sp)
                                } ?: Text("Select breed A", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                            }
                            Box(modifier = Modifier.width(1.dp).background(Color(0xFFE5E7EB)))
                            Column(modifier = Modifier.weight(1f)) {
                                selectedB?.let {
                                    Text(it.name, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF7C3AED), modifier = Modifier.padding(bottom = 4.dp))
                                    Text(it.description, fontSize = 12.sp, color = Color(0xFF4B5563), lineHeight = 18.sp)
                                } ?: Text("Select breed B", fontSize = 12.sp, color = Color(0xFF9CA3AF))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun BreedHeaderCell(breed: BreedInfo?, color: Color, modifier: Modifier) {
    Box(
        modifier = modifier.clip(RoundedCornerShape(10.dp)).background(color.copy(alpha = 0.1f)).padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(if (breed?.category == "Buffalo") "🐃" else "🐄", fontSize = 24.sp)
            Text(breed?.name ?: "Select", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color, textAlign = TextAlign.Center)
            if (breed != null) {
                Text(breed.category, fontSize = 10.sp, color = color.copy(alpha = 0.7f))
            }
        }
    }
}

@Composable
private fun CompareRow(label: String, valueA: String, valueB: String, isAlt: Boolean, colorA: Color, colorB: Color) {
    val bgColor = if (isAlt) Color(0xFFF9FAFB) else Color.White
    Row(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(6.dp)).background(bgColor).padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF6B7280), modifier = Modifier.width(90.dp))
        Text(valueA, fontSize = 12.sp, color = colorA, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text(valueB, fontSize = 12.sp, color = colorB, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
    }
}