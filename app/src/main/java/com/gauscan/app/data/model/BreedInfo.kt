package com.gauscan.app.data.model

data class BreedInfo(
    val name: String,
    val category: String,       // "Cattle" or "Buffalo"
    val originState: String,
    val milkYield: String,
    val description: String,
    val characteristics: List<String>,
    val uses: List<String>,
    val conservationStatus: String,
    val imageDescription: String  // Used for display placeholder
)

// Static breed encyclopedia data
object BreedDatabase {
    val allBreeds = listOf(
        BreedInfo(
            name = "Gir",
            category = "Cattle",
            originState = "Gujarat",
            milkYield = "10-16 liters/day",
            description = "Gir is one of the principal zebu breeds of cattle from India. Highly prized for milk production with high fat content. Known for its distinctive bulging forehead and drooping ears.",
            characteristics = listOf("Prominent bulging forehead", "Long drooping ears", "Reddish-brown coat with white spots", "Heavy dewlap"),
            uses = listOf("Milk Production", "Breeding Program", "Export"),
            conservationStatus = "Stable",
            imageDescription = "🐄 Reddish-brown with dome forehead"
        ),
        BreedInfo(
            name = "Sahiwal",
            category = "Cattle",
            originState = "Punjab (Pakistan-India border)",
            milkYield = "12-15 liters/day",
            description = "Sahiwal is considered the best dairy breed in South Asia. Known for heat tolerance and tick resistance. One of the highest milk-producing zebu breeds.",
            characteristics = listOf("Reddish-brown or dull red coat", "Massive body", "Short thick horns or hornless", "Heavy milk veins"),
            uses = listOf("High milk production", "Crossbreeding", "Draft work"),
            conservationStatus = "Vulnerable",
            imageDescription = "🐄 Reddish-dun, heavy milker"
        ),
        BreedInfo(
            name = "Ongole",
            category = "Cattle",
            originState = "Andhra Pradesh",
            milkYield = "4-6 liters/day",
            description = "Ongole is a well-known draft breed, also used for beef in some countries. Named after Ongole region in Andhra Pradesh. Exported worldwide especially to Brazil (as Nelore).",
            characteristics = listOf("White to grey coat", "Large hump", "Loose pendulous sheath", "Very muscular"),
            uses = listOf("Draft/Ploughing", "Beef (export)", "Crossbreeding"),
            conservationStatus = "Stable",
            imageDescription = "🐄 White/grey, large muscular body"
        ),
        BreedInfo(
            name = "Red Sindhi",
            category = "Cattle",
            originState = "Sindh (origin), distributed across India",
            milkYield = "8-12 liters/day",
            description = "Red Sindhi is a dairy breed known for deep red color and milk production. Highly heat tolerant and disease resistant. Popular in Kerala and Tamil Nadu.",
            characteristics = listOf("Deep red/brown color", "Medium build", "Moderate dewlap", "Tick resistant"),
            uses = listOf("Dairy farming", "Crossbreeding dairy cows"),
            conservationStatus = "Stable",
            imageDescription = "🐄 Deep red, medium build"
        ),
        BreedInfo(
            name = "Hariana",
            category = "Cattle",
            originState = "Haryana",
            milkYield = "6-10 liters/day",
            description = "Hariana is a dual-purpose breed from Haryana, used for both draft and dairy. Known for endurance and ability to work in harsh conditions. White to grey in color.",
            characteristics = listOf("White to light grey", "Long face", "Thin horns pointing outward", "Active temperament"),
            uses = listOf("Dairy", "Draft", "Agricultural work"),
            conservationStatus = "Stable",
            imageDescription = "🐄 White/grey, long face"
        ),
        BreedInfo(
            name = "Tharparkar",
            category = "Cattle",
            originState = "Rajasthan",
            milkYield = "8-12 liters/day",
            description = "Tharparkar is an excellent dual-purpose breed from the Thar desert of Rajasthan. Extremely drought-tolerant with good milk yield even in harsh conditions. White to grey colored.",
            characteristics = listOf("White to grey color", "Lyre-shaped horns", "Deep chest", "Hardy desert breed"),
            uses = listOf("Dairy", "Draft", "Desert agriculture"),
            conservationStatus = "Stable",
            imageDescription = "🐄 White, desert adapted"
        ),
        BreedInfo(
            name = "Kankrej",
            category = "Cattle",
            originState = "Gujarat & Rajasthan",
            milkYield = "5-8 liters/day",
            description = "Kankrej is one of the heaviest Indian zebu breeds. Used for heavy draft work and also for dairy. Known as 'Wadad' in Gujarat. Exported to Brazil as Guzerat breed.",
            characteristics = listOf("Silver-grey to iron-grey color", "Lyre-shaped upswept horns", "Very heavy build", "Pendulous ears"),
            uses = listOf("Heavy draft", "Dairy", "Export/Crossbreeding"),
            conservationStatus = "Stable",
            imageDescription = "🐄 Silver-grey, heavy build"
        ),
        BreedInfo(
            name = "Deoni",
            category = "Cattle",
            originState = "Maharashtra & Karnataka",
            milkYield = "6-8 liters/day",
            description = "Deoni is a dual-purpose breed found in Marathwada region. White with black or red spots on the neck and hindquarters. A good working and moderate dairy breed.",
            characteristics = listOf("White with black/red spots", "Medium build", "Short horns", "Compact body"),
            uses = listOf("Draft", "Dairy"),
            conservationStatus = "Endangered",
            imageDescription = "🐄 White with colored spots"
        ),
        // BUFFALO BREEDS
        BreedInfo(
            name = "Murrah",
            category = "Buffalo",
            originState = "Haryana",
            milkYield = "15-25 liters/day",
            description = "Murrah is the world's highest milk-producing buffalo breed. Origin is Rohtak and Hisar districts of Haryana. Jet black with tightly curled horns. The breed is globally recognized and exported worldwide.",
            characteristics = listOf("Jet black color", "Tightly coiled curled horns", "Heavy built", "Prominent milk veins"),
            uses = listOf("High milk production", "Export worldwide", "Crossbreeding"),
            conservationStatus = "Stable",
            imageDescription = "🐃 Jet black, curled horns"
        ),
        BreedInfo(
            name = "Surti",
            category = "Buffalo",
            originState = "Gujarat",
            milkYield = "10-13 liters/day",
            description = "Surti buffalo is a medium-sized breed from the Charotar region of Gujarat. Rust-brown to silver-grey color. Known for high fat content in milk (7-12%). Named after Surat city.",
            characteristics = listOf("Rust to silver-grey body", "Medium build", "Sickle-shaped horns", "High fat milk"),
            uses = listOf("Dairy (high fat milk)", "Ghee production"),
            conservationStatus = "Vulnerable",
            imageDescription = "🐃 Rust-grey, sickle horns"
        ),
        BreedInfo(
            name = "Jaffarabadi",
            category = "Buffalo",
            originState = "Gujarat",
            milkYield = "12-18 liters/day",
            description = "Jaffarabadi is the heaviest buffalo breed in India. Origin is Gir forest area of Gujarat. Jet black color with massive body. Horns are heavy, curved and drooping on the neck.",
            characteristics = listOf("Jet black", "Massive body (600-900 kg)", "Heavy neck-drooping horns", "Largest Indian buffalo"),
            uses = listOf("Dairy", "Heavy draft"),
            conservationStatus = "Endangered",
            imageDescription = "🐃 Jet black, massive, neck horns"
        ),
        BreedInfo(
            name = "Nili-Ravi",
            category = "Buffalo",
            originState = "Punjab",
            milkYield = "15-18 liters/day",
            description = "Nili-Ravi is a high milk-producing buffalo from Punjab region (India-Pakistan border area). Black with white markings on face, legs and tail switch. Named after Nili (Sutlej) and Ravi rivers.",
            characteristics = listOf("Black with white markings", "Wall-eye common", "Tightly curved horns", "Heavy build"),
            uses = listOf("High milk production", "Crossbreeding"),
            conservationStatus = "Stable",
            imageDescription = "🐃 Black with white spots"
        ),
        BreedInfo(
            name = "Bhadawari",
            category = "Buffalo",
            originState = "Uttar Pradesh & Madhya Pradesh",
            milkYield = "5-7 liters/day",
            description = "Bhadawari buffalo is known for the highest fat content in milk (12-14%) among all buffalo breeds. Copper-brown to brown-ash colored. Suited for hot, dry conditions. From Agra and Bhind districts.",
            characteristics = listOf("Copper-brown color", "White chevron on neck", "Highest fat milk (12-14%)", "Drought resistant"),
            uses = listOf("High-fat milk & Ghee", "Butter production"),
            conservationStatus = "Endangered",
            imageDescription = "🐃 Copper-brown, white chevron"
        ),
        BreedInfo(
            name = "Pandharpuri",
            category = "Buffalo",
            originState = "Maharashtra",
            milkYield = "8-12 liters/day",
            description = "Pandharpuri buffalo is found in Solapur and Pandharpur areas of Maharashtra. Black in color with distinctive very long, flat and curved horns. Well adapted to the dry Deccan plateau.",
            characteristics = listOf("Black color", "Very long flat curved horns", "Medium build", "Dry climate adapted"),
            uses = listOf("Dairy", "Draft"),
            conservationStatus = "Endangered",
            imageDescription = "🐃 Black, very long flat horns"
        ),
        BreedInfo(
            name = "Mehsana",
            category = "Buffalo",
            originState = "Gujarat",
            milkYield = "12-15 liters/day",
            description = "Mehsana is a crossbred type between Murrah and Surti buffaloes. Developed in Mehsana district of Gujarat. Slightly lighter than Murrah but with comparable milk yield. Black or brown-black in color.",
            characteristics = listOf("Black to brownish-black", "Medium to heavy build", "Somewhat curved horns", "Good milk yield"),
            uses = listOf("Commercial dairy farming"),
            conservationStatus = "Stable",
            imageDescription = "🐃 Brown-black, medium horns"
        )
    )

    fun getBreedByName(name: String): BreedInfo? {
        return allBreeds.find { it.name.equals(name, ignoreCase = true) }
    }

    fun getCattleBreeds() = allBreeds.filter { it.category == "Cattle" }
    fun getBuffaloBreeds() = allBreeds.filter { it.category == "Buffalo" }
    fun getEndangeredBreeds() = allBreeds.filter { it.conservationStatus == "Endangered" }
}