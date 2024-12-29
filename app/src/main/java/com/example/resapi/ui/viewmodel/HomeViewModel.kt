package com.example.resapi.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.resapi.model.Mahasiswa
import com.example.resapi.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeuiState{
    data class Success(
        val mahasiswa: List<Mahasiswa>
    ): HomeuiState()
    object Error: HomeuiState()
    object Loading: HomeuiState()
}

class HomeViewModel(
    private val mhs: MahasiswaRepository
    ): ViewModel()
    {
        var mhsUiState: HomeuiState by mutableStateOf(HomeuiState.Loading)
            private set

        init {
            getMhs()
        }

        fun getMhs(){
            viewModelScope.launch {
                mhsUiState = HomeuiState.Loading
                mhsUiState = try {
                    HomeuiState.Success(mhs.getMahasiswa())
                } catch (e: IOException){
                    HomeuiState.Error
                } catch (e: HttpException){
                    HomeuiState.Error
                }
            }
        }

        fun deleteMhs(nim: String){
            viewModelScope.launch {
                try {
                    mhs.deleteMahasiswa(nim)
                } catch (e: IOException){
                    HomeuiState.Error
                } catch (e: HttpException){
                    HomeuiState.Error
                }
            }
        }

    }
