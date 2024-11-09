package com.example.cyber

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TelaInicial(navigateTo: (String) -> Unit) {
    var menuExpanded by remember { mutableStateOf(false) }
    var verificationExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCBD6E2))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header com ícone do menu e perfil
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Ícone do menu suspenso
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { menuExpanded = true }
            )

            // Ícone do perfil
            Image(
                painter = painterResource(id = R.drawable.ic_profile),
                contentDescription = "Perfil",
                modifier = Modifier
                    .size(30.dp)
                    .clickable { navigateTo("login") }
            )

            // Menu Dropdown
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                modifier = Modifier.background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_key),
                                contentDescription = "Gerador de Senha",
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF1D2B53)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Gerador de Senha", color = Color(0xFF1D2B53))
                        }
                    },
                    onClick = {
                        navigateTo("gerarSenha")
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_chat),
                                contentDescription = "Verificação de Segurança",
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF1D2B53)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Verificação de Segurança", color = Color(0xFF1D2B53))
                        }
                    },
                    onClick = {
                        verificationExpanded = !verificationExpanded // Expande submenus
                    }
                )

                // Submenus de Verificação de Segurança
                if (verificationExpanded) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_email),
                                    contentDescription = "E-mail",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("E-mail", color = Color.Gray)
                            }
                        },
                        onClick = {
                            navigateTo("verificacaoEmail")
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_url),
                                    contentDescription = "URL",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("URL", color = Color.Gray)
                            }
                        },
                        onClick = {
                            navigateTo("verificacaoUrl")
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_file),
                                    contentDescription = "Arquivos",
                                    modifier = Modifier.size(20.dp),
                                    tint = Color.Gray
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text("Arquivos", color = Color.Gray)
                            }
                        },
                        onClick = {
                            navigateTo("verificacaoArquivos")
                            menuExpanded = false
                        }
                    )
                }

                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_shield),
                                contentDescription = "Chat de Suporte",
                                modifier = Modifier.size(20.dp),
                                tint = Color(0xFF1D2B53)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Chat de Suporte", color = Color(0xFF1D2B53))
                        }
                    },
                    onClick = {
                        navigateTo("chatSuporte")
                        menuExpanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Logo do aplicativo
        Image(
            painter = painterResource(id = R.drawable.logo_cyber),
            contentDescription = "Logo",
            modifier = Modifier
                .size(150.dp),

            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Texto principal
        Text(
            text = "Ferramentas para sua segurança online.",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            color = Color(0xFF123456),
            modifier = Modifier
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Botões de navegação (layout 2x1)
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Primeira linha com dois botões lado a lado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão Gerar Senhas
                Button(
                    onClick = { navigateTo("gerarSenha") },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(120.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_key),
                            contentDescription = "Gerar Senhas",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Gerar Senhas", color = Color.White)
                    }
                }

                // Botão Verificação de Segurança
                // Botão Verificação de Segurança
                Button(
                    onClick = { navigateTo("VerificacaoTela") },  // Navega para a tela de verificação
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(120.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_shield),
                            contentDescription = "Verificação de Segurança",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Verificação de Segurança", color = Color.White)
                    }
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            // Segunda linha com um botão
            Button(
                onClick = { navigateTo("chatSuporte") },  // Navega para o chatbot
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_chat),
                        contentDescription = "Chat de Suporte",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Chat de Suporte", color = Color.White)
                }
            }
        }
    }

}