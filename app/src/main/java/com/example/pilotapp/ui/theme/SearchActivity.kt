package com.example.pilotapp.ui.theme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pilotapp.MainViewModel
import com.example.pilotapp.R
import com.example.pilotapp.ui.theme.ui.theme.PilotAppTheme

class SearchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilotAppTheme {
                SearchBar()
            }
        }
    }
}

@Composable fun SearchBar() {
    val viewModel = viewModel<MainViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val persons by viewModel.persons.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
        )
        {
            val filter_icon = ImageVector.vectorResource(id = R.drawable.filter)
            val Search_icon = ImageVector.vectorResource(id = R.drawable.searchicon)
            Icon(
                imageVector = filter_icon, contentDescription = null,
                Modifier
                    .padding(3.dp)
                    .padding(vertical = 10.dp)
            )
            OutlinedTextField(
                value = searchText,
                onValueChange = viewModel::onSearchChange,
                Modifier
                    .padding(3.dp)
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    fontSize = 17.sp,
                    //lineHeight =,
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Search_icon,
                        contentDescription = null,
                        Modifier.size(20.dp)
                    )
                },
                placeholder = { Text(text = "Search Courses") },
                colors = TextFieldDefaults.outlinedTextFieldColors(cursorColor = Color(0xff80ed99))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        if (isSearching) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            LazyColumn(Modifier.fillMaxWidth())
            {
                items(persons)
                { person ->
                    Text(
                        text = "${person.firstname} ${person.lastname}",
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }
        }
    }
}

