package com.example.resapi.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resapi.model.Mahasiswa
import com.example.resapi.ui.navigation.DestinasiNavigasi
import com.example.resapi.ui.viewmodel.EditViewModel

object DestinasiEdit : DestinasiNavigasi {
    override val route = "edit"
    override val titleRes = "Edit Mahasiswa"
}
@Composable
fun EditScreen(
    nim: String,
    mahasiswa: Mahasiswa, // Parameter mahasiswa
    navigateBack: () -> Unit,
    onUpdateMahasiswa: (Mahasiswa) -> Unit
) {
    // Variabel untuk menyimpan input pengguna
    var name by remember { mutableStateOf(mahasiswa.nama) }
    var address by remember { mutableStateOf(mahasiswa.alamat) }
    var gender by remember { mutableStateOf(mahasiswa.jenisKelamin) }
    var classRoom by remember { mutableStateOf(mahasiswa.kelas) }
    var year by remember { mutableStateOf(mahasiswa.angkatan) }

    // Tampilan Form Edit
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit Mahasiswa", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") }
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Alamat") }
        )

        TextField(
            value = gender,
            onValueChange = { gender = it },
            label = { Text("Jenis Kelamin") }
        )

        TextField(
            value = classRoom,
            onValueChange = { classRoom = it },
            label = { Text("Kelas") }
        )

        TextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Angkatan") }
        )

        Button(onClick = {
            // Buat objek Mahasiswa baru untuk diperbarui
            val updatedMahasiswa = mahasiswa.copy(
                nama = name,
                alamat = address,
                jenisKelamin = gender,
                kelas = classRoom,
                angkatan = year
            )
            onUpdateMahasiswa(updatedMahasiswa) // Memanggil callback untuk memperbarui mahasiswa
            navigateBack() // Kembali setelah pembaruan
        }) {
            Text("Update")
        }
    }
}
