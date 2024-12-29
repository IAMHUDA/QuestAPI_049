package com.example.resapi.containerapp

import com.example.resapi.repository.MahasiswaRepository



interface AppContainer{
    val kontakRepository: MahasiswaRepository
}

