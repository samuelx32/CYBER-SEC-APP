package com.example.cyber

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ServicoVirusTotalArquivo {
    private val TAG = "ServicoVirusTotalArquivo"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)     // Tempo maior para upload de arquivos
        .writeTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()

    private val baseUrl = "https://www.virustotal.com/api/v3"
    private val apiKey = "0650f0c6354e1bb93cfb5764762a24a6005e52bdff5f7fe25bb8e09fdb398ca0" // Substitua pela sua chave API do VirusTotal

    sealed class ResultadoAnalise {
        data class Sucesso(
            val estatisticas: Map<String, Int>,
            val arquivoEhSeguro: Boolean,
            val detalhesAntivirus: Map<String, String>
        ) : ResultadoAnalise()
        data class Erro(val mensagem: String) : ResultadoAnalise()
    }

    suspend fun analisarArquivo(caminhoArquivo: String): ResultadoAnalise = withContext(Dispatchers.IO) {
        Log.d(TAG, "Iniciando análise do arquivo: $caminhoArquivo")
        try {
            if (apiKey == "SUA_CHAVE_API_AQUI") {
                Log.e(TAG, "Chave API não configurada")
                return@withContext ResultadoAnalise.Erro("Chave API do VirusTotal não configurada")
            }

            val arquivo = File(caminhoArquivo)
            if (!arquivo.exists()) {
                Log.e(TAG, "Arquivo não encontrado: $caminhoArquivo")
                return@withContext ResultadoAnalise.Erro("Arquivo não encontrado")
            }

            // Primeira requisição para enviar arquivo para análise
            Log.d(TAG, "Enviando arquivo para análise")
            val respostaUpload = enviarArquivoParaAnalise(arquivo)
            val idAnalise = extrairIdAnalise(respostaUpload)
            Log.d(TAG, "ID da análise obtido: $idAnalise")

            // Aguarda a análise ser completada
            Log.d(TAG, "Aguardando processamento da análise...")
            delay(10000) // Espera 10 segundos antes de obter resultados

            // Segunda requisição para obter resultados da análise
            Log.d(TAG, "Obtendo resultado da análise")
            val resultadoAnalise = obterRelatorioAnalise(idAnalise)
            return@withContext processarResultadoAnalise(resultadoAnalise)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao analisar arquivo: ${e.message}", e)
            return@withContext ResultadoAnalise.Erro("Erro ao analisar arquivo: ${e.message}")
        }
    }

    private fun enviarArquivoParaAnalise(arquivo: File): String {
        try {
            Log.d(TAG, "Preparando upload do arquivo: ${arquivo.name}")

            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(
                    "file",
                    arquivo.name,
                    arquivo.asRequestBody("application/octet-stream".toMediaTypeOrNull())
                )
                .build()

            val request = Request.Builder()
                .url("$baseUrl/files")
                .post(requestBody)
                .addHeader("accept", "application/json")
                .addHeader("x-apikey", apiKey)
                .build()

            return client.newCall(request).execute().use { resposta ->
                Log.d(TAG, "Resposta do upload - Código: ${resposta.code}")

                if (!resposta.isSuccessful) {
                    val erro = "Resposta inesperada ${resposta.code}"
                    Log.e(TAG, erro)
                    throw IOException(erro)
                }

                val responseBody = resposta.body?.string()
                Log.d(TAG, "Resposta do upload recebida: $responseBody")

                responseBody ?: throw IOException("Corpo da resposta vazio")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao enviar arquivo para análise", e)
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
        val resultados = atributos.getJSONObject("results")

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

        // Coleta resultados de cada antivírus
        val detalhesAntivirus = mutableMapOf<String, String>()
        resultados.keys().forEach { antivirusNome ->
            val resultado = resultados.getJSONObject(antivirusNome)
            val categoria = resultado.optString("category", "não detectado")
            detalhesAntivirus[antivirusNome] = categoria
        }

        Log.d(TAG, "Estatísticas processadas: $mapaEstatisticas")
        Log.d(TAG, "Detalhes dos antivírus: $detalhesAntivirus")

        // Considera arquivo seguro se não houver detecções maliciosas ou suspeitas
        val arquivoEhSeguro = malicioso == 0 && suspeito == 0
        Log.d(TAG, "Arquivo considerado seguro: $arquivoEhSeguro")

        return ResultadoAnalise.Sucesso(mapaEstatisticas, arquivoEhSeguro, detalhesAntivirus)
    }
}