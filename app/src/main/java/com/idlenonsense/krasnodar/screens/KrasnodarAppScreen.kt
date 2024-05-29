@file:OptIn(ExperimentalMaterial3Api::class)

package com.idlenonsense.krasnodar.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Attractions
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.idlenonsense.krasnodar.data.Category
import com.idlenonsense.krasnodar.data.Place
import com.idlenonsense.krasnodar.data.foodPlaces
import com.idlenonsense.krasnodar.data.funPlaces
import com.idlenonsense.krasnodar.data.hotelPlaces
import com.idlenonsense.krasnodar.data.visitPlaces
import com.idlenonsense.krasnodar.ui.KrasnodarViewModel
import com.idlenonsense.krasnodar.ui.theme.KrasnodarTheme

@Composable
fun KrasnodarApp(
    windowSize: WindowWidthSizeClass
) {
    if (windowSize == WindowWidthSizeClass.Compact) {
        KrasnodarCompactDevice()
    } else {
        KrasnodarExpandedDevice()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KrasnodarCompactDevice(
    appViewModel: KrasnodarViewModel = viewModel()
) {
    val appState by appViewModel.uiState.collectAsState()
    val currentCategory = appState.currentCategory
    Scaffold(
        bottomBar = {
            if (appState.isShowingListPage) {
                HorizontalNavBar(appViewModel = appViewModel)
            }
                    },
        topBar = { TopBar(
            currentCategory,
            isShowingListPage = appState.isShowingListPage,
            currentPlace = appState.currentPlace,
            onClick = { appViewModel.navigateToListPage() }
        ) }
    ) { paddingValues ->
        if (appState.isShowingListPage) {
            PlaceCardList(
                placesList = when (appState.currentCategory) {
                    Category.HOTELS -> hotelPlaces
                    Category.VISIT -> visitPlaces
                    Category.FOOD -> foodPlaces
                    else -> funPlaces
                                                             },
                onClick = {
                    appViewModel.changeCurrentPlace(it)
                    appViewModel.navigateToDetailPage()
                },
                contentPadding = paddingValues
            )
        } else {
            PlaceDetailsScreen(
                place = appState.currentPlace,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun KrasnodarExpandedDevice(
    appViewModel: KrasnodarViewModel = viewModel()
) {
    val appState by appViewModel.uiState.collectAsState()
    Row {
        VerticalNavBar(
            appViewModel = appViewModel,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
        )
        PlaceCardList(
            placesList = when (appState.currentCategory) {
                Category.HOTELS -> hotelPlaces
                Category.VISIT -> visitPlaces
                Category.FOOD -> foodPlaces
                else -> funPlaces
            },
            onClick = {
                appViewModel.changeCurrentPlace(it)
                appViewModel.navigateToDetailPage()
            },
            modifier = Modifier.weight(5F)
        )
        PlaceDetailsScreen(
            place = appState.currentPlace,
            modifier = Modifier.weight(7F)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListItem(
    place: Place,
    modifier: Modifier = Modifier,
    onClick: (Place) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(CircleShape),
        onClick = { onClick(place) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = stringResource(id = place.name),
                modifier = modifier
                    .clip(CircleShape)
                    .size(96.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = place.name),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun PlaceCardList(
    placesList: List<Place>,
    onClick: (Place) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier.padding(contentPadding)
    ) {
        items(placesList, key = null) { place ->
            PlaceListItem(
                place = place,
                onClick = onClick
            )
        }
    }
}

@Composable
fun PlaceDetailsScreen(
    place: Place,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Image(
                painter = painterResource(id = place.image),
                contentDescription = stringResource(id = place.name),
                modifier = Modifier.clip(RoundedCornerShape(0.dp, 0.dp, 35.dp, 35.dp))
            )
        }
        item {
            Text(
                stringResource(id = place.desc),
                modifier = Modifier.padding(top = 8.dp, start = 12.dp, end = 12.dp),
                fontSize = 22.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    currentCategory: Category,
    isShowingListPage: Boolean,
    currentPlace: Place,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    TopAppBar(
        title = {
            if (isShowingListPage) {
                Text(
                    text = when(currentCategory) {
                    Category.HOTELS -> "Отели"
                    Category.FOOD -> "Еда"
                    Category.FUN -> "Развлечения"
                    else -> "Достопримечательности"
                },
                    fontWeight = FontWeight.Bold
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            modifier = modifier.fillMaxHeight()
                        )
                    }
                    Text(
                        text = stringResource(id = currentPlace.name),
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(end = 8.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun HorizontalNavBar(
    modifier: Modifier = Modifier.height(64.dp),
    appViewModel: KrasnodarViewModel
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.HOTELS) },
            modifier = modifier.weight(1F)
        ) {
            Icon(
                imageVector = Icons.Filled.Hotel,
                contentDescription = "Hotels",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.FOOD) },
            modifier = modifier.weight(1F)
        ) {
            Icon(
                imageVector = Icons.Filled.Restaurant,
                contentDescription = "Food",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.FUN) },
            modifier = modifier.weight(1F)
        ) {
            Icon(
                imageVector = Icons.Filled.Attractions,
                contentDescription = "Fun",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.VISIT) },
            modifier = modifier.weight(1F)
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Visit",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

@Composable
fun VerticalNavBar(
    modifier: Modifier = Modifier.height(64.dp),
    appViewModel: KrasnodarViewModel
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.HOTELS) },
            modifier = modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Hotel,
                contentDescription = "Hotels",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )

        }
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.FOOD) },
            modifier = modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Restaurant,
                contentDescription = "Food",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.FUN) },
            modifier = modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Attractions,
                contentDescription = "Fun",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        IconButton(
            onClick = { appViewModel.changeCurrentCategory(Category.VISIT) },
            modifier = modifier
                .weight(1F)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Visit",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun KrasnodarAppPreview() {
    KrasnodarTheme(darkTheme = true) {
        KrasnodarCompactDevice()
    }
}

@Preview(device = Devices.TABLET, showBackground = true, showSystemUi = true)
@Composable
fun KrasnodarAppExpandedPreview() {
    KrasnodarExpandedDevice()
}