package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun GeradorDeSenhas(
    navController: NavHostController,
    viewModel: HistoricoGeralViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var length by remember { mutableStateOf("") }
    var includeUppercase by remember { mutableStateOf(false) }
    var includeLowercase by remember { mutableStateOf(true) }
    var includeNumbers by remember { mutableStateOf(false) }
    var includeSymbols by remember { mutableStateOf(true) }
    var generatedPassword by remember { mutableStateOf("Senha Gerada") }
    var passwordStrength by remember { mutableStateOf("Média") }
    var passwordStrengthProgress by remember { mutableFloatStateOf(0.5f) }
    var progressBarColor by remember { mutableStateOf(Color.Yellow) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Cabeçalho com seta de retorno
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Gerador de Senhas",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF1D2B53),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        // Card com instruções
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1D2B53)
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Requisitos da Senha:",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "• Mínimo de 6 caracteres\n• Recomendado usar letras, números e símbolos",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        // Campo de Comprimento
        OutlinedTextField(
            value = length,
            onValueChange = { newText ->
                if (newText.all { it.isDigit() }) length = newText
            },
            label = { Text("Comprimento da senha") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Opções em Cards
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Opções de Caracteres",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1D2B53),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        CheckboxWithLabel("Maiúsculas (A-Z)", includeUppercase) { includeUppercase = it }
                        CheckboxWithLabel("Minúsculas (a-z)", includeLowercase) { includeLowercase = it }
                    }
                    Column {
                        CheckboxWithLabel("Números (0-9)", includeNumbers) { includeNumbers = it }
                        CheckboxWithLabel("Símbolos (!@#)", includeSymbols) { includeSymbols = it }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão Gerar Senha
        Button(
            onClick = {
                val tamInp = length.toIntOrNull() ?: 8
                generatedPassword = generatePassword(
                    tamInp,
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

                // Salva no histórico
                viewModel.adicionarItem("Senha Gerada", generatedPassword, "Força: $passwordStrength")
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("Gerar Senha", color = Color.White, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo com a Senha Gerada
        OutlinedTextField(
            value = generatedPassword,
            onValueChange = {},
            readOnly = true,
            label = { Text("Senha Gerada") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botão Copiar Senha
        Button(
            onClick = {
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("Generated Password", generatedPassword)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context, "Senha copiada!", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Copiar", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra de Progresso da Força da Senha
        Row(verticalAlignment = Alignment.CenterVertically) {
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
        .map { allowedChars.random() }  // Elemento de imbrevisibilidade
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