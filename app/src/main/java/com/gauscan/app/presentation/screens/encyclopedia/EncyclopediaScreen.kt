package com.gauscan.app.presentation.screens.encyclopedia

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gauscan.app.data.model.BreedInfo
import com.gauscan.app.presentation.navigation.Screen
import com.gauscan.app.presentation.screens.home.BottomNavigationBar

// ═══════════════════════════════════════════════════════════════
//  WORLD CATTLE & BUFFALO BREED DATABASE
//  200+ breeds from every continent
// ═══════════════════════════════════════════════════════════════
object AllBreeds {

    // BAAD MEIN — lazy use karo:
    val list: List<BreedInfo> by lazy {
        indianCattle + indianBuffalo +
                europeanCattle + africanCattle + americanCattle +
                asianPacificCattle
    }

    // ── INDIAN CATTLE (35 breeds) ──────────────────────────────
    private val indianCattle = listOf(
        BreedInfo("Gir", "Cattle", "Gujarat, India", "10–16 L/day",
            "One of India's finest dairy breeds with a distinctive bulging forehead and drooping ears. Prized for A2 milk rich in beta-casein protein. Exported globally; forms base of Brazil's Nelore breed.",
            listOf("Prominent bulging forehead", "Long drooping ears", "Reddish-brown with white patches", "Heavy pendulous dewlap"),
            listOf("A2 Milk Production", "Export Breeding", "Crossbreeding"), "Stable", ""),

        BreedInfo("Sahiwal", "Cattle", "Punjab, India–Pakistan", "12–15 L/day",
            "Best dairy zebu breed in South Asia. Extremely heat-tolerant and tick-resistant. Milk has 4–5% fat. Docile temperament ideal for small farmers.",
            listOf("Reddish-brown to dull red", "Massive rounded body", "Short thick neck", "Prominent milk veins"),
            listOf("High milk production", "Crossbreeding", "Small farm dairy"), "Vulnerable", ""),

        BreedInfo("Ongole", "Cattle", "Andhra Pradesh, India", "4–6 L/day",
            "One of the largest Indian zebu breeds. Exported as 'Nelore' to Brazil where it dominates beef production. White to grey coat with large hump.",
            listOf("White to grey coat", "Massive hump", "Loose pendulous sheath", "Muscular build"),
            listOf("Draft work", "Export beef breeding", "Crossbreeding"), "Stable", ""),

        BreedInfo("Red Sindhi", "Cattle", "Sindh region, India", "8–12 L/day",
            "Versatile dairy breed with deep red coat. Highly resistant to tropical diseases and ticks. Popular in Kerala and Tamil Nadu for consistent milk yield even under poor feeding conditions.",
            listOf("Deep red coat", "Medium build", "Moderate dewlap", "Tick resistant"),
            listOf("Dairy farming", "Crossbreeding"), "Stable", ""),

        BreedInfo("Hariana", "Cattle", "Haryana, India", "6–10 L/day",
            "Dual-purpose breed from Indo-Gangetic plains. Known for endurance in field work and steady milk production. White to light grey with a long face profile.",
            listOf("White to grey coat", "Long narrow face", "Thin outward-pointing horns", "Compact body"),
            listOf("Dairy", "Draft", "Ploughing"), "Stable", ""),

        BreedInfo("Tharparkar", "Cattle", "Rajasthan (Thar Desert), India", "8–12 L/day",
            "Remarkable desert breed producing high milk yield in harsh arid conditions. One of India's best dual-purpose breeds. Compact and hardy constitution.",
            listOf("White to grey", "Lyre-shaped horns", "Deep chest", "Exceptional drought tolerance"),
            listOf("Desert dairy", "Draft work", "Dry agriculture"), "Stable", ""),

        BreedInfo("Kankrej", "Cattle", "Gujarat & Rajasthan, India", "5–8 L/day",
            "One of India's heaviest zebu breeds, known as 'Wadad' in Gujarat. Exported to Brazil as Guzerat. Used for heavy agricultural draft.",
            listOf("Silver-grey to iron-grey", "Lyre-shaped upswept horns", "Very heavy build", "Large pendulous ears"),
            listOf("Heavy draft", "Export breeding", "Dairy"), "Stable", ""),

        BreedInfo("Deoni", "Cattle", "Maharashtra & Karnataka, India", "6–8 L/day",
            "Dual-purpose breed from Marathwada. White body with black or red patches on neck and hindquarters.",
            listOf("White with black/red patches", "Medium build", "Short rounded horns", "Compact body"),
            listOf("Draft", "Dairy"), "Endangered", ""),

        BreedInfo("Rathi", "Cattle", "Rajasthan, India", "8–10 L/day",
            "Best dairy breed of Rajasthan from Bikaner and Ganganagar districts. Brown with white spots. Adapted to semi-arid conditions.",
            listOf("Brown with white spots", "Medium size", "Curved horns", "Semi-arid adapted"),
            listOf("Dairy", "Small farm use"), "Stable", ""),

        BreedInfo("Hallikar", "Cattle", "Karnataka, India", "3–5 L/day",
            "Famous Karnataka draft breed used in Kambala racing. Sleek athletic build. Grey-white coat with long tapering horns.",
            listOf("Grey-white sleek body", "Long tapering horns", "Athletic build", "Highly agile"),
            listOf("Heavy draft", "Racing", "Cultural ceremonies"), "Vulnerable", ""),

        BreedInfo("Amrit Mahal", "Cattle", "Karnataka, India", "2–4 L/day",
            "Historic breed maintained by Mysore royal family for war and transport. One of India's fastest draft breeds. Grey with dark dorsal stripe.",
            listOf("Dark grey with dorsal stripe", "Long upswept horns", "Very athletic", "Exceptional stamina"),
            listOf("Heavy draft", "Heritage preservation"), "Endangered", ""),

        BreedInfo("Khillari", "Cattle", "Maharashtra, India", "3–5 L/day",
            "Compact powerful draft breed from Deccan plateau. Known for speed and endurance. White or grey with strong hindquarters.",
            listOf("White to grey", "Short sturdy horns", "Compact muscular body", "Strong hindquarters"),
            listOf("Draft", "Light farm work"), "Vulnerable", ""),

        BreedInfo("Kangayam", "Cattle", "Tamil Nadu, India", "4–6 L/day",
            "Hardy draft breed from Kongu Nadu. Used in Jallikattu. Grey-white with black patches on face and switch.",
            listOf("Grey-white with black markings", "Short sharp horns", "Very agile", "Strong hindquarters"),
            listOf("Draft", "Jallikattu", "Cultural events"), "Vulnerable", ""),

        BreedInfo("Malnad Gidda", "Cattle", "Karnataka (Western Ghats), India", "2–3 L/day",
            "Miniature breed from Western Ghats. Highly disease-resistant and adapted to wet hilly terrain. Considered sacred locally.",
            listOf("Small stature", "Black or brown coat", "Hill terrain adapted", "Disease resistant"),
            listOf("Small farm dairy", "Cultural use"), "Endangered", ""),

        BreedInfo("Vechur", "Cattle", "Kerala, India", "3–5 L/day",
            "Guinness World Record holder as world's smallest cattle breed. Average height only 87 cm. Milk is highly nutritious with medicinal value.",
            listOf("World's smallest cattle", "Brown or black", "87 cm average height", "High-fat A2 milk"),
            listOf("Specialty dairy", "Conservation breeding"), "Endangered", ""),

        BreedInfo("Punganur", "Cattle", "Andhra Pradesh, India", "2–5 L/day",
            "Critically endangered dwarf breed from Chittoor. Fewer than 100 pure individuals remain. Milk fat up to 8%. A living national heritage.",
            listOf("Extremely small stature", "White to grey", "Short legs", "8%+ fat milk"),
            listOf("Heritage preservation", "Specialty dairy"), "Endangered", ""),

        BreedInfo("Nimari", "Cattle", "Madhya Pradesh, India", "4–6 L/day",
            "Draft breed from Narmada valley. Cross between Gir and Khillari. Red to brownish-red coat. Used in black soil agricultural regions.",
            listOf("Red to brownish-red", "Medium build", "Curved horns", "Black soil adapted"),
            listOf("Draft", "Agricultural use"), "Stable", ""),

        BreedInfo("Gaolao", "Cattle", "Maharashtra & MP, India", "3–5 L/day",
            "Dual-purpose breed from Vidarbha. White to grey-white with black muzzle, hooves, and tail switch. Docile nature.",
            listOf("White with black muzzle", "Medium build", "Black hooves and tail switch", "Docile"),
            listOf("Dairy", "Draft"), "Stable", ""),

        BreedInfo("Krishna Valley", "Cattle", "Karnataka & Andhra Pradesh, India", "4–6 L/day",
            "Large powerful draft breed developed in Krishna river valley. Cross of local breeds with European draft cattle. Grey to white.",
            listOf("Large frame", "Grey to white", "Massive hump", "Very powerful"),
            listOf("Heavy draft", "Canal irrigation work"), "Stable", ""),

        BreedInfo("Bachaur", "Cattle", "Bihar, India", "3–5 L/day",
            "Draft breed from Sitamarhi and Sheohar districts of Bihar. Medium-sized with a compact body. White to grey with a muscular build suited for agricultural work.",
            listOf("White to grey", "Compact muscular body", "Short thick neck", "Medium horns"),
            listOf("Draft", "Agricultural use"), "Stable", ""),

        BreedInfo("Bargur", "Cattle", "Tamil Nadu, India", "3–5 L/day",
            "Hill breed from Bargur forests of Erode district. Brown with white patches and a distinctive white patch on forehead. Fast and agile.",
            listOf("Brown with white patches", "White forehead patch", "Very agile", "Hill adapted"),
            listOf("Draft", "Racing", "Hill agriculture"), "Endangered", ""),

        BreedInfo("Dangi", "Cattle", "Maharashtra, India", "3–5 L/day",
            "Breed from Dangs region of Maharashtra and Gujarat. Black or red with white speckles. Well-adapted to hilly, forested terrain and heavy rainfall.",
            listOf("Black/red with white speckles", "Medium build", "Curved short horns", "Forest terrain adapted"),
            listOf("Draft", "Hill agriculture"), "Stable", ""),

        BreedInfo("Gangatiri", "Cattle", "Uttar Pradesh & Bihar, India", "5–8 L/day",
            "Dual-purpose breed from Gangetic plains. White to grey with a broad forehead. Good milk yield and suitable for light draft work.",
            listOf("White to grey", "Broad forehead", "Medium build", "Gentle temperament"),
            listOf("Dairy", "Draft"), "Stable", ""),

        BreedInfo("Mewati", "Cattle", "Rajasthan & Haryana, India", "4–7 L/day",
            "Large heavy draft breed from Mewat region. White to grey with a large hump. Known for pulling heavy loads over long distances.",
            listOf("White to grey", "Very large hump", "Heavy muscular body", "Long legs"),
            listOf("Heavy draft", "Agricultural use"), "Stable", ""),

        BreedInfo("Nagori", "Cattle", "Rajasthan, India", "3–5 L/day",
            "Active draft breed from Nagaur district of Rajasthan. Known as one of the best work breeds. White to light grey, compact, and very fast.",
            listOf("White to light grey", "Compact body", "Very fast walker", "Strong legs"),
            listOf("Fast draft work", "Transportation"), "Stable", ""),

        BreedInfo("Ponwar", "Cattle", "Uttar Pradesh, India", "3–4 L/day",
            "Draft breed from Pilibhit and Lakhimpur districts. White with grey spots. Medium-sized with a compact frame suitable for rice field cultivation.",
            listOf("White with grey spots", "Medium compact frame", "Short horns", "Rice field adapted"),
            listOf("Draft", "Rice cultivation"), "Endangered", ""),

        BreedInfo("Siri", "Cattle", "Darjeeling, West Bengal, India", "4–6 L/day",
            "Hill breed from Darjeeling, Sikkim, and parts of Bhutan. Black or brown with white patches. Adapted to cold mountainous terrain. Dual-purpose.",
            listOf("Black/brown with white patches", "Small to medium size", "Cold climate adapted", "Sure-footed"),
            listOf("Dairy", "Light draft", "Hill transport"), "Stable", ""),

        BreedInfo("Umblachery", "Cattle", "Tamil Nadu, India", "3–5 L/day",
            "Draft breed from Thanjavur delta region. Grey with black and white markings on the face. Used for wet land cultivation and known for endurance in paddy fields.",
            listOf("Grey with facial markings", "Medium build", "Paddy field adapted", "Endurance worker"),
            listOf("Paddy cultivation", "Draft"), "Endangered", ""),

        BreedInfo("Pulikulum", "Cattle", "Tamil Nadu, India", "2–4 L/day",
            "Compact draft breed from Madurai, Dindigul regions. Grey-white with black switch. Known for agility and used in traditional Jallikattu events.",
            listOf("Grey-white", "Black tail switch", "Very compact body", "Extremely agile"),
            listOf("Draft", "Jallikattu sport"), "Vulnerable", ""),

        BreedInfo("Motu", "Cattle", "Odisha, India", "2–4 L/day",
            "Small hill breed from Motu region of Odisha. Brown to black with a compact body. Adapted to hilly terrain and sparse fodder conditions.",
            listOf("Brown to black", "Small compact body", "Short horns", "Hill terrain adapted"),
            listOf("Light draft", "Hill agriculture"), "Endangered", ""),

        BreedInfo("Lohani", "Cattle", "Himachal Pradesh & Pakistan border, India", "3–5 L/day",
            "Mountain breed from Balochistan-Himachal border region. Compact and hardy with thick coat for cold weather. Dual-purpose.",
            listOf("Brown or dun colored", "Thick winter coat", "Compact hardy body", "Cold climate adapted"),
            listOf("Dairy", "Light draft", "Mountain transport"), "Stable", ""),

        BreedInfo("Ladakhi", "Cattle", "Ladakh, India", "2–4 L/day",
            "Himalayan high-altitude breed adapted to extreme cold and low oxygen. Small stature but incredibly hardy. Related to Tibetan cattle.",
            listOf("Small stature", "Thick double coat", "High altitude adapted", "Very hardy"),
            listOf("Dairy", "Pack transport"), "Stable", ""),

        BreedInfo("Binjharpuri", "Cattle", "Odisha, India", "4–6 L/day",
            "Large dual-purpose breed from Binjharpur, Odisha. White with grey tones. Known for large frame and good draught capacity.",
            listOf("White to grey", "Large heavy frame", "Long horns", "Dual purpose"),
            listOf("Draft", "Dairy"), "Stable", ""),

        BreedInfo("Kosali", "Cattle", "Chhattisgarh, India", "3–5 L/day",
            "Native breed of Chhattisgarh from Bilaspur region. Medium-sized, compact and suited for rice cultivation in the Chhattisgarh plains.",
            listOf("White to grey", "Medium compact body", "Short to medium horns", "Rice plains adapted"),
            listOf("Draft", "Rice cultivation"), "Stable", ""),

        BreedInfo("Kenwar", "Cattle", "Uttar Pradesh, India", "3–5 L/day",
            "Draft breed from Banda and Hamirpur districts. White with grey tones. Known for hard work in rocky Bundelkhand terrain.",
            listOf("White to grey", "Medium build", "Hard hooves", "Rocky terrain adapted"),
            listOf("Draft", "Agricultural use"), "Stable", ""),

        BreedInfo("Badri", "Cattle", "Uttarakhand, India", "3–5 L/day",
            "Small hill breed from Uttarakhand Himalayas. Black, brown, or mixed colors. Highly adapted to steep mountain terrain and cold climates.",
            listOf("Black/brown/mixed", "Small stature", "Mountain adapted", "Cold resistant"),
            listOf("Dairy", "Hill transport", "Light draft"), "Stable", "")
    )

    // ── INDIAN BUFFALO (15 breeds) ─────────────────────────────
    private val indianBuffalo = listOf(
        BreedInfo("Murrah", "Buffalo", "Haryana, India", "15–25 L/day",
            "World's highest milk-producing buffalo. Originated in Rohtak and Hisar. Jet black with tightly curled horns. Exported worldwide for breed improvement.",
            listOf("Jet black", "Tightly coiled horns", "Heavy build 600–900 kg", "Prominent milk veins"),
            listOf("Highest milk production", "Global export", "Crossbreeding"), "Stable", ""),

        BreedInfo("Surti", "Buffalo", "Gujarat, India", "10–13 L/day",
            "Medium-sized buffalo from Charotar region. Rust-brown to silver-grey. Famous for 7–12% milk fat. Named after Surat. Ideal for ghee production.",
            listOf("Rust to silver-grey", "Sickle-shaped horns", "Medium build", "High fat milk"),
            listOf("High fat dairy", "Ghee production"), "Vulnerable", ""),

        BreedInfo("Jaffarabadi", "Buffalo", "Gujarat, India", "12–18 L/day",
            "Heaviest buffalo in India from Gir forest area. Jet black, up to 900 kg. Massive neck-drooping horns. Second highest milk yield in India.",
            listOf("Jet black", "Up to 900 kg", "Neck-drooping horns", "Largest Indian buffalo"),
            listOf("Dairy", "Heavy draft"), "Endangered", ""),

        BreedInfo("Nili-Ravi", "Buffalo", "Punjab, India", "15–18 L/day",
            "High milk-producing buffalo from Punjab belt. Named after Nili (Sutlej) and Ravi rivers. Black with white markings. Wall-eye (blue iris) common.",
            listOf("Black with white markings", "Wall-eye common", "Tightly curved horns", "Heavy build"),
            listOf("Commercial dairy", "Crossbreeding"), "Stable", ""),

        BreedInfo("Bhadawari", "Buffalo", "Uttar Pradesh & MP, India", "5–7 L/day",
            "Highest fat content milk (12–14%) of any buffalo worldwide. Copper-brown color. From Agra and Bhind districts. Drought resistant.",
            listOf("Copper-brown coat", "White chevron on neck", "12–14% fat milk", "Drought resistant"),
            listOf("High-fat ghee production", "Butter production"), "Endangered", ""),

        BreedInfo("Pandharpuri", "Buffalo", "Maharashtra, India", "8–12 L/day",
            "Distinctive buffalo from Solapur with very long flat curved horns. Black. Well-adapted to dry Deccan plateau.",
            listOf("Jet black", "Very long flat curved horns", "Medium build", "Deccan plateau adapted"),
            listOf("Dairy", "Draft"), "Endangered", ""),

        BreedInfo("Mehsana", "Buffalo", "Gujarat, India", "12–15 L/day",
            "Cross of Murrah and Surti developed in Mehsana district. Black to brownish-black. Widely used in commercial dairy farms of Gujarat.",
            listOf("Black to brownish-black", "Medium-heavy build", "Curved horns", "High yield"),
            listOf("Commercial dairy"), "Stable", ""),

        BreedInfo("Nagpuri", "Buffalo", "Maharashtra, India", "7–10 L/day",
            "Also called Ellichpuri. From Nagpur and Amravati. Black with white markings. Tightly-curved flat horns. Adapted to Vidarbha rain-shadow areas.",
            listOf("Black with white markings", "Tightly-curved flat horns", "Medium build", "Rain-shadow adapted"),
            listOf("Dairy", "Draft"), "Stable", ""),

        BreedInfo("Toda", "Buffalo", "Tamil Nadu (Nilgiris), India", "1–2 L/day",
            "Sacred buffalo of Toda tribe in Nilgiri hills. Used only for religious dairy. Critically rare. Milk used in traditional rituals.",
            listOf("Black or brown", "Large curved horns", "Hill adapted", "Sacred breed"),
            listOf("Cultural dairy", "Heritage conservation"), "Endangered", ""),

        BreedInfo("Marathwadi", "Buffalo", "Maharashtra, India", "6–8 L/day",
            "Medium-sized buffalo from Marathwada. Black with curved flat horns. Hardy for agricultural areas. Used for both milk and field work.",
            listOf("Black coat", "Curved flat horns", "Medium build", "Drought tolerant"),
            listOf("Dairy", "Agricultural draft"), "Stable", ""),

        BreedInfo("Chhattisgarhi", "Buffalo", "Chhattisgarh, India", "4–6 L/day",
            "Local buffalo of Chhattisgarh plains. Black with medium curved horns. Well-adapted to humid tropical conditions of central India.",
            listOf("Black coat", "Medium curved horns", "Medium build", "Tropical adapted"),
            listOf("Dairy", "Light draft"), "Stable", ""),

        BreedInfo("Kalahandi", "Buffalo", "Odisha, India", "5–7 L/day",
            "Buffalo breed from Kalahandi district of Odisha. Black with straight or slightly curved horns. Good for milk and draft in tribal farming systems.",
            listOf("Black coat", "Slightly curved horns", "Medium build", "Tribal farming adapted"),
            listOf("Dairy", "Draft"), "Stable", ""),

        BreedInfo("Tarai", "Buffalo", "Uttar Pradesh & Uttarakhand, India", "6–9 L/day",
            "Buffalo from Terai belt of Himalayan foothills. Black with moderate curved horns. Adapted to humid sub-Himalayan conditions.",
            listOf("Black coat", "Moderate curved horns", "Heavy build", "Sub-Himalayan adapted"),
            listOf("Dairy", "Draft"), "Stable", ""),

        BreedInfo("Gojri", "Buffalo", "Himachal Pradesh & J&K, India", "6–8 L/day",
            "Migratory buffalo kept by Gujjar nomads. Moves between Himalayan pastures seasonally. Black or brown. Hardy under varied terrain.",
            listOf("Black or brown", "Curved horns", "Nomadic adapted", "Very hardy"),
            listOf("Dairy", "Nomadic farming"), "Stable", ""),

        BreedInfo("Sambalpur", "Buffalo", "Odisha, India", "5–8 L/day",
            "Buffalo from western Odisha. Black with compact body. Used for both milk and agricultural work in the Mahanadi basin region.",
            listOf("Black coat", "Short curved horns", "Compact body", "River basin adapted"),
            listOf("Dairy", "Draft"), "Stable", "")
    )

    // ── EUROPEAN CATTLE (50+ breeds) ──────────────────────────
    private val europeanCattle = listOf(
        BreedInfo("Holstein Friesian", "Cattle", "Netherlands / Germany", "25–40 L/day",
            "World's highest milk-producing cattle breed. Black and white patches. Dominant in commercial dairy worldwide. Highly efficient feed conversion.",
            listOf("Black and white patches", "Large frame 600–700 kg", "Very prominent udder", "Long body"),
            listOf("Commercial dairy", "Global milk production"), "Stable", ""),

        BreedInfo("Jersey", "Cattle", "Jersey Island, UK", "15–25 L/day",
            "Small dairy breed with highest milk fat (5–6%) among mainstream dairy breeds. Fawn to dark brown. Extremely efficient and adaptable.",
            listOf("Fawn to dark brown", "Small frame 400–500 kg", "Large prominent eyes", "Highest fat milk"),
            listOf("Dairy", "Butter production", "Small farm dairy"), "Stable", ""),

        BreedInfo("Ayrshire", "Cattle", "Scotland, UK", "15–20 L/day",
            "Medium dairy breed from Ayrshire, Scotland. Red and white patches. Good quality milk with well-balanced fat and protein. Hardy and long-lived.",
            listOf("Red and white patches", "Medium frame", "Strong legs", "Well-attached udder"),
            listOf("Dairy", "Crossbreeding"), "Stable", ""),

        BreedInfo("Brown Swiss", "Cattle", "Switzerland", "18–25 L/day",
            "Ancient breed from Swiss Alps. Brown coat ranging from light to dark. High milk protein content ideal for cheese making. Extremely docile.",
            listOf("Light to dark brown coat", "Large heavy frame", "Short strong horns", "High protein milk"),
            listOf("Dairy", "Cheese production", "Draft"), "Stable", ""),

        BreedInfo("Guernsey", "Cattle", "Guernsey Island, UK", "14–20 L/day",
            "Fawn and white dairy breed from Guernsey Island. Milk is naturally golden due to high beta-carotene content. Rich flavor and high butter fat.",
            listOf("Fawn and white", "Medium frame", "Golden milk", "4.5–5% fat milk"),
            listOf("Specialty dairy", "Butter production"), "Stable", ""),

        BreedInfo("Hereford", "Cattle", "Herefordshire, England", "N/A (Beef)",
            "One of the world's most popular beef breeds. Red with white face, crest, and underparts. Docile, hardy, and excellent foragers. Found on every continent.",
            listOf("Red with white face", "Polled or horned", "Compact muscular body", "Excellent forager"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Aberdeen Angus", "Cattle", "Scotland, UK", "N/A (Beef)",
            "Premium beef breed from northeast Scotland. Solid black, naturally polled (hornless). Produces highly marbled beef. World's most influential beef breed.",
            listOf("Solid black coat", "Naturally polled", "Compact deep body", "Fine marbled beef"),
            listOf("Premium beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Simmental", "Cattle", "Switzerland", "18–25 L/day",
            "One of the world's oldest and most widely distributed breeds. Red/yellow and white. Dual-purpose for both high milk yield and excellent beef.",
            listOf("Red-yellow and white", "Very large frame", "Dual purpose", "Heavy muscled"),
            listOf("Dual purpose dairy/beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Charolais", "Cattle", "Charolles, France", "N/A (Beef)",
            "White to cream-colored French beef breed. Heavily muscled with rapid growth. Second most popular beef breed globally after Angus.",
            listOf("White to cream coat", "Massive muscle development", "Large frame 700–1000 kg", "Fast growth"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Limousin", "Cattle", "Limousin, France", "N/A (Beef)",
            "Lean, efficient French beef breed. Red-golden coat. Known for high cutout percentage and low fat in beef. Excellent feed efficiency.",
            listOf("Red to golden coat", "Fine bone structure", "Lean muscular body", "High cutout %"),
            listOf("Lean beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Blonde d'Aquitaine", "Cattle", "Aquitaine, France", "N/A (Beef)",
            "Large French beef breed from Aquitaine region. Blonde to wheat colored. Very muscular with rapid growth rate. Third largest beef breed in France.",
            listOf("Blonde to wheat color", "Large heavily muscled", "Good temperament", "Fast growing"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Normande", "Cattle", "Normandy, France", "18–22 L/day",
            "Dual-purpose breed from Normandy. Distinctive tricolor coat (red, brown, white). Milk excellent for Camembert and Livarot cheese production.",
            listOf("Tricolor coat", "Distinctive spectacled eyes", "Medium-heavy frame", "High casein milk"),
            listOf("Cheese dairy", "Beef", "Dual purpose"), "Stable", ""),

        BreedInfo("Salers", "Cattle", "Auvergne, France", "6–12 L/day",
            "Ancient breed from volcanic Massif Central. Mahogany red with lyre-shaped horns. Excellent mothers with strong calving ease. Hardy in tough terrain.",
            listOf("Mahogany red coat", "Lyre-shaped horns", "Excellent mothering", "Very hardy"),
            listOf("Dual purpose", "Suckling beef"), "Stable", ""),

        BreedInfo("Montbeliarde", "Cattle", "Franche-Comté, France", "20–28 L/day",
            "Red pied breed from Jura Mountains. High milk protein ideal for Comté cheese. Second most common dairy breed in France.",
            listOf("Red and white pied", "Large strong frame", "High protein milk", "Excellent feet"),
            listOf("Dairy", "Cheese production"), "Stable", ""),

        BreedInfo("Belgian Blue", "Cattle", "Belgium", "N/A (Beef)",
            "Extreme double-muscled breed from Belgium. Blue-white coat with exceptional muscle hypertrophy. One of the most muscular cattle breeds in the world.",
            listOf("Blue-white coat", "Double muscle hypertrophy", "Very heavy 700–1100 kg", "Exceptional lean meat"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Piedmontese", "Cattle", "Piedmont, Italy", "N/A (Beef)",
            "Double-muscled Italian breed with natural mutation for lean, tender beef. White to pale grey. One of the leanest beef breeds with exceptional tenderness.",
            listOf("White to pale grey", "Double muscling", "Very lean tender beef", "Fine bone"),
            listOf("Specialty lean beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Chianina", "Cattle", "Tuscany, Italy", "N/A (Beef)",
            "World's largest and oldest cattle breed from Chiana Valley, Tuscany. Porcelain white. Can reach 1800 kg. Source of famous Bistecca alla Fiorentina.",
            listOf("Porcelain white coat", "World's largest breed", "Up to 1800 kg", "Very long legs"),
            listOf("Beef production", "Cultural heritage"), "Stable", ""),

        BreedInfo("Marchigiana", "Cattle", "Marche, Italy", "N/A (Beef)",
            "Italian white beef breed related to Chianina. Large frame with heavy muscling. Grey-white coat. Popular in crossbreeding programs worldwide.",
            listOf("Grey-white coat", "Large heavily muscled", "Long body", "Good growth rate"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Kerry", "Cattle", "County Kerry, Ireland", "8–12 L/day",
            "Ancient Irish dairy breed, one of Europe's oldest. Small black cattle. Hardy on poor pasture. Milk high in casein content for cheese.",
            listOf("Solid black coat", "Small frame 350 kg", "Long sweeping horns", "Pasture efficient"),
            listOf("Specialty dairy", "Conservation"), "Vulnerable", ""),

        BreedInfo("Dexter", "Cattle", "Ireland", "8–12 L/day",
            "Smallest European cattle breed from Ireland. Black, red, or dun. Dual-purpose for small farms. Very efficient on minimal land.",
            listOf("Small stature 300 kg", "Black, red, or dun", "Short legs", "Very efficient"),
            listOf("Small farm dairy/beef", "Hobby farming"), "Stable", ""),

        BreedInfo("Highland", "Cattle", "Scottish Highlands, UK", "4–8 L/day",
            "Ancient shaggy-coated breed from Scottish Highlands. Long curved horns and double coat for cold weather. Hardy on rough terrain. Lean marbled beef.",
            listOf("Long wavy double coat", "Large curved horns", "All colors", "Extreme cold hardy"),
            listOf("Beef", "Heritage tourism", "Conservation grazing"), "Stable", ""),

        BreedInfo("Galloway", "Cattle", "Galloway, Scotland", "N/A (Beef)",
            "Ancient Scottish beef breed, naturally polled. Black, dun, or belted. Thick double coat. Hardy on rough, cold pastures. Excellent quality beef.",
            listOf("Black, dun, or belted", "Naturally polled", "Thick double coat", "Cold hardy"),
            listOf("Beef", "Hardy grazing"), "Stable", ""),

        BreedInfo("Red Poll", "Cattle", "East Anglia, England", "10–15 L/day",
            "British dual-purpose breed. Solid red coat, naturally polled. Combines good milk yield with quality beef. Docile and easy to manage.",
            listOf("Solid red coat", "Naturally polled", "Medium frame", "Dual purpose"),
            listOf("Dual purpose dairy/beef"), "Stable", ""),

        BreedInfo("Sussex", "Cattle", "Sussex, England", "N/A (Beef)",
            "Deep mahogany-red English beef breed. One of England's oldest established breeds. Well-muscled with a calm temperament. Excellent on grass.",
            listOf("Deep mahogany red", "White horns with dark tips", "Compact muscular body", "Grass efficient"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Devon", "Cattle", "Devon, England", "N/A (Beef/Dairy)",
            "Deep red dual-purpose breed from Devon. Known as 'Red Ruby'. Excellent forager. Hardy on poor pasture. Historically used for beef, dairy, and draft.",
            listOf("Deep ruby red coat", "Short curved horns", "Medium compact frame", "Excellent forager"),
            listOf("Dual purpose", "Conservation grazing"), "Stable", ""),

        BreedInfo("English Longhorn", "Cattle", "England", "N/A (Beef)",
            "Ancient English breed with spectacular long sweeping horns. Red/brown with white dorsal stripe. Nearly extinct before conservation saved it. Hardy grazier.",
            listOf("Long sweeping horns", "Red-brown with white stripe", "Medium frame", "Very hardy"),
            listOf("Beef", "Heritage conservation"), "Vulnerable", ""),

        BreedInfo("Shorthorn", "Cattle", "Northeast England", "15–20 L/day",
            "One of the most influential historical breeds. Red, roan, or white. Both dairy and beef varieties. Spread worldwide in 18th-19th centuries.",
            listOf("Red, roan, or white", "Medium-heavy frame", "Short thick horns", "Dual purpose"),
            listOf("Dairy", "Beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Swedish Red", "Cattle", "Sweden", "18–24 L/day",
            "Dominant dairy breed in Sweden and Finland. Red or red/white pied. High milk production with good fat and protein. Excellent health and longevity.",
            listOf("Red or red/white pied", "Medium-heavy frame", "Long productive life", "Good fertility"),
            listOf("Commercial dairy"), "Stable", ""),

        BreedInfo("Norwegian Red", "Cattle", "Norway", "16–22 L/day",
            "Composite dairy breed developed in Norway. Red to red-white pied. Excellent health traits, fertility, and adaptability. Popular in crossbreeding.",
            listOf("Red to pied coat", "Medium frame", "Exceptional health traits", "High fertility"),
            listOf("Commercial dairy", "Crossbreeding"), "Stable", ""),

        BreedInfo("Fleckvieh", "Cattle", "Germany / Austria", "20–28 L/day",
            "German/Austrian dual-purpose breed (Simmental variant). Red and white pied. High milk production with outstanding beef qualities. Very popular worldwide.",
            listOf("Red and white pied", "Very large heavy frame", "Dual purpose", "Rapid growth"),
            listOf("Dual purpose dairy/beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Pinzgauer", "Cattle", "Austria", "10–16 L/day",
            "Ancient Alpine breed from Pinzgau valley. Chestnut with white dorsal stripe and underparts. Dual-purpose. Extremely hardy in mountain conditions.",
            listOf("Chestnut with white stripe", "Mountain adapted", "Medium-heavy frame", "Very durable hooves"),
            listOf("Dual purpose", "Alpine farming"), "Vulnerable", ""),

        BreedInfo("Aubrac", "Cattle", "Aveyron, France", "N/A (Beef)",
            "Hardy French beef breed from volcanic Aubrac plateau. Wheat-colored with dark rim around eyes and muzzle. Excellent mothers. Traditional French heritage.",
            listOf("Wheat to fawn color", "Dark eye rims and muzzle", "Medium frame", "Strong maternal instinct"),
            listOf("Beef", "Suckling calves"), "Stable", ""),

        BreedInfo("Romagnola", "Cattle", "Emilia-Romagna, Italy", "N/A (Beef)",
            "Gray-white Italian beef breed from Romagnola region. Large and heavily muscled. Good heat tolerance. Prized for high quality lean beef.",
            listOf("Grey-white coat", "Heavy muscled body", "Short horns", "Heat tolerant"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Podolica", "Cattle", "Southern Italy", "4–6 L/day",
            "Ancient cattle breed from southern Italy with roots in Podolia (Ukraine). Grey to white with long lyre horns. Extremely hardy, semi-feral grazing.",
            listOf("Grey to white coat", "Long lyre-shaped horns", "Semi-feral", "Extremely hardy"),
            listOf("Extensive beef", "Traditional dairy"), "Vulnerable", ""),

        BreedInfo("Buša", "Cattle", "Balkans (Serbia/Bosnia)", "3–6 L/day",
            "Ancient Balkan primitive breed. Small, colorful (black, brown, red, pied). Extremely hardy and resistant to disease. Multi-colored patterned coat.",
            listOf("Various colors/patterns", "Very small stature", "Extreme disease resistance", "Ancient genetics"),
            listOf("Dual purpose", "Heritage conservation"), "Endangered", ""),

        BreedInfo("White Park", "Cattle", "Britain", "N/A (Beef)",
            "Ancient white British breed with colored points (black or red on ears, nose, feet). Semi-wild heritage. One of Britain's oldest breeds. Royal connections.",
            listOf("White with colored points", "Naturally horned", "Semi-wild temperament", "Ancient genetics"),
            listOf("Heritage conservation", "Beef"), "Endangered", ""),

        BreedInfo("Gloucester", "Cattle", "Gloucestershire, England", "8–10 L/day",
            "Rare ancient English breed. Dark brown with white finching stripe on back and underparts. Milk traditionally used for Double Gloucester cheese.",
            listOf("Dark brown with white finch line", "Lyre-shaped horns", "Medium frame", "Traditional cheese milk"),
            listOf("Specialty cheese dairy", "Heritage conservation"), "Endangered", "")
    )

    // ── AFRICAN CATTLE (35 breeds) ─────────────────────────────
    private val africanCattle = listOf(
        BreedInfo("Brahman", "Cattle", "USA (Indian origin)", "N/A (Beef)",
            "American breed developed from Indian zebu (Gir, Ongole, Sahiwal). Distinctive large hump and drooping ears. Highly heat and tick resistant. Dominant beef breed in tropics.",
            listOf("Grey to red coat", "Large hump", "Drooping ears", "Extreme heat/tick resistance"),
            listOf("Tropical beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Ankole-Watusi", "Cattle", "East Africa (Rwanda/Uganda)", "2–4 L/day",
            "Famous for enormous, uniquely shaped horns that can span over 2.4 meters. Sanga-type cattle sacred in Tutsi culture. Excellent heat tolerance. Milk very rich.",
            listOf("Giant lyre-shaped horns 2.4m span", "Red to brown coat", "Long legs", "Sacred cultural status"),
            listOf("Dairy", "Cultural/ceremonial", "Heritage"), "Stable", ""),

        BreedInfo("Afrikaner", "Cattle", "South Africa", "N/A (Beef)",
            "Ancient South African sanga breed. Red coat. Large hump, pendulous dewlap. Extremely adapted to African veld conditions. Hardy and disease resistant.",
            listOf("Red coat", "Large hump", "Pendulous dewlap", "Veld adapted"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Nguni", "Cattle", "South Africa / Swaziland", "2–6 L/day",
            "Multi-colored cattle of Zulu people. Extremely varied coat patterns. Hardy, tick-resistant, and adapted to harsh African savanna conditions.",
            listOf("Many color patterns", "Small to medium size", "Extreme tick resistance", "Savanna adapted"),
            listOf("Beef", "Cultural use"), "Stable", ""),

        BreedInfo("Boran", "Cattle", "East Africa (Kenya/Ethiopia)", "N/A (Beef)",
            "East African zebu from Borana people. White, grey, or red. Very tick and disease resistant. Excellent heat tolerance. One of Africa's best beef breeds.",
            listOf("White, grey, or red", "Medium hump", "Tick resistant", "East African adapted"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Tuli", "Cattle", "Zimbabwe / Botswana", "N/A (Beef)",
            "Golden-yellow beef breed from Zimbabwe. Natural selection for heat, drought, and tick resistance. Smooth short coat. Very efficient on poor pasture.",
            listOf("Golden yellow coat", "Small to medium hump", "Smooth short coat", "Drought resistant"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Mashona", "Cattle", "Zimbabwe", "3–5 L/day",
            "Indigenous Shona people's cattle. Dual-purpose small-medium breed. Very hardy with strong disease resistance. Adapted to hot dry Zimbabwe plateau.",
            listOf("Various colors", "Small to medium size", "Very disease resistant", "Dry plateau adapted"),
            listOf("Beef", "Dairy", "Draft"), "Stable", ""),

        BreedInfo("N'Dama", "Cattle", "West Africa (Guinea)", "2–4 L/day",
            "Small West African taurine breed with natural resistance to trypanosomiasis (sleeping sickness). Red to yellow. Critical for tsetse fly regions.",
            listOf("Red to yellow coat", "Short horns", "Trypano-tolerant", "Very small stature"),
            listOf("Trypanosomiasis-zone beef/dairy", "Conservation"), "Stable", ""),

        BreedInfo("White Fulani", "Cattle", "West Africa (Nigeria/Niger)", "3–6 L/day",
            "Large humped cattle of Fulani pastoralists. White coat with black points. Nomadic breed adapted to Sahel and savanna migrations.",
            listOf("White with black points", "Long lyre horns", "Large hump", "Nomadic adapted"),
            listOf("Dairy", "Beef", "Nomadic pastoralism"), "Stable", ""),

        BreedInfo("Red Bororo", "Cattle", "Sahel, West Africa", "2–4 L/day",
            "Large red zebu of Bororo Fulani nomads. Long lyre-shaped horns. Primarily a beef and status animal in Sahel. Known for endurance on long migrations.",
            listOf("Red coat", "Very long lyre horns", "Large hump", "Sahel adapted"),
            listOf("Beef", "Cultural/status"), "Stable", ""),

        BreedInfo("Kuri", "Cattle", "Lake Chad Basin", "3–5 L/day",
            "Unique amphibious cattle of Lake Chad islands. Balloon-like floating horns filled with spongy tissue. Excellent swimmers. Critically rare.",
            listOf("White or sandy coat", "Balloon-shaped floating horns", "Excellent swimmer", "Aquatic habitat adapted"),
            listOf("Dairy", "Beef"), "Endangered", ""),

        BreedInfo("Ethiopian Highland", "Cattle", "Ethiopia", "2–5 L/day",
            "Ancient small cattle of Ethiopian highlands. Diverse breeds including Boran, Harar, Ogaden types. Hardy for high altitude farming conditions.",
            listOf("Various colors", "Small stature", "High altitude adapted", "Hardy"),
            listOf("Dairy", "Draft", "Beef"), "Stable", ""),

        BreedInfo("Kenana", "Cattle", "Sudan", "4–8 L/day",
            "Best dairy breed in Sudan. Long-legged, leggy zebu from Kenana region of White Nile. White to light grey. Adapted to hot arid conditions.",
            listOf("White to light grey", "Long legs", "Large hump", "Heat resistant"),
            listOf("Dairy", "Beef"), "Stable", ""),

        BreedInfo("Sheko", "Cattle", "Ethiopia (Bench region)", "2–4 L/day",
            "Critically rare ancient taurine breed from Bench region of southwestern Ethiopia. Small, short-horned with trypanosomiasis tolerance. Living genetic resource.",
            listOf("Brown or dark colored", "Short horns", "Trypano-tolerant", "Ancient genetics"),
            listOf("Subsistence farming", "Conservation"), "Endangered", ""),

        BreedInfo("Bonsmara", "Cattle", "South Africa", "N/A (Beef)",
            "South African composite breed (3/8 Afrikaner, 3/8 Hereford, 1/4 Shorthorn). Adaptable beef breed. Red coat. Best beef breed for subtropical Africa.",
            listOf("Red coat", "Medium hump", "Adaptable", "Good beef conformation"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Nkone", "Cattle", "Zimbabwe / Botswana", "N/A (Beef)",
            "Sanga breed from western Zimbabwe. Fawn to brown coat. Naturally tick-resistant with a calm temperament. Efficient on native veld pastures.",
            listOf("Fawn to brown coat", "Medium hump", "Tick resistant", "Calm temperament"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Lagune", "Cattle", "Benin / Togo (West Africa)", "2–3 L/day",
            "Very small coastal West African taurine. One of the smallest cattle breeds in Africa. Strong trypanosomiasis resistance. Important genetic resource.",
            listOf("Black or pied", "Very small stature", "Trypano-tolerant", "Coastal forest adapted"),
            listOf("Subsistence farming"), "Vulnerable", ""),

        BreedInfo("Gudali", "Cattle", "West-Central Africa", "2–5 L/day",
            "Fulani cattle breed from Adamawa plateau region. Both zebu and sanga types. White or grey. Dual-purpose in humid savanna regions.",
            listOf("White or grey", "Medium hump", "Savanna adapted", "Dual purpose"),
            listOf("Dairy", "Beef", "Draft"), "Stable", "")
    )

    // ── AMERICAN CATTLE (25 breeds) ───────────────────────────
    private val americanCattle = listOf(
        BreedInfo("Nelore", "Cattle", "Brazil (Ongole origin)", "N/A (Beef)",
            "Dominant beef breed of Brazil, derived from Indian Ongole. White to grey. World's most populous beef breed with over 167 million head in Brazil alone.",
            listOf("White to grey coat", "Massive hump", "Pendulous dewlap", "Dominant tropical beef"),
            listOf("Beef production", "Tropical crossbreeding"), "Stable", ""),

        BreedInfo("Guzerat (Brazilian)", "Cattle", "Brazil (Kankrej origin)", "N/A (Beef)",
            "Brazilian adaptation of Indian Kankrej. Large heavy zebu. Silver-grey. Important in Zebu breeding in South America and USA.",
            listOf("Silver to grey coat", "Very large frame", "Upswept horns", "Zebu characteristics"),
            listOf("Beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Tabapua", "Cattle", "Brazil", "N/A (Beef)",
            "White zebu breed developed in Brazil from Indian Nellore and Gyr with European genetics. Hardy in tropical conditions. Excellent growth rate.",
            listOf("White coat", "Medium hump", "Compact build", "Tropical adapted"),
            listOf("Beef production", "Tropical farming"), "Stable", ""),

        BreedInfo("Caracu", "Cattle", "Brazil", "8–12 L/day",
            "Ancient criollo cattle of Brazil. Yellow to cream colored. One of Brazil's oldest breeds adapted to tropical conditions for 400+ years.",
            listOf("Yellow to cream coat", "Medium hump", "Tropical adapted", "Heritage breed"),
            listOf("Dairy", "Beef", "Heritage conservation"), "Vulnerable", ""),

        BreedInfo("Texas Longhorn", "Cattle", "USA (Spanish origin)", "N/A (Beef)",
            "Iconic American cattle with spectacularly long horns (can span 2+ meters). Derived from Spanish cattle brought by Columbus. Extremely hardy.",
            listOf("Very long horns 2m+", "Various colors", "Lean body", "Extreme hardiness"),
            listOf("Beef", "Heritage", "Tourism"), "Stable", ""),

        BreedInfo("Brangus", "Cattle", "USA", "N/A (Beef)",
            "American composite breed (3/8 Brahman, 5/8 Angus). Black, polled. Combines Angus beef quality with Brahman heat and tick resistance.",
            listOf("Black polled coat", "Moderate hump", "Compact build", "Tick resistant"),
            listOf("Tropical beef production"), "Stable", ""),

        BreedInfo("Beefmaster", "Cattle", "USA", "N/A (Beef)",
            "American composite (1/2 Brahman, 1/4 Hereford, 1/4 Shorthorn). Selected for growth, conformation, and adaptability. Varied colors.",
            listOf("Various colors", "Medium hump", "Heavy muscled", "Heat tolerant"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Santa Gertrudis", "Cattle", "USA (Texas)", "N/A (Beef)",
            "First beef breed developed in USA. Cherry red (5/8 Shorthorn, 3/8 Brahman). From King Ranch, Texas. Hardy in subtropical conditions.",
            listOf("Cherry red coat", "Moderate hump", "Thick hide", "Heat tolerant"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Senepol", "Cattle", "US Virgin Islands", "N/A (Beef)",
            "Heat-tolerant polled beef breed from St. Croix. Red coat. Developed from N'Dama and Red Poll. Excellent tick and heat resistance without zebu characteristics.",
            listOf("Red coat", "Naturally polled", "Heat tolerant", "No hump - taurine"),
            listOf("Tropical beef", "Crossbreeding"), "Stable", ""),

        BreedInfo("Romosinuano", "Cattle", "Colombia", "4–8 L/day",
            "Colombian criollo breed adapted to tropical lowlands. Red to yellow. Naturally polled. Excellent tick and heat resistance. Dual-purpose.",
            listOf("Red to yellow coat", "Naturally polled", "Tropical lowland adapted", "Tick resistant"),
            listOf("Dual purpose dairy/beef"), "Vulnerable", ""),

        BreedInfo("Criollo", "Cattle", "Latin America (Spanish origin)", "3–6 L/day",
            "Group of breeds descended from Spanish cattle brought to Americas 500+ years ago. Extremely diverse. Adapted to every climate from Andes to Amazon.",
            listOf("Highly variable", "Small to medium size", "Various climates adapted", "Ancient genetics"),
            listOf("Dual purpose", "Draft", "Cultural heritage"), "Stable", ""),

        BreedInfo("Murray Grey", "Cattle", "Victoria, Australia", "N/A (Beef)",
            "Australian beef breed developed in Murray River valley. Silver-grey coat. Polled. Combines Shorthorn milking traits with Angus beef quality.",
            listOf("Silver-grey coat", "Naturally polled", "Medium frame", "Quality beef"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Droughtmaster", "Cattle", "Queensland, Australia", "N/A (Beef)",
            "Australian tropical beef breed (Brahman + Shorthorn). Red coat. Highly adapted to harsh Australian tropical conditions. Tick and buffalo fly resistant.",
            listOf("Red coat", "Small to medium hump", "Heat tolerant", "Australian tropical adapted"),
            listOf("Beef production"), "Stable", ""),

        BreedInfo("Braford", "Cattle", "USA/Australia", "N/A (Beef)",
            "Composite breed (Brahman + Hereford). Red body with white face. Combines Hereford quality with Brahman adaptability. Popular in subtropical regions.",
            listOf("Red body with white face", "Moderate hump", "Heat tolerant", "Good temperament"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Simbrah", "Cattle", "USA", "N/A (Beef)",
            "Simmental-Brahman composite. Large framed with excellent growth. Combines Simmental's size and dual-purpose traits with Brahman's heat tolerance.",
            listOf("Red-white pied or solid", "Moderate hump", "Very large frame", "Heat tolerant"),
            listOf("Beef production", "Crossbreeding"), "Stable", ""),

        BreedInfo("Florida Cracker", "Cattle", "Florida, USA", "3–5 L/day",
            "Ancient Spanish criollo cattle of Florida. Small, wiry, and extremely heat/parasite resistant. Cultural heritage breed. Among USA's oldest cattle.",
            listOf("Various colors", "Small wiry build", "Tick resistant", "Ancient Spanish heritage"),
            listOf("Heritage conservation", "Extensive beef"), "Vulnerable", ""),

        BreedInfo("Pineywoods", "Cattle", "Southern USA", "3–5 L/day",
            "Ancient feral-origin cattle from southern USA pine forests. Hardy, disease resistant. Living genetic archive of colonial Spanish cattle.",
            listOf("Various color patterns", "Small frame", "Very disease resistant", "Forest adapted"),
            listOf("Heritage conservation", "Extensive beef"), "Endangered", "")
    )

    // ── ASIAN & PACIFIC CATTLE (20 breeds) ────────────────────
    private val asianPacificCattle = listOf(
        BreedInfo("Wagyu (Japanese Black)", "Cattle", "Japan", "N/A (Beef)",
            "World's most prized beef breed. Produces extraordinary intramuscular fat marbling (IMF). 'Kobe' beef comes from this breed. Black coat. Genetic mutations unique for marbling.",
            listOf("Black coat", "Superior marbling IMF", "Small to medium frame", "Docile temperament"),
            listOf("Premium marbled beef", "Global luxury market"), "Stable", ""),

        BreedInfo("Wagyu (Japanese Brown)", "Cattle", "Japan (Kumamoto)", "N/A (Beef)",
            "Red Japanese Wagyu variety from Kumamoto. Also produces highly marbled beef though less intensely than Japanese Black. Known as 'Akaushi' (red cow).",
            listOf("Red to brown coat", "Good marbling", "Medium frame", "Docile"),
            listOf("Premium beef", "Akaushi beef market"), "Stable", ""),

        BreedInfo("Hanwoo", "Cattle", "South Korea", "N/A (Beef)",
            "Korean native beef cattle. Light tan to dark brown. Highly marbled premium beef. National symbol of South Korea. Prized for unique flavor profile.",
            listOf("Light tan to brown coat", "Medium frame", "Well-marbled beef", "National heritage"),
            listOf("Premium Korean beef"), "Stable", ""),

        BreedInfo("Qinchuan", "Cattle", "Shaanxi, China", "N/A (Draft/Beef)",
            "One of China's five major cattle breeds. Red to brown coat. Large frame. Traditionally used for draft in Guanzhong plains. Now developed for beef.",
            listOf("Red to brown coat", "Large heavy frame", "Strong draft capacity", "Northern China adapted"),
            listOf("Draft", "Beef production"), "Stable", ""),

        BreedInfo("Nanyang", "Cattle", "Henan, China", "N/A (Draft/Beef)",
            "Chinese draft breed from Nanyang basin. Brown coat. One of China's largest cattle breeds. Historically important for agricultural draft.",
            listOf("Brown coat", "Very large frame", "Heavy hump", "Agricultural draft"),
            listOf("Draft", "Beef"), "Stable", ""),

        BreedInfo("Luxi", "Cattle", "Shandong, China", "N/A (Draft/Beef)",
            "Strong Chinese draft breed from Shandong. Yellow to brown coat. Famous for its pulling strength and endurance. Now increasingly used for beef.",
            listOf("Yellow to brown coat", "Heavy muscular frame", "Great pulling strength", "Endurance draft"),
            listOf("Draft", "Beef"), "Stable", ""),

        BreedInfo("Mongolian", "Cattle", "Mongolia", "3–6 L/day",
            "Ancient nomadic cattle of Mongolian steppes. Brown or black-brown. Very hardy for extreme cold. Used for milk, meat, and transport by nomadic herders.",
            listOf("Brown or black-brown", "Compact hardy body", "Extreme cold resistant", "Nomadic adapted"),
            listOf("Dairy", "Beef", "Nomadic use"), "Stable", ""),

        BreedInfo("Yak", "Cattle", "Tibet / Himalayas", "1–2.5 L/day",
            "High-altitude bovine of Tibetan plateau and Himalayas. Massive long-haired body. Can survive at 6000m altitude. Milk extremely rich in fat (6–7%). Vital to mountain communities.",
            listOf("Long thick shaggy coat", "Black or brown", "Up to 6000m altitude", "Extreme cold resistant"),
            listOf("Dairy", "Meat", "Pack transport", "Fiber"), "Stable", ""),

        BreedInfo("Mithun (Gayal)", "Cattle", "Northeast India / Myanmar", "2–3 L/day",
            "Semi-domesticated bovine of Northeast India and Myanmar. Related to gaur. Black or brown with white legs. Sacred in Naga and Mizo culture. Currency and sacrifice animal.",
            listOf("Black or brown with white legs", "Massive build", "Semi-wild", "Sacred cultural animal"),
            listOf("Cultural ceremonies", "Meat", "Currency"), "Vulnerable", ""),

        BreedInfo("Bali Cattle (Banteng)", "Cattle", "Bali, Indonesia", "2–4 L/day",
            "Domesticated banteng of Bali and Java. Red-brown (females) and dark brown-black (males). White rump. Important for Balinese culture and agriculture.",
            listOf("Red-brown or dark body", "White rump and legs", "Compact frame", "Indonesian island adapted"),
            listOf("Draft", "Beef", "Cultural use"), "Stable", ""),

        BreedInfo("Kedah-Kelantan", "Cattle", "Malaysia", "2–5 L/day",
            "Small Malaysian zebu cattle from northern states. Grey or brown. Compact and heat-tolerant. Used for draft in Malaysian rice paddies. National heritage breed.",
            listOf("Grey or brown coat", "Small compact build", "Rice paddy adapted", "Heat tolerant"),
            listOf("Draft", "Light beef"), "Stable", ""),

        BreedInfo("Lao Highland", "Cattle", "Laos", "2–4 L/day",
            "Indigenous cattle of Lao highlands. Small zebu-type. Adapted to mountainous terrain. Kept by ethnic minorities for draft, meat, and ceremonies.",
            listOf("Brown or dark coat", "Small stature", "Mountain adapted", "Multi-purpose"),
            listOf("Draft", "Beef", "Ceremonial"), "Stable", ""),

        BreedInfo("Australian Lowline", "Cattle", "Australia", "N/A (Beef)",
            "World's smallest registered beef cattle breed, developed from Angus. Docile and efficient on small landholdings. 30% smaller than standard Angus.",
            listOf("Black coat", "Very small stature", "Naturally polled", "Efficient beef converter"),
            listOf("Small farm beef", "Hobby farms"), "Stable", ""),

        BreedInfo("Sahiwal (Pakistan)", "Cattle", "Punjab, Pakistan", "12–16 L/day",
            "Pakistan's best dairy zebu, same origin as Indian Sahiwal. Reddish-brown. Adapted to subtropical heat. One of top dairy zebu breeds globally.",
            listOf("Reddish-brown", "Heavy body", "Heat tolerant", "Tick resistant"),
            listOf("Commercial dairy", "Crossbreeding"), "Stable", ""),

        BreedInfo("Tharparkar (Pakistani)", "Cattle", "Sindh, Pakistan", "8–10 L/day",
            "Pakistan counterpart of Indian Tharparkar. White to grey. Desert breed known for high milk yield in water-scarce, hot environments.",
            listOf("White to grey", "Lyre horns", "Desert adapted", "Good milk in drought"),
            listOf("Desert dairy", "Draft"), "Stable", ""),

        BreedInfo("Aceh", "Cattle", "Aceh Province, Indonesia", "N/A (Beef)",
            "Small indigenous cattle of Aceh, Sumatra. Thought to be descended from Banteng. Very compact and heat-tolerant. Important for local small farmers.",
            listOf("Vary from red to dark brown", "Small stature", "Tropical heat tolerant", "Banteng related"),
            listOf("Beef", "Draft"), "Stable", ""),

        BreedInfo("Korean Native (Hanwoo)", "Cattle", "Korea", "N/A (Beef)",
            "The native Korean cattle breed with rich history over 2000 years. Tan to dark brown. Produces premium marbled 'Hanwoo' beef valued among world's finest.",
            listOf("Tan to light brown", "Medium frame", "Very fine marbling", "2000-year heritage"),
            listOf("Premium beef"), "Stable", "")
    )

    // ── Helper functions ──────────────────────────────────────
    fun getCattle() = list.filter { it.category == "Cattle" }
    fun getBuffalo() = list.filter { it.category == "Buffalo" }
    fun getEndangered() = list.filter { it.conservationStatus == "Endangered" }
    fun getVulnerable() = list.filter { it.conservationStatus == "Vulnerable" }
    fun getByRegion(region: String) = list.filter {
        it.originState.contains(region, ignoreCase = true)
    }
    fun search(query: String) = list.filter {
        it.name.contains(query, true) ||
                it.originState.contains(query, true) ||
                it.description.contains(query, true) ||
                it.category.contains(query, true)
    }
}

// ═══════════════════════════════════════════════════════════════
//  ENCYCLOPEDIA SCREEN UI
// ═══════════════════════════════════════════════════════════════
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncyclopediaScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var expandedBreed by remember { mutableStateOf<String?>(null) }

    val categories = listOf(
        "All" to AllBreeds.list.size,
        "Cattle" to AllBreeds.getCattle().size,
        "Buffalo" to AllBreeds.getBuffalo().size,
        "Endangered" to AllBreeds.getEndangered().size,
        "Indian" to AllBreeds.getByRegion("India").size,
        "European" to AllBreeds.getByRegion("Europe").size.let {
            AllBreeds.list.count { b ->
                listOf("Netherlands","Germany","France","Italy","UK","Scotland",
                    "Ireland","Switzerland","Sweden","Norway","Austria","Belgium",
                    "Spain","Portugal").any { r -> b.originState.contains(r, true) }
            }
        }
    )

    val filteredBreeds = remember(searchQuery, selectedCategory) {
        AllBreeds.list.filter { breed ->
            val categoryMatch = when (selectedCategory) {
                "All" -> true
                "Cattle" -> breed.category == "Cattle"
                "Buffalo" -> breed.category == "Buffalo"
                "Endangered" -> breed.conservationStatus == "Endangered"
                "Indian" -> breed.originState.contains("India", true)
                "European" -> listOf(
                    "Netherlands","Germany","France","Italy","UK","Scotland",
                    "Ireland","Switzerland","Sweden","Norway","Austria","Belgium"
                ).any { r -> breed.originState.contains(r, true) }
                else -> true
            }
            val searchMatch = searchQuery.isEmpty() ||
                    breed.name.contains(searchQuery, true) ||
                    breed.originState.contains(searchQuery, true) ||
                    breed.description.contains(searchQuery, true)
            categoryMatch && searchMatch
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Breed Encyclopedia",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Text(
                            "${AllBreeds.list.size} breeds worldwide",
                            fontSize = 12.sp,
                            color = Color(0xFF9CA3AF)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF374151)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF8F5F0)
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = Screen.Encyclopedia.route
            )
        },
        containerColor = Color(0xFFF8F5F0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        "Search breeds, origin, traits...",
                        fontSize = 14.sp,
                        color = Color(0xFF9CA3AF)
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(20.dp)
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = null,
                                tint = Color(0xFF9CA3AF),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF1B4332),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true
            )

            // Category filter chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Triple("All", "${AllBreeds.list.size}", Color(0xFF1B4332)),
                    Triple("Indian", "${AllBreeds.getByRegion("India").size}", Color(0xFFD97706)),
                    Triple("Cattle", "${AllBreeds.getCattle().size}", Color(0xFF92400E)),
                    Triple("Buffalo", "${AllBreeds.getBuffalo().size}", Color(0xFF1E3A5F)),
                    Triple("European", "37", Color(0xFF5B21B6)),
                    Triple("African", "${AllBreeds.getByRegion("Africa").size}", Color(0xFF065F46)),
                    Triple("Endangered", "${AllBreeds.getEndangered().size}", Color(0xFFDC2626))
                ).forEach { (cat, count, color) ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        onClick = { selectedCategory = cat },
                        label = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(cat, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                Text(count, fontSize = 10.sp)
                            }
                        },
                        selected = isSelected,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = color,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color(0xFF6B7280)
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = Color(0xFFE5E7EB),
                            selectedBorderColor = color
                        )
                    )
                }
            }

            // Results count
            Text(
                text = "${filteredBreeds.size} breed${if (filteredBreeds.size != 1) "s" else ""} found",
                fontSize = 12.sp,
                color = Color(0xFF9CA3AF),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )

            // Breed list
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredBreeds, key = { it.name }) { breed ->
                    BreedCard(
                        breed = breed,
                        isExpanded = expandedBreed == breed.name,
                        onToggle = {
                            expandedBreed = if (expandedBreed == breed.name) null else breed.name
                        }
                    )
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}

@Composable
fun BreedCard(breed: BreedInfo, isExpanded: Boolean, onToggle: () -> Unit) {
    val statusColor = when (breed.conservationStatus) {
        "Endangered" -> Color(0xFFDC2626)
        "Vulnerable" -> Color(0xFFD97706)
        else -> Color(0xFF059669)
    }
    val isIndian = breed.originState.contains("India", true)
    val isBuffalo = breed.category == "Buffalo"
    val accentColor = when {
        isBuffalo -> Color(0xFF1E3A5F)
        isIndian -> Color(0xFFD97706)
        breed.originState.contains("Japan", true) ||
                breed.originState.contains("Korea", true) -> Color(0xFFDC2626)
        breed.originState.contains("Africa", true) ||
                breed.originState.contains("Kenya", true) ||
                breed.originState.contains("Nigeria", true) ||
                breed.originState.contains("South Africa", true) -> Color(0xFF065F46)
        else -> Color(0xFF1B4332)
    }
    val accentBg = accentColor.copy(alpha = 0.1f)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onToggle),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            if (isExpanded) 1.5.dp else 1.dp,
            if (isExpanded) accentColor else Color(0xFFE5E7EB)
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Category icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(accentBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isBuffalo) Icons.Default.Water else Icons.Default.Grass,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        breed.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF111827)
                    )
                    Text(
                        "${breed.category}  •  ${breed.originState}",
                        fontSize = 12.sp,
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        Surface(
                            shape = RoundedCornerShape(5.dp),
                            color = statusColor.copy(alpha = 0.1f)
                        ) {
                            Text(
                                breed.conservationStatus,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = statusColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        if (isIndian) {
                            Surface(
                                shape = RoundedCornerShape(5.dp),
                                color = Color(0xFFD97706).copy(alpha = 0.1f)
                            ) {
                                Text(
                                    "Indian",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFFD97706),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
                Icon(
                    if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color(0xFF9CA3AF),
                    modifier = Modifier.size(22.dp)
                )
            }

            // Milk yield pill
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0FDF4))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Water,
                    contentDescription = null,
                    tint = Color(0xFF059669),
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    "Milk: ${breed.milkYield}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF065F46)
                )
            }

            // Expanded details
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFFF3F4F6)
                    )
                    Text(
                        breed.description,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        color = Color(0xFF374151)
                    )
                    if (breed.characteristics.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Key Characteristics",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        breed.characteristics.forEach { t ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 6.dp)
                                        .size(5.dp)
                                        .background(accentColor, RoundedCornerShape(50))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(t, fontSize = 13.sp, color = Color(0xFF4B5563))
                            }
                        }
                    }
                    if (breed.uses.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Primary Uses",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.horizontalScroll(rememberScrollState())
                        ) {
                            breed.uses.forEach { use ->
                                Surface(
                                    shape = RoundedCornerShape(7.dp),
                                    color = accentBg,
                                    border = BorderStroke(1.dp, accentColor.copy(alpha = 0.25f))
                                ) {
                                    Text(
                                        use,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = accentColor,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFF9FAFB))
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFF9CA3AF),
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                breed.originState,
                                fontSize = 12.sp,
                                color = Color(0xFF6B7280)
                            )
                        }
                        Surface(
                            shape = RoundedCornerShape(5.dp),
                            color = statusColor.copy(alpha = 0.1f)
                        ) {
                            Text(
                                breed.conservationStatus,
                                fontSize = 11.sp,
                                color = statusColor,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}