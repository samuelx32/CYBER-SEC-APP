package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.shape.RoundedCornerShape // Import necessário para bordas arredondadas


@Composable
fun HistoricoScreen(navController: NavHostController) {
    val historicoSenhas = listOf("Senha: ********123", "Senha: ********XYZ")
    val historicoLinks = listOf("www.malicious-site.com", "www.suspicious-link.com")
    val historicoEmails = listOf("phishing@example.com", "suspicious@mail.com")
    val historicoArquivos = listOf("arquivo_malware.exe", "documento_suspeito.pdf")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        // Cabeçalho
        Text(
            text = "Histórico",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color(0xFF061233),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Seções do Histórico
        Text(
            text = "Senhas Copiadas:",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1D2B53),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        historicoSenhas.forEach {
            HistoricoItem(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Links Maliciosos Confirmados:",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1D2B53),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        historicoLinks.forEach {
            HistoricoItem(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "E-mails Suspeitos:",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1D2B53),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        historicoEmails.forEach {
            HistoricoItem(it)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Arquivos Verificados:",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1D2B53),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        historicoArquivos.forEach {
            HistoricoItem(it)
        }
    }
}

@Composable
fun HistoricoItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            color = Color.Black
        )
    }
}
