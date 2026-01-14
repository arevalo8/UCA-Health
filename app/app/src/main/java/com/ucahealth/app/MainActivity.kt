package com.ucahealth.app


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.ucahealth.app.ui.sports.SportsViewModel
import com.ucahealth.app.ui.theme.AppTheme
class MainActivity : ComponentActivity() {

    private val viewModel: SportsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Se ejecuta una sola vez al entrar en pantalla
                    LaunchedEffect(Unit) {
                        viewModel.loadSports()
                    }

                    val sports by viewModel.sports.collectAsState()
                    val isLoading by viewModel.isLoading.collectAsState()
                    val error by viewModel.error.collectAsState()

                    Column {
                        if (isLoading) {
                            Text("Cargando...")
                        }

                        if (error != null) {
                            Text("Error: $error")
                        }

                        LazyColumn {
                            items(sports) { sport ->
                                Text("${sport.name} - ${sport.description}")
                            }
                        }
                    }
                }
            }
        }
    }
}

