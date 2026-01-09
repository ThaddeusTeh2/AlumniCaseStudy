package com.dx.alumnicasestudy.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dx.alumnicasestudy.data.domain.models.User
import com.dx.alumnicasestudy.data.domain.repo.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repo: AuthRepository,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _isCurrentUser = MutableStateFlow(false)
    val isCurrentUser = _isCurrentUser.asStateFlow()

    fun loadUser(userId: String?) {
        viewModelScope.launch {
            val idToLoad = userId ?: auth.currentUser?.uid

            _isCurrentUser.value = (userId == null || userId == auth.currentUser?.uid)

            if (idToLoad != null) {

                _user.value = null

                _user.value = repo.getUserById(idToLoad)
            } else {
                _user.value = null
            }
        }
    }
}