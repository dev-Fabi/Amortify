package dev.schedler.amortify

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.room.RoomDatabase
import dev.schedler.amortify.data.local.db.AmortifyDatabase
import dev.schedler.amortify.di.databaseModule
import dev.schedler.amortify.di.repositoryModule
import dev.schedler.amortify.di.viewModelModule
import dev.schedler.amortify.presentation.cardlist.CardListScreen
import dev.schedler.amortify.presentation.cardlist.CardListViewModel
import dev.schedler.amortify.presentation.navigation.Screen
import dev.schedler.amortify.presentation.navigation.UuidNavType
import dev.schedler.amortify.presentation.util.Resource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import kotlin.reflect.typeOf
import kotlin.uuid.Uuid

@Composable
@Preview
fun App(databaseBuilder: RoomDatabase.Builder<AmortifyDatabase>) {
    KoinApplication(application = {
        modules(
            databaseModule(databaseBuilder),
            repositoryModule,
            viewModelModule,
        )
    }) {
        val isDarkTheme = isSystemInDarkTheme()
        val colorTheme = remember(isDarkTheme) {
            if (isDarkTheme) darkColorScheme() else lightColorScheme()
        }
        MaterialTheme(
            colorScheme = colorTheme
        ) {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.CardList) {
                composable<Screen.CardList> {
                    val vm: CardListViewModel = koinViewModel()
                    CardListScreen(
                        cards = vm.cards.collectAsStateWithLifecycle(Resource.Loading).value,
                        onSave = vm::saveCard,
                        onClick = {
                            if (it.id != null) navController.navigate(Screen.CardDetail(it.id))
                        },
                        onAddUsage = vm::addUsageEntry
                    )
                }
                composable<Screen.CardDetail>(
                    typeMap = mapOf(typeOf<Uuid>() to UuidNavType())
                ) { backStackEntry ->
                    val screen: Screen.CardDetail = backStackEntry.toRoute()

                    Scaffold { paddingValues ->
                        Column(modifier = Modifier.padding(paddingValues)) {
                            Text("Card Detail ${screen.cardId}")
                            Button(onClick = { navController.popBackStack() }) {
                                Text("Go Back")
                            }
                        }
                    }
                }
            }
        }
    }
}