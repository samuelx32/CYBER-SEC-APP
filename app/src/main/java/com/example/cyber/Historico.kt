package com.example.cyber

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HistoricoScreen(viewModel: HistoricoGeralViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(16.dp)
    ) {
        Text(
            text = "Histórico Geral",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF061233),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (viewModel.historico.isEmpty()) {
            // Mensagem caso o histórico esteja vazio
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum dado registrado ainda.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        } else {
            // Lista de itens do histórico
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.historico.forEachIndexed { index, item ->
                    HistoricoItem(
                        tipo = item.tipo,
                        conteudo = item.conteudo,
                        status = item.status,
                        onDelete = { viewModel.removerItem(index) } // Permite excluir itens
                    )
                }
            }
        }
    }
}

@Composable
fun HistoricoItem(
    tipo: String,
    conteudo: String,
    status: String,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Tipo: $tipo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF061233)
                )
                Text(
                    text = "Conteúdo: $conteudo",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Status: $status",
                    fontSize = 14.sp,
                    color = if (status == "Seguro") Color.Green else Color.Red
                )
            }

            // Botão para remover item do histórico
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remover Item",
                    tint = Color.Red
                )
            }
        }
    }
}
