package com.example.pokeapp.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.R
import com.example.pokeapp.data.LoginRepository
import com.example.pokeapp.data.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>(false)
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch {
            val result = loginRepository.login(username, password)

            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
                _isLoggedIn.value = true
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
                _isLoggedIn.value = false
            }
        }
    }

    fun logout() {
        // can be launched in a separate asynchronous job
        Log.d("LogoutResource call", "menu");
        viewModelScope.launch {
            loginRepository.logout()
            _isLoggedIn.value = loginRepository.isLoggedIn
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}