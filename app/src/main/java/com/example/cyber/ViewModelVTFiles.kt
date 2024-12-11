package com.example.cyber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
sealed class EstadoAnaliseArquivo {
    object Inicial : EstadoAnaliseArquivo()
    object Carregando : EstadoAnaliseArquivo()
    data class Sucesso(
        val estatisticas: Map<String, Int>,
        val arquivoEhSeguro: Boolean,
        val detalhesAntivirus: Map<String, String>
    ) : EstadoAnaliseArquivo()
    data class Erro(val mensagem: String) : EstadoAnaliseArquivo()
}

class ViewModelVirusTotalArquivo : ViewModel() {
    private val servicoVirusTotalArquivo = ServicoVirusTotalArquivo()
    private val _estadoAnalise = MutableStateFlow<EstadoAnaliseArquivo>(EstadoAnaliseArquivo.Inicial)
    val estadoAnalise: StateFlow<EstadoAnaliseArquivo> = _estadoAnalise

    fun analisarArquivo(caminhoArquivo: String) {
        Log.d("ViewModelVirusTotalArquivo", "Iniciando análise do arquivo: $caminhoArquivo")

        viewModelScope.launch {
            _estadoAnalise.value = EstadoAnaliseArquivo.Carregando

            when (val resultado = servicoVirusTotalArquivo.analisarArquivo(caminhoArquivo)) {
                is ServicoVirusTotalArquivo.ResultadoAnalise.Sucesso -> {
                    Log.d("ViewModelVirusTotalArquivo", "Análise concluída com sucesso")
                    _estadoAnalise.value = EstadoAnaliseArquivo.Sucesso(
                        resultado.estatisticas,
                        resultado.arquivoEhSeguro,
                        resultado.detalhesAntivirus
                    )
                }
                is ServicoVirusTotalArquivo.ResultadoAnalise.Erro -> {
                    Log.e("ViewModelVirusTotalArquivo", "Erro na análise: ${resultado.mensagem}")
                    _estadoAnalise.value = EstadoAnaliseArquivo.Erro(resultado.mensagem)
                }
            }
        }
    }
}