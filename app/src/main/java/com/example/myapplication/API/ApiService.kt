package com.example.myapplication.API

import com.example.myapplication.API.Amortizacion.AmortizacionRequest
import com.example.myapplication.API.Amortizacion.AmortizacionResponse
import com.example.myapplication.API.Anualidades.AnualidadesRequest
import com.example.myapplication.API.Anualidades.UniValorResponse
import com.example.myapplication.API.Bonos.BonosRequest
import com.example.myapplication.API.Bonos.BonosResponse
import com.example.myapplication.API.Capitalizaciones.CapitalizacionColectivaRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionIndividualRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionMixtoRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionSegurosRequest
import com.example.myapplication.API.Capitalizaciones.CapitalizacionSegurosResponse
import com.example.myapplication.API.GradienteAritmetico.GradienteRequest
import com.example.myapplication.API.GradienteGeometrico.GradienteGRequest
import com.example.myapplication.API.Inflacion.InflacionRequest
import com.example.myapplication.API.Login.LoginRequest
import com.example.myapplication.API.Login.LoginResponse
import com.example.myapplication.API.Registro.UserRegisterRequestDTO
import com.example.myapplication.API.Registro.UserRegisterResponse
import com.example.myapplication.API.Tir.TirContableRequest
import com.example.myapplication.API.Tir.TirModificadaRequest
import com.example.myapplication.API.Tir.TirModificadaResponse
import com.example.myapplication.API.Tir.TirRealRequest
import com.example.myapplication.API.Tir.TirSimpleRequest
import com.example.myapplication.API.Tir.TirSimpleResponse
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
   @POST("token/login")
   suspend fun iniciarSesion(@Body request: LoginRequest): Response<LoginResponse>
   @POST("tirsimple")
   suspend fun calcularTirSimple(@Body request: TirSimpleRequest): Response<TirSimpleResponse>
   @POST("tirreal")
   suspend fun calcularTirReal(@Body request: TirRealRequest): Response<Double>
   @POST("tircontable")
   suspend fun calcularTirContable(@Body request: TirContableRequest): Response<Double>
   @POST("tirmodificada")
   suspend fun calcularTirModificada(@Body request: TirModificadaRequest): Response<TirModificadaResponse>
   @POST("gradientegeometrico/presente")
   suspend fun calcularValorPresente(@Body request: GradienteGRequest): Response<Double>
   @POST("gradientegeometrico/futuro")
   suspend fun calcularValorFuturo(@Body request: GradienteGRequest): Response<Double>
   @POST("inflacion")
   suspend fun calcularInflacion(@Body request: InflacionRequest): Response<Double>
   @POST("bonos")
   suspend fun calcularBono(@Body request: BonosRequest): Response<BonosResponse>
   @POST("colectiva")
   suspend fun capitalColectiva(@Body request: CapitalizacionColectivaRequest): Response<Double?>
   @POST("individual")
   suspend fun capitalIndividual(@Body request: CapitalizacionIndividualRequest): Response<Double?>
   @POST("mixto")
   suspend fun capitalMixta(@Body request: CapitalizacionMixtoRequest): Response<Double?>
   @POST("seguros")
   suspend fun capitalSeguros(@Body request: CapitalizacionSegurosRequest): Response<CapitalizacionSegurosResponse>
   @POST("amortizacion/francesa")
   suspend fun calcularFrancesa(@Body request: AmortizacionRequest): Response<AmortizacionResponse>

   @POST("amortizacion/alemana")
   suspend fun calcularAlemana(@Body request: AmortizacionRequest): Response<AmortizacionResponse>

   @POST("amortizacion/americana")
   suspend fun calcularAmericana(@Body request: AmortizacionRequest): Response<AmortizacionResponse>

   @POST("token/register")
   suspend fun registrarse(@Body request: UserRegisterRequestDTO): Response<UserRegisterResponse>
}

object RetrofitClient {
   private const val BASE_URL = "http://192.168.2.10:8080/"

   val apiService: ApiService = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()
      .create(ApiService::class.java)
}