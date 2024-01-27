package com.czech.swapi


import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.czech.swapi.repository.model.Person
import com.czech.swapi.ui.theme.SwapiTheme

class DetailsActivity : ComponentActivity() {
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("CUSTOM_ID", -1)
        viewModel.getPersonDetails(id)

        setContent {
            SwapiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val uiState = viewModel.immutableData.observeAsState(UiState()).value

                    when {
                        uiState.isLoading -> {
                            MyLoadingView()
                        }

                        uiState.error != null -> {
                            MyErrorView(uiState.error)
                        }

                        uiState.data != null -> {
                            uiState.data?.let { DetailsView(it) }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun DetailsView(person: Person) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.luke),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "Name: ${person.name}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text("Height: ${person.height}")
        Text("Mass: ${person.mass}")
        Text("Hair Color: ${person.hair_color}")
        Text("Skin Color: ${person.skin_color}")
        Text("Eye Color: ${person.eye_color}")
        Text("Birth Year: ${person.birth_year}")
        Text("Gender: ${person.gender}")
        Text("Homeworld: ${person.homeworld}")
    }
}