package com.example.mvvmsample.ui.home.profile

import androidx.lifecycle.ViewModel
import com.example.mvvmsample.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {

    val user = repository.getUser()

}