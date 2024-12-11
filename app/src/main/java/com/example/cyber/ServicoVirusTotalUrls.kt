package com.example.cyber

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ServicoVirusTotal {
    private val TAG = "ServicoVirusTotal"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val baseUrl = "https://www.virustotal.com/api/v3"
    private val apiKey = "0650f0c6354e1bb93cfb5764762a24a6005e52bdff5f7fe25bb8e09fdb398ca0"

    data class ResultadoDetalhado(
        val urlEhSegura: Boolean,
        val estatisticasAnalise: Map<String, Int>,
        val estatisticasReport: Map<String, Int>,
        val ultimaAnalise: String,
        val reputacao: Int,
        val totalVotos: Map<String, Int>
    )


    sealed class ResultadoAnalise {
        data class Sucesso(val estatisticas: Map<String, Int>, val urlEhSegura: Boolean) : ResultadoAnalise()
        data class Erro(val mensagem: String) : ResultadoAnalise()
    }

    suspend fun analisarURL(url: String): ResultadoAnalise = withContext(Dispatchers.IO) {
        Log.d(TAG, "Iniciando análise da URL: $url")
        try {
            if (apiKey == "") {
                Log.e(TAG, "Chave API não configurada")
                return@withContext ResultadoAnalise.Erro("Chave API do VirusTotal não configurada")
            }
            // Primeira requisição para enviar URL para análise
            Log.d(TAG, "Enviando URL para análise")
            val respostaAnalise = enviarUrlParaAnalise(url)
            val idAnalise = extrairIdAnalise(respostaAnalise)
            Log.d(TAG, "ID da análise obtido: $idAnalise")

            // Aguarda a análise ser completada
            Log.d(TAG, "Aguardando processamento da análise...")
            delay(5000) // Espera 5 segundos antes de obter resultados

            // Segunda requisição para obter resultados da análise
            Log.d(TAG, "Obtendo resultado da análise")
            val resultadoAnalise = obterRelatorioAnalise(idAnalise)
            return@withContext processarResultadoAnalise(resultadoAnalise)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao analisar URL: ${e.message}", e)
            return@withContext ResultadoAnalise.Erro("Erro ao analisar URL: ${e.message}")
        }
    }

    private fun enviarUrlParaAnalise(url: String): String {
        try {
            val urlFormatada = if (!url.startsWith("http")) "https://$url" else url
            Log.d(TAG, "URL formatada para análise: $urlFormatada")

            val mediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()
            val body = "url=$urlFormatada".toRequestBody(mediaType)

            val request = Request.Builder()
                .url("$baseUrl/urls")
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("x-apikey", apiKey)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build()

            return client.newCall(request).execute().use { resposta ->
                Log.d(TAG, "Resposta do envio - Código: ${resposta.code}")

                if (!resposta.isSuccessful) {
                    val erro = "Resposta inesperada ${resposta.code}"
                    Log.e(TAG, erro)
                    throw IOException(erro)
                }

                val responseBody = resposta.body?.string()
                Log.d(TAG, "Resposta do envio recebida: $responseBody")

                responseBody ?: throw IOException("Corpo da resposta vazio")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao enviar URL para análise", e)
            throw e
        }
    }

    private fun obterRelatorioAnalise(idAnalise: String): String {
        val request = Request.Builder()
            .url("$baseUrl/analyses/$idAnalise")
            .get()
            .addHeader("accept", "application/json")
            .addHeader("x-apikey", apiKey)
            .build()

        return client.newCall(request).execute().use { resposta ->
            Log.d(TAG, "Resposta do relatório - Código: ${resposta.code}")

            if (!resposta.isSuccessful) {
                val erro = "Resposta inesperada ${resposta.code}"
                Log.e(TAG, erro)
                throw IOException(erro)
            }

            val responseBody = resposta.body?.string()
            Log.d(TAG, "Relatório recebido: $responseBody")

            responseBody ?: throw IOException("Corpo da resposta vazio")
        }
    }

    private fun extrairIdAnalise(resposta: String): String {
        val jsonResposta = JSONObject(resposta)
        return jsonResposta.getJSONObject("data").getString("id")
    }

    private fun processarResultadoAnalise(resposta: String): ResultadoAnalise {
        val jsonResposta = JSONObject(resposta)
        val atributos = jsonResposta.getJSONObject("data").getJSONObject("attributes")
        val estatisticas = atributos.getJSONObject("stats")

        val malicioso = estatisticas.optInt("malicious", 0)
        val suspeito = estatisticas.optInt("suspicious", 0)
        val inofensivo = estatisticas.optInt("harmless", 0)
        val naoDetectado = estatisticas.optInt("undetected", 0)

        val mapaEstatisticas = mapOf(
            "malicioso" to malicioso,
            "suspeito" to suspeito,
            "inofensivo" to inofensivo,
            "naoDetectado" to naoDetectado
        )

        Log.d(TAG, "Estatísticas processadas: $mapaEstatisticas")

        // Considera URL segura se não houver detecções maliciosas ou suspeitas
        val urlEhSegura = malicioso == 0 && suspeito == 0
        Log.d(TAG, "URL considerada segura: $urlEhSegura")

        return ResultadoAnalise.Sucesso(mapaEstatisticas, urlEhSegura)
    }
}