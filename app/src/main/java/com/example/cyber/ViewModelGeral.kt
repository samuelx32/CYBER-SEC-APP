package com.example.cyber

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class VerificacaoItem(val tipo: String, val conteudo: String, val status: String)

class HistoricoGeralViewModel : ViewModel() { // ViewModel para gerenciar o histórico
    val historico = mutableStateListOf<VerificacaoItem>() // Lista de itens do histórico

    fun adicionarItem(tipo: String, conteudo: String, status: String) {
        historico.add(VerificacaoItem(tipo, conteudo, status))
    }

    fun removerItem(index: Int) {
        if (index in historico.indices) {
            historico.removeAt(index)
        }
    }
}



