package com.example.api_practice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

const val YOUR_API_KEY = ""
object ApiService {
    private const val BASE_URL = "https://api.openai.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openAIApi: OpenAIApi = retrofit.create(OpenAIApi::class.java)
}

interface OpenAIApi {
    @Headers("Content-Type: application/json")


    @POST("v1/images/generations")
    suspend fun generateImage(@Header("Authorization") authHeader: String, @Body requestBody: ImgRequest): ImgResponse
    @POST("v1/chat/completions")
    suspend fun generateResponse(@Header("Authorization") authHeader: String, @Body requestBody: Request): Response
}

data class Request(
    val model: String = "gpt-4",
    val messages: List<Message>,
    val max_tokens: Int = 3000,
    val temperature: Double = 0.7
)

data class Response(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class ImgRequest(
    val prompt: String,
    val n: Int = 1,
    val size: String = "1024x1024"
)

data class ImgResponse(
    val data: List<ImgMessage>
)