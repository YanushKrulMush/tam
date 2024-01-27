@file:OptIn(ExperimentalMaterial3Api::class)

package com.czech.swapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.czech.swapi.repository.model.Person
import com.czech.swapi.ui.theme.SwapiTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getData()

        setContent {
            SwapiTheme {
                Scaffold(
                    topBar = { MyTopView(viewModel = viewModel) }
                ) {
                    MainView(
                        modifier = Modifier.padding(it),
                        viewModel,
                        onClick = { id -> navigateToDetailsActivity(id) })
                }
            }
        }
    }

    fun navigateToDetailsActivity(id: Int) {
        val detailsIntent = Intent(this, DetailsActivity::class.java)
        detailsIntent.putExtra("CUSTOM_ID", id)
        startActivity(detailsIntent)
    }
}

@Composable
fun MainView(modifier: Modifier, viewModel: MainViewModel, onClick: (Int) -> Unit) {
    val uiState = viewModel.immutableData.observeAsState(UiState()).value
    val query = viewModel.filterQuery.observeAsState("").value

    when {
        uiState.isLoading -> {
            MyLoadingView()
        }

        uiState.error != null -> {
            MyErrorView(uiState.error)
        }

        uiState.data != null -> {
            uiState.data?.let { allPeople ->
                MyListView(modifier, people = allPeople.filter{ it.name.contains(query, true)}, onClick)
            }
        }
    }
}

@Composable
fun MyErrorView(message: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Red,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun MyLoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun MyListView(modifier: Modifier, people: List<Person>, onClick: (Int) -> Unit) {
    LazyColumn(modifier = modifier) {

        itemsIndexed(people) { index, person ->
            CharacterDetailsScreen(person, onClick, index + 1)
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopView(viewModel: MainViewModel) {
    var searchText by remember { mutableStateOf("") }

    TopAppBar(
        title = { Text(text = "Star Wars Characters") },
        actions = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = searchText,
                onQueryChange = { newText -> searchText = newText },
                onSearch = { viewModel.updateFilterQuery(it) },
                placeholder = { Text(text = "Wyszukaj...") },
                active = false,
                onActiveChange = { },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                },
                trailingIcon = {
                    Image(
                        modifier = Modifier.clickable {
                            searchText = ""
                            viewModel.updateFilterQuery("")
                        },
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear"
                    )
                }
            ) {

            }
        }
    )
}


@Composable
fun CharacterDetailsScreen(character: Person, onClick: (Int) -> Unit, id: Int) {

    Row(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { onClick.invoke(id) }
                .align(Alignment.CenterVertically)
        ) {
            DetailItem("Name", character.name)
            DetailItem("Height", character.height)
            DetailItem("Mass", character.mass)
            DetailItem("Gender", character.gender)
        }
        val imageResourceIds = listOf(
            R.drawable.boba,
            R.drawable.yoda,
            R.drawable.ball,
            R.drawable.guy,
            R.drawable.luke
        )
        val random = java.util.Random()
        val randomIndex = random.nextInt(imageResourceIds.size)
        Image(
            painter = painterResource(id = imageResourceIds[randomIndex]),
            contentDescription = null,
            modifier = Modifier
                .size(220.dp)
                .padding(start = 16.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Text(
        text = "$label: $value",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val character = Person(
        name = "Luke Skywalker",
        height = "172",
        mass = "77",
        hair_color = "blond",
        skin_color = "fair",
        eye_color = "blue",
        birth_year = "19BBY",
        gender = "male",
        homeworld = "Tatooine",
        films = emptyList(),
        species = emptyList(),
        vehicles = emptyList(),
        starships = emptyList(),
    )
    val doNothing: (Int) -> Unit = { _ -> }
    CharacterDetailsScreen(character, doNothing, 1)
}