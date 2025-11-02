package com.example.pasteleriamilsaboresapp.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pasteleriamilsaboresapp.data.repository.AuthRepository

data class LoginViewModel(
    private val repo: AuthRepository = AuthRepository()
): ViewModel(){
    var uiState by mutableStateOf(LoginUiState())

    fun onUsernameChange(value:String){
        uiState=uiState.copy(username=value, error=null)
    }

    fun onPasswordChange(value: String){
        uiState=uiState.copy(password=value,error=null)
    }

    fun submit(onSucces:(String) -> Unit){
        uiState=uiState.copy(isLoading=true, error=null)

        val  oK=repo.login(uiState.username.trim(), uiState.password)

        if(oK) onSucces(uiState.username.trim())
        else uiState = uiState.copy(error="Credenciales invalidas")
    }
}
