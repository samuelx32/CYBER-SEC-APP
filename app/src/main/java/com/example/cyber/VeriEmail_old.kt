package com.example.cyber

/*
@Composable
fun VerificacaoEmailScreen(navigateTo: (String) -> Unit) {
    val email by remember { mutableStateOf("") }
    val conteudo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD4D8E2))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Título
        Text(
            text = "Insira as informações do email recebido",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F),
            modifier = Modifier.padding(bottom = 32.dp),
            textAlign = TextAlign.Center
        )

        /*Text(
            text = "Insira o e-mail e o conteúdo para análise.",
            color = Color(0xFF123456),
            fontSize = 14.sp,
            modifier = Modifier
                .padding(bottom = 32.dp)
        )

         */

        // Campos de entrada
        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Insira o email do remetente...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Insira o conteúdo do email...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Insira quaisquer links anexados ao email...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Botão para verificar o e-mail
        Button(
            onClick = {
                // Lógica de verificação de e-mail
                if (verificarEmail(email, conteudo)) {
                    navigateTo("resultadoSeguro")
                } else {
                    navigateTo("resultadoAlerta")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1D2B53)),
        ) {
            Text(
                text = "Verificar E-Mail",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        // Texto explicativo
        Text(
            text = "Com base nas informações inseridas, verificações\n" +
                    "de segurança serão feitas, para determinar\n" +
                    "a segurança do email.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF37474F),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

// Função de verificação de e-mail (simulação)
fun verificarEmail(email: String, conteudo: String): Boolean {
    val dominiosConfiaveis = listOf(
        "gmail.com", "outlook.com", "yahoo.com", // Provedores populares
        "bradesco.com.br", "itau.com.br", "santander.com.br", "bb.com.br", "caixa.gov.br", // Bancos
        "nubank.com.br", "inter.com", "original.com.br", "banco.bradesco", "sicredi.com.br", // Bancos digitais e cooperativas
        "amazon.com", "apple.com", "facebook.com", "google.com", "microsoft.com", "twitter.com", "linkedin.com", // Big Techs
        "steam.com", "playstation.com", "xbox.com", "nintendo.com", "epicgames.com", "riotgames.com", // Empresas de jogos
        "gov.br", "senado.leg.br", "camara.leg.br", "jus.br" // Instituições governamentais no Brasil
    )

    val palavrasMaliciosas = listOf("oferta", "dinheiro", "urgente", "clique", "promoção", "grátis", "presente", "ganhe", "desconto", "prêmio")

    // Verificar domínio do remetente
    val dominio = email.substringAfterLast("@")
    if (!dominiosConfiaveis.contains(dominio)) {
        return false
    }

    // Verificar palavras maliciosas no conteúdo
    val score = palavrasMaliciosas.count { conteudo.contains(it, ignoreCase = true) }
    return score < 2 // Ajuste o valor conforme necessário
}



// Tela verde (segura)
@Composable
fun ResultadoSeguroScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Boas Notícias!", color = Color.White, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Nenhuma Violação encontrada!", color = Color.White, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navigateTo("home") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Voltar", color = Color(0xFF4CAF50))
        }
    }
}

// Tela preta de alerta (suspeito)
@Composable
fun ResultadoAlertaScreen(navigateTo: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("ALERT!", color = Color.Red, fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("O E-Mail inserido aparenta ser uma tentativa de golpe!", color = Color.White, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Recomenda-se bloquear o remetente.", color = Color.White, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = { navigateTo("home") },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Voltar", color = Color.Black)
        }
    }
}
*/