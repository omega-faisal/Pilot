package com.example.pilotapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    val _searchText = MutableStateFlow("")

    //this is the one which we will show to the user
    val searchText = _searchText.asStateFlow()

    val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _persons = MutableStateFlow(AllPersons)
    // TODO -this will be the thing which will contain all the courses...
    // this can be stored in a local database or can be taken from an API

    @OptIn(FlowPreview::class)
    val persons =
        searchText//.debounce(1000L)
            .onEach { _isSearching.update { true } }.combine(_persons)
            // this debounce here provides a slight delay in the execution of the block given below...
            //so that when we take data from api we don't want to change the persons variable after every word typed by the user
            { text, persons ->
                if (text.isBlank()) {
                    persons
                } else {
                    delay(1000L) // this is artificial delay
                    //TODO- here we will have to make a network call or database call and then filter the results based
                    // on search text....
                    persons.filter {
                        (it.DoesMatchSearchQuery(text))
                    }
                }
            }.onEach { _isSearching.update { false } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _persons.value)

    fun onSearchChange(text: String) {
        _searchText.value = text

    }

}

data class Person(
    val firstname: String,
    val lastname: String
) {
    fun DoesMatchSearchQuery(query: String): Boolean {
        val matchingCombinations = listOf(
            "${firstname}${lastname}",
            "${firstname} ${lastname}",
            "${firstname.first()}${lastname.first()}"
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }

}

private val AllPersons = listOf(
    Person("Stock", "Market"),
    Person("Loss", "Profit"),
    Person("buy", "sell"),
    Person("next", "Previous"),
    Person("trade", "equity")

)