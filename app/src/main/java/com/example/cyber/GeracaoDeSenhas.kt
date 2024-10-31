package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.navigation.NavHostController
import androidx.compose.ui.text.font.FontWeight



@Composable
fun GeradorDeSenhas(navController: NavHostController) {
    var length by remember { mutableStateOf("") }
    var includeUppercase by remember { mutableStateOf(false) }
    var includeLowercase by remember { mutableStateOf(true) }
    var includeNumbers by remember { mutableStateOf(false) }
    var includeSymbols by remember { mutableStateOf(true) }
    var generatedPassword by remember { mutableStateOf("Senha Gerada") }
    var passwordStrength by remember { mutableStateOf("Média") }
    var passwordStrengthProgress by remember { mutableFloatStateOf(0.5f) }
    var progressBarColor by remember { mutableStateOf(Color.Yellow) } // Cor da barra de progresso

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Seta de retorno ao topo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, // Ícone padrão de retorno
                    contentDescription = "Voltar",
                    tint = Color(0xFF1D2B53)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Gerador de Senhas",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 22.sp,
                    color = Color(0xFF1D2B53)
                ),
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Insira o comprimento da senha a ser gerada:",
            color = Color(0xFF123456),
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = length,
            onValueChange = { newText ->
                if (newText.all { it.isDigit() }) {
                    length = newText // Aceita apenas números
                }
            },
            placeholder = { Text("Apenas números") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                CheckboxWithLabel("Letras Maiúsculas", includeUppercase) { includeUppercase = it }
                CheckboxWithLabel("Letras Minúsculas", includeLowercase) { includeLowercase = it }
            }
            Column {
                CheckboxWithLabel("Números", includeNumbers) { includeNumbers = it }
                CheckboxWithLabel("Símbolos", includeSymbols) { includeSymbols = it }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                generatedPassword = generatePassword(
                    length.toIntOrNull() ?: 8,
                    includeUppercase,
                    includeLowercase,
                    includeNumbers,
                    includeSymbols
                )
                passwordStrength = calculatePasswordStrength(generatedPassword)
                when (passwordStrength) {
                    "Fraca" -> {
                        passwordStrengthProgress = 0.25f
                        progressBarColor = Color.Red
                    }
                    "Média" -> {
                        passwordStrengthProgress = 0.5f
                        progressBarColor = Color.Yellow
                    }
                    "Forte" -> {
                        passwordStrengthProgress = 0.75f
                        progressBarColor = Color(0xFFFFA500) // Laranja
                    }
                    "Muito Forte" -> {
                        passwordStrengthProgress = 1.0f
                        progressBarColor = Color.Green
                    }
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(text = "Gerar Senha", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = generatedPassword,
            onValueChange = { generatedPassword = it },
            readOnly = true,
            placeholder = { Text("Senha Gerada") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = { /* lógica para copiar senha */ },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Copy", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Força da Senha: $passwordStrength", color = Color(0xFF123456))
            Spacer(modifier = Modifier.width(8.dp))
            LinearProgressIndicator(
                progress = passwordStrengthProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = progressBarColor
            )
        }
    }
}

@Composable
fun CheckboxWithLabel(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = label, color = Color(0xFF123456))
    }
}

fun generatePassword(
    length: Int,
    uppercase: Boolean,
    lowercase: Boolean,
    numbers: Boolean,
    symbols: Boolean
): String {
    val uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val lowercaseChars = "abcdefghijklmnopqrstuvwxyz"
    val numberChars = "0123456789"
    val symbolChars = "!@#$%^&*()-_=+<>,.?"

    var allowedChars = ""
    if (uppercase) allowedChars += uppercaseChars
    if (lowercase) allowedChars += lowercaseChars
    if (numbers) allowedChars += numberChars
    if (symbols) allowedChars += symbolChars

    if (allowedChars.isEmpty()) allowedChars = lowercaseChars

    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

fun calculatePasswordStrength(password: String): String {
    var strength = 0
    if (password.length >= 8) strength++
    if (password.any { it.isUpperCase() }) strength++
    if (password.any { it.isLowerCase() }) strength++
    if (password.any { it.isDigit() }) strength++
    if (password.any { "!@#$%^&*()-_=+<>,.?".contains(it) }) strength++

    return when (strength) {
        in 0..1 -> "Fraca"
        2 -> "Média"
        3 -> "Forte"
        else -> "Muito Forte"
    }
}
