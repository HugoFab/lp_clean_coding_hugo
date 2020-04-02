package fr.hugo.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.hugo.template.data.model.User
import fr.hugo.template.data.repository.UserRepository
import kotlinx.coroutines.launch


typealias OnSuccess<T> = (T) -> Unit

@Suppress("UNCHECKED_CAST")
class UserViewModel (private val repository: UserRepository) : ViewModel(){


    val userList = repository.getPaginatedList(viewModelScope)


    fun getUserById(id: String, onSuccess: OnSuccess<User?>) {
        viewModelScope.launch {
            repository.getUserDetails(id).run(onSuccess)
        }
    }

    companion object Factory : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(UserRepository.instance) as T
        }
    }



}