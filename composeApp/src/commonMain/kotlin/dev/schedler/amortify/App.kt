package dev.schedler.amortify

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.RoomDatabase
import dev.schedler.amortify.data.local.db.AmortifyDatabase
import dev.schedler.amortify.di.databaseModule
import dev.schedler.amortify.di.repositoryModule
import dev.schedler.amortify.di.viewModelModule
import dev.schedler.amortify.presentation.cardlist.CardListScreen
import dev.schedler.amortify.presentation.cardlist.CardListViewModel
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
            val vm: CardListViewModel = koinViewModel()
            CardListScreen(
                cards = vm.cards.collectAsStateWithLifecycle(Resource.Loading).value,
                onSave = vm::saveCard,
                onClick = { /* TODO */ },
                onAddUsage = { /* TODO */ }
            )
        }
    }
}