import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.API.InteresSimpleRequest
import com.example.myapplication.API.InteresSimpleResponse
import com.example.myapplication.API.RetrofitClient
import kotlinx.coroutines.launch

class InteresViewModel : ViewModel() {
    private val _interesSimpleResponse = MutableLiveData<InteresSimpleResponse?>()
    val interesSimpleResponse: LiveData<InteresSimpleResponse?> = _interesSimpleResponse

    private val _interesCompuestoResponse = MutableLiveData<InteresSimpleResponse?>()
    val interesCompuestoResponse: LiveData<InteresSimpleResponse?> = _interesCompuestoResponse

    fun calcularInteresSimple(
        tasaInteres: String,
        tiempo: String,
        valorFinal: String,
        valorPresente: String
    ) {
        viewModelScope.launch {
            try {
                val request = InteresSimpleRequest(
                    tasaInteres.toDouble(),
                    tiempo.toDouble(),
                    valorFinal.toDouble(),
                    valorPresente.toDouble()
                )
                val response = RetrofitClient.apiService.calcularInteres(request)
                println(response.body())
                if (response.isSuccessful) {
                    _interesSimpleResponse.postValue(response.body())
                } else {
                    println(request)
                    println("Error en la API (Interés Simple): ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al llamar a la API (Interés Simple): ${e.message}")
            }
        }
    }

    fun calcularInteresCompuesto(
        tasaInteres: String,
        tiempo: String,
        valorFinal: String,
        valorPresente: String
    ) {
        viewModelScope.launch {
            try {
                val request = InteresSimpleRequest(
                    tasaInteres.toDouble(),
                    tiempo.toDouble(),
                    valorFinal.toDouble(),
                    valorPresente.toDouble()
                )

                val response = RetrofitClient.apiService.calcularInteresCompuesto(request)

                if (response.isSuccessful) {
                    _interesCompuestoResponse.postValue(response.body())
                } else {
                    println("Error en la API (Interés Compuesto): ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                println("Error al llamar a la API (Interés Compuesto): ${e.message}")
            }
        }
    }
}
