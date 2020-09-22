package com.kafka.content.compose

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.ExperimentalLazyDsl
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kafka.content.R
import com.kafka.ui.theme.KafkaTheme

@ExperimentalLazyDsl
@Composable
fun MainScreen(actions: Actions) {
    val selectedNavigation: MutableState<BottomNavigationItem> = mutableStateOf(BottomNavigationItem.Home)
    Scaffold(
        backgroundColor = KafkaTheme.colors.surface,
        topBar = { TopBar() },
        bottomBar = { BottomBar(selectedNavigation) }
    ) {
        when (selectedNavigation.value) {
            BottomNavigationItem.Home -> Homepage(actions)
            BottomNavigationItem.Search -> Homepage(actions)
            BottomNavigationItem.Library -> Homepage(actions)
            BottomNavigationItem.Profile -> Homepage(actions)
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        title = { Text(text = "") },
        backgroundColor = KafkaTheme.colors.background,
        navigationIcon = { Icon(vectorResource(id = R.drawable.ic_menu)) },
        actions = { Icon(vectorResource(id = R.drawable.ic_sun)) },
        elevation = 0.dp
    )
}

@Composable
fun BottomBar(selectedNavigation: MutableState<BottomNavigationItem>) {
    BottomNavigation(backgroundColor = KafkaTheme.colors.secondary) {
        navigationItems.forEach {
            BottomNavigationItem(
                icon = { Icon(vectorResource(id = it.icon), tint = KafkaTheme.colors.iconPrimary) },
                selected = selectedNavigation.value == it,
                onClick = { selectedNavigation.value = it })
        }
    }
}

sealed class BottomNavigationItem(val icon: Int) {
    object Home : BottomNavigationItem(R.drawable.ic_home)
    object Search : BottomNavigationItem(R.drawable.ic_twotone_search_24)
    object Library : BottomNavigationItem(R.drawable.ic_layers)
    object Profile : BottomNavigationItem(R.drawable.ic_user)
}

val navigationItems = arrayOf(
    BottomNavigationItem.Home,
    BottomNavigationItem.Search,
    BottomNavigationItem.Library,
    BottomNavigationItem.Profile
)
