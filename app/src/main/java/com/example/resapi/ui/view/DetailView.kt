package com.example.resapi.ui.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.resapi.model.Mahasiswa
import com.example.resapi.repository.MahasiswaRepository
import com.example.resapi.ui.customwidget.CostumeTopAppBar
import com.example.resapi.ui.navigation.DestinasiNavigasi
import com.example.resapi.ui.viewmodel.DetailViewModel
import com.example.resapi.ui.viewmodel.DetailViewModelFactory

object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(nim: String,
                 repository: MahasiswaRepository,
                 navigateBack: () -> Unit,
                 modifier: Modifier = Modifier) {
    val viewModel: DetailViewModel = viewModel(factory = DetailViewModelFactory(repository))

    var mahasiswa by remember { mutableStateOf<Mahasiswa?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    LaunchedEffect(nim) {
        viewModel.getMahasiswabyNim(nim) { result ->
            mahasiswa = result
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetail.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (mahasiswa != null) {
                DetailContent(mahasiswa = mahasiswa!!, modifier = Modifier.padding(innerPadding))
            } else {
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                    Text("Mahasiswa tidak ditemukan")
                }
            }
        }
    }
}


@Composable
fun DetailContent(mahasiswa: Mahasiswa, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(Color(0xFFFFFAC5)), // Warna latar belakang FAFFC5
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, Color(0xFF000000)) // Border hitam
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                ,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "NIM: ${mahasiswa.nim}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Nama: ${mahasiswa.nama}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Jenis Kelamin: ${mahasiswa.jenisKelamin}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Angkatan: ${mahasiswa.angkatan}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Kelas: ${mahasiswa.kelas}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Alamat: ${mahasiswa.alamat}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
