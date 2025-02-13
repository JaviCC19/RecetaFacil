package com.myproject.JavierCifuentes.Presentation.ViewModels

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.JavierCifuentes.Data.local.DataStore.DataStoreManager
import com.myproject.JavierCifuentes.Presentation.States.LoginUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val dataStoreManager = DataStoreManager(context)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    //TODO: change the place for the valid credentiasl
    companion object {
        private const val VALID_EMAIL = "info@koalit.dev"
            private const val VALID_PASSWORD = "koalit123"
    }

    fun onUserNameChange(newUser: String) {
        _uiState.value = _uiState.value.copy(userName = newUser)
    }

    fun onPasswordChange(newPass: String) {
        _uiState.value = _uiState.value.copy(password = newPass)
    }

    fun login() {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (currentState.userName.isEmpty() || currentState.password.isEmpty()) {
                _uiState.value = currentState.copy(errorMessage = "Debe ingresar usuario y contraseña")
                return@launch
            }

            _uiState.value = currentState.copy(isLoading = true)

            delay(1000)

            if (currentState.userName == VALID_EMAIL && currentState.password == VALID_PASSWORD) {
                dataStoreManager.saveUserName(currentState.userName)
                _uiState.value = currentState.copy(loginSuccess = true, isLoading = false)
            } else {
                _uiState.value = currentState.copy(errorMessage = "Credenciales incorrectas", isLoading = false)
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}

