package com.example.resapi.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.resapi.dependenciesinjection.AppContainer
import com.example.resapi.model.Mahasiswa
import com.example.resapi.ui.view.DestinasiDetail
import com.example.resapi.ui.view.DestinasiEdit
import com.example.resapi.ui.view.DestinasiEntry
import com.example.resapi.ui.view.DestinasiHome
import com.example.resapi.ui.view.DetailScreen
import com.example.resapi.ui.view.EditScreen
import com.example.resapi.ui.view.EntryMhsScreen
import com.example.resapi.ui.view.HomeScreen
import com.example.resapi.ui.viewmodel.DetailViewModel
import com.example.resapi.ui.viewmodel.EditViewModel
import com.example.resapi.ui.viewmodel.PenyediaViewModel

@Composable
fun PengelolaHalaman(
    appContainer: AppContainer,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToltemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim -> navController.navigate("${DestinasiDetail.route}/$nim") },
                navigateToEdit = { nim -> navController.navigate("${DestinasiEdit.route}/$nim") } // Added Edit destination
            )
        }
        composable(DestinasiEntry.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetail.route + "/{nim}") { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim")
            if (nim != null) {
                DetailScreen(nim = nim, repository = appContainer.kontakRepository,navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }


        composable(DestinasiEdit.route + "/{nim}") { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("nim")
            if (nim != null) {
                // Dapatkan EditViewModel
                val editViewModel: EditViewModel = viewModel(factory = PenyediaViewModel.Factory)

                // Ambil mahasiswa berdasarkan NIM
                LaunchedEffect(nim) {
                    editViewModel.fetchMahasiswaByNim(nim)
                }

                // Ambil status mahasiswa dari ViewModel
                val mahasiswaState by editViewModel.mahasiswa.collectAsState(initial = null)

                if (mahasiswaState != null) {
                    // Pastikan mahasiswa tidak null sebelum mengirimkannya ke EditScreen
                    EditScreen(
                        nim = nim,
                        mahasiswa = mahasiswaState!!, // Kirimkan data mahasiswa yang telah diambil
                        navigateBack = {
                            navController.navigate(DestinasiHome.route) {
                                popUpTo(DestinasiHome.route) {
                                    inclusive = true
                                }
                            }
                        },
                        onUpdateMahasiswa = { updatedMahasiswa ->
                            editViewModel.updateMahasiswa(nim, updatedMahasiswa) // Menggunakan nim saat memperbarui
                        }
                    )
                } else {
                    // Tampilkan indikator loading
                    CircularProgressIndicator()
                }
            } else {
                // Handle error if NIM is null
                Text(text = "Error: NIM tidak ditemukan", style = MaterialTheme.typography.bodyMedium)
            }
        }


    }
}