package com.example.api_practice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val YOUR_API_KEY = ""
object ApiService {
    private const val BASE_URL = "https://api.openai.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openAIApi: OpenAIApi = retrofit.create(OpenAIApi::class.java)
}

interface OpenAIApi {
    @Headers("Content-Type: application/json", "Authorization: Bearer $YOUR_API_KEY")
    @POST("v1/chat/completions")
    suspend fun generateResponse(@Body requestBody: Request): Response
}

data class Request(
    val model: String = "gpt-4",
    val messages: List<Message>,
    val max_tokens: Int = 3000,
    val temperature: Double = 0.7
)

data class Response(
    val choices: List<ResponseMessage>
)

data class ResponseMessage(
    val message: Message
)