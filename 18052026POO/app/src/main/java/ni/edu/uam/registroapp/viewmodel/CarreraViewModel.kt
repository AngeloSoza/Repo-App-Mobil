package ni.edu.uam.registroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ni.edu.uam.registroapp.data.model.Carrera
import ni.edu.uam.registroapp.remote.RetrofitCliente

sealed class UiState{
    object Loading : UiState()
    data class Success(val data: List<Carrera>) : UiState()
    data class Error(val message: String) : UiState()
}

class CarreraViewModel: ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()


    init {
        cargarCarreras()
    }

    private fun cargarCarreras() {
        viewModelScope.launch {
            try {
                val lista = RetrofitCliente.api.listar()
                _uiState.value = UiState.Success(lista)
        } catch (e: Exception) {
            _uiState.value = UiState.Error(e.message ?: "Error desconocido")
        }
    }
    }

}
