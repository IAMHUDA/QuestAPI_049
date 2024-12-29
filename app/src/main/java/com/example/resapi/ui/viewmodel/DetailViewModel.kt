package com.example.resapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resapi.model.Mahasiswa
import com.example.resapi.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException



import androidx.lifecycle.ViewModelProvider




class DetailViewModelFactory(private val repository: MahasiswaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailViewModel(private val mhs: MahasiswaRepository) : ViewModel() {

    // Mengambil data mahasiswa berdasarkan NIM
    fun getMahasiswabyNim(nim: String, onResult: (Mahasiswa?) -> Unit) {
        viewModelScope.launch {
            val mahasiswa = try {
                mhs.getMahasiswabyNim(nim)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(mahasiswa)
        }
    }


}
