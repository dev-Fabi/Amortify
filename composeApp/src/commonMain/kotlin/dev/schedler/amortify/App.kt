package dev.schedler.amortify

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import dev.schedler.amortify.presentation.util.Resource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel

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
        MaterialTheme {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.CardList) {
                composable<Screen.CardList> {
                    val vm: CardListViewModel = koinViewModel()
                    CardListScreen(
                        cards = vm.cards.collectAsStateWithLifecycle(Resource.Loading).value,
                        onSave = vm::saveCard,
                        onClick = {
                            if (it.id != null) navController.navigate(Screen.CardDetail(it.id.toHexString()))
                        },
                        onAddUsage = { /* TODO */ }
                    )
                }
                composable<Screen.CardDetail> { backStackEntry ->
                    val screen: Screen.CardDetail = backStackEntry.toRoute()

                    Column {
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