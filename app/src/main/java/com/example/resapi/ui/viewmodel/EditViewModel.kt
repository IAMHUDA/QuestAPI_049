package com.example.resapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resapi.model.Mahasiswa
import com.example.resapi.repository.MahasiswaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditViewModel(private val repository: MahasiswaRepository) : ViewModel() {
    private val _mahasiswa = MutableStateFlow<Mahasiswa?>(null)
    val mahasiswa: StateFlow<Mahasiswa?> = _mahasiswa

    // Fungsi untuk mengambil mahasiswa berdasarkan NIM
    fun fetchMahasiswaByNim(nim: String) {
        viewModelScope.launch {
            try {
                val fetchedMahasiswa = repository.getMahasiswabyNim(nim)
                _mahasiswa.value = fetchedMahasiswa
            } catch (e: Exception) {
                _mahasiswa.value = null // Tangani kesalahan jika diperlukan
            }
        }
    }

    // Fungsi untuk memperbarui mahasiswa
    fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                repository.updateMahasiswa(nim, mahasiswa)
            } catch (e: Exception) {
                // Tangani kesalahan jika diperlukan
            }
        }
    }
}
