package com.example.cyber

// ViewModelVirusTotal.kt
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class EstadoAnaliseUrl {
    object Inicial : EstadoAnaliseUrl()
    object Carregando : EstadoAnaliseUrl()
    data class Sucesso(val estatisticas: Map<String, Int>, val urlEhSegura: Boolean) : EstadoAnaliseUrl()
    data class Erro(val mensagem: String) : EstadoAnaliseUrl()
}

class ViewModelVirusTotal : ViewModel() {
    private val servicoVirusTotal = ServicoVirusTotal()
    private val _estadoAnalise = MutableStateFlow<EstadoAnaliseUrl>(EstadoAnaliseUrl.Inicial)
    val estadoAnalise: StateFlow<EstadoAnaliseUrl> = _estadoAnalise

    fun analisarUrl(url: String) {
        viewModelScope.launch {
            _estadoAnalise.value = EstadoAnaliseUrl.Carregando
            when (val resultado = servicoVirusTotal.analisarURL(url)) {
                is ServicoVirusTotal.ResultadoAnalise.Sucesso -> {
                    _estadoAnalise.value = EstadoAnaliseUrl.Sucesso(resultado.estatisticas, resultado.urlEhSegura)
                }
                is ServicoVirusTotal.ResultadoAnalise.Erro -> {
                    _estadoAnalise.value = EstadoAnaliseUrl.Erro(resultado.mensagem)
                }
            }
        }
    }
}