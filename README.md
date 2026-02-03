# PassWordChecker
A Java Swing GUI application that analyzes password strength in real-time. Features interactive password criteria validation, visual strength indicators with color-coded progress bars, and detailed security feedback to help users create stronger passwords.
🔐 Password Strength Checker
A Java Swing-based GUI application that evaluates password strength in real-time, providing visual feedback and security recommendations.

https://img.shields.io/badge/Java-17%252B-blue
https://img.shields.io/badge/GUI-Swing-orange
https://img.shields.io/badge/License-MIT-green

# Features
Real-time Analysis: Password strength updates as you type

Visual Feedback: Color-coded progress bar and strength indicators

# Criteria Validation: Checks 6 essential password security criteria:

Minimum 8 characters
Contains uppercase letters
Contains lowercase letters
Contains numbers
Contains special characters
No common patterns (like "123", "password", etc.)
Score System: 0-100 scoring system with detailed breakdown

# User-Friendly Interface:

Show/hide password toggle

Clear button to reset inputs

Responsive design with intuitive layout

Pattern Detection: Identifies common weak patterns and repeated characters

Getting Started
Prerequisites
Java Runtime Environment (JRE) 8 or higher

# Java Development Kit (JDK) 8 or higher (for compilation)

# Installation
Clone the repository:

bash
git clone https://github.com/aarif121-s/PassWordChecker.git
cd password-strength-checker
Compile the application:

bash
javac PasswordStrengthChecker.java
Run the application:

bash
java PasswordStrengthChecker
Alternative: Using an IDE
Open the project in your preferred Java IDE (Eclipse, IntelliJ IDEA, NetBeans)

Import the PasswordStrengthChecker.java file

Run the main method

# How to Use
Launch the application

Enter a password in the text field

The strength will update automatically as you type

Use the "Show Password" checkbox to toggle visibility

View the analysis:

Progress bar shows strength level with color coding

Score (0-100) displays numerical evaluation

Checkboxes indicate which criteria are met

# Use the buttons:

"Check Strength": Manually trigger analysis

"Clear": Reset all fields and analysis

# Strength Levels
Score Range	Level	Color	Description
0-19	Very Weak	🔴 Red	Extremely vulnerable
20-39	Weak	🟠 Orange	Easily guessable
40-59	Moderate	🟡 Yellow	Basic protection
60-79	Good	🟢 Green	Reasonably secure
80-100	Strong	🔵 Blue	Highly secure
Scoring System
The application uses a weighted scoring system:

Length: Up to 40 points (3 points per character, max 40)

Character Variety: Up to 40 points (10 points per character type)

Special Characters: 10 points

No Common Patterns: 10 points

Project Structure
text
PasswordStrengthChecker/
├── PasswordStrengthChecker.java  # Main application file
├── README.md                     # This file
└── screenshot.png                # Application screenshot
# Technical Details
Framework: Java Swing for GUI

Pattern Matching: Java Regex for criteria validation

Architecture: MVC-inspired design with clear separation of concerns

Thread Safety: All GUI operations on EDT (Event Dispatch Thread)
