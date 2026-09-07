#  GauScan

### AI-Powered Cattle & Buffalo Breed Identification and Livestock Management

GauScan is a modern Android application built to help cattle and buffalo owners identify breeds, maintain livestock records, and access practical livestock management tools from a single platform.

The application combines **AI-powered image analysis**, **Firebase-based data management**, and a clean **Jetpack Compose** interface to provide an easy-to-use digital solution for livestock management.

---

##  About GauScan

Identifying livestock breeds and maintaining individual animal records can be difficult when information is scattered across different sources.

**GauScan brings these capabilities together in one application.**

Users can scan an animal image to receive AI-assisted breed information, explore a breed encyclopedia, maintain scan history, and use tools for milk tracking, feed calculation, health records, and breed comparison.

---

## ✨ Key Features

### 🤖 AI Breed Identification
- Scan cattle or buffalo images using the device camera/gallery.
- AI-assisted breed identification.
- Displays predicted breed information and confidence level.
- Supports both cattle and buffalo categories.

### 📚 Breed Encyclopedia
Explore information about different Indian cattle and buffalo breeds, including:

- Gir
- Sahiwal
- Murrah
- Ongole
- Tharparkar
- Jaffarabadi
- Red Sindhi
- Nili-Ravi
- Kankrej
- Bhadawari

###  Scan History
- Automatically maintain previous scan records.
- Review previously identified animals.
- Store relevant scan information for future reference.

###  User Authentication & Profiles
- Firebase Authentication.
- User-specific data management.
- Personal profile support.

###  Milk Tracker
Track milk production records and maintain a history of milk-related data.

###  Feed Calculator
A practical tool for estimating livestock feed requirements and managing feeding information.

###  Health Diary
Maintain health-related records and important notes for individual livestock.

###  Breed Comparison
Compare different cattle and buffalo breeds to understand their characteristics and differences.

###  Modern User Interface
- Built with Jetpack Compose.
- Clean and responsive UI.
- Material Design principles.
- Dark/light theme support.
- Structured navigation between application modules.

---

##  Architecture

GauScan follows a modular architecture based on modern Android development practices.

```text
UI Layer
   │
   ├── Screens
   ├── ViewModels
   └── Navigation
        │
        ▼
Domain / Repository Layer
        │
        ├── AuthRepository
        └── ScanRepository
        │
        ▼
Data Layer
        │
        ├── Models
        ├── Firebase
        └── Gemini API
