package com.example.myapplication.API

import com.example.myapplication.API.Anualidades.AnualidadesRequest
import com.example.myapplication.API.Anualidades.UniValorResponse
import com.example.myapplication.API.GradienteAritmetico.GradienteRequest
import com.example.myapplication.API.Login.LoginRequest
import com.example.myapplication.API.Login.LoginResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
   @POST("simple")
   suspend fun calcularInteres(@Body request: InteresSimpleRequest): Response<InteresSimpleResponse>
   @POST("compuesto")
   suspend fun calcularInteresCompuesto(@Body request: InteresSimpleRequest): Response<InteresSimpleResponse>
   @POST("anualidades/valorFinal")
   suspend fun calcularAnualidades(@Body request: AnualidadesRequest): Response<UniValorResponse>
   @POST("gradientes/presente")
   suspend fun calcularGradientePresente(@Body request: GradienteRequest): Response<UniValorResponse>
   @POST("gradientes/futuro")
   suspend fun calcularGradienteFuturo(@Body request: GradienteRequest): Response<UniValorResponse>
   @POST("auth")
   suspend fun iniciarSesion(@Body request: LoginRequest): Response<LoginResponse>
}

object RetrofitClient {
   private const val BASE_URL = "http://10.0.2.2:8080/"

   val apiService: ApiService = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(ApiService::class.java)
}