package com.example.cyber


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(navigateTo: (String) -> Unit, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Menu",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF061233),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Itens do menu
            DrawerItem("Gerador de Senhas", R.drawable.ic_key, navigateTo = {
                navigateTo("gerarSenha")
            }, onClose = onClose)

            DrawerItem("Verificação de E-mails", R.drawable.ic_email, navigateTo = {
                navigateTo("verificacaoEmail")
            }, onClose = onClose)

            DrawerItem("Verificação de URLs", R.drawable.ic_url, navigateTo = {
                navigateTo("verificacaoUrl")
            }, onClose = onClose)

            DrawerItem("Chat de Suporte", R.drawable.ic_chat, navigateTo = {
                navigateTo("chatSuporte")
            }, onClose = onClose)
        }

        // Rodapé com configuração ou logout
        Text(
            text = "Configurações",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            modifier = Modifier.clickable {
                navigateTo("configuracoes")
                onClose()
            }
        )
    }
}

@Composable
fun DrawerItem(title: String, icon: Int, navigateTo: () -> Unit, onClose: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateTo()
                onClose()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF1D2B53)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF061233)
        )
    }
}
