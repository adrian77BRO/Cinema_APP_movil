package com.example.apptransaccional.home.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val _profileImageUri = MutableLiveData<Uri?>()
    val profileImageUri: LiveData<Uri?> get() = _profileImageUri

    private val sharedPreferences = application.getSharedPreferences("CinemAPP", Context.MODE_PRIVATE)

    init {
        loadProfileImage()
    }

    fun setProfileImage(uri: Uri) {
        _profileImageUri.postValue(uri)
        saveProfileImage(uri)
    }

    private fun saveProfileImage(uri: Uri) {
        sharedPreferences.edit().putString("profile_image", uri.toString()).apply()
    }

    private fun loadProfileImage() {
        val uriString = sharedPreferences.getString("profile_image", null)
        _profileImageUri.postValue(uriString?.let { Uri.parse(it) })
    }
}
